package Data;
import Data.Interfaces.Applyable;
import Data.Interfaces.Groubby;
import Data.Values.*;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFrame {
    public static void main(String [] argv) {
    }
    protected ArrayList<String> names = new ArrayList<String>();
    protected ArrayList<Class<? extends Value>> types = new ArrayList<>();
    private ArrayList<ArrayList<Value>> Table= new ArrayList<>();

    public DataFrame(String[] columnnames, ArrayList<Class<? extends Value>> columntypes){
        for(int a =0; a< columntypes.size();a++)
        {
                names.add(columnnames[a]);
                types=columntypes;
                Table.add(new ArrayList<>());
        }
    }
    public DataFrame(String filePath, ArrayList<Class<? extends Value>> columntypes, boolean header){
        ArrayList<ArrayList<String>> temp = ReadFile(filePath,columntypes,header);
        if(!temp.isEmpty()) {
            int a;
            String[] columnnames;
            columnnames= handlingColumnNames(columntypes.size(), header, temp);
            for(int c =0; c< columntypes.size();c++)
            {
                names.add(columnnames[c]);
            }
            types= columntypes;
            if(header)
                a=1;
            else
                a=0;
            for(;a< temp.size();a++){
                this.add(temp.get(a));
            }
        }
    }

    protected String[] handlingColumnNames(int length, boolean header, ArrayList<ArrayList<String>> temp) {
        String[] columnnames=new String[length];
        if (header) {
            columnnames=temp.get(0).toArray(new String[0]);
        } else {
            System.out.println("Enter column names");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                for (int b = 0; b < length; b++) {
                    String line = br.readLine();
                    columnnames[b] = line;
                }
                br.close();
            }
            catch (IOException exc){
                System.out.println("Buffer error");
            }
        }
        return columnnames;
    }

    protected ArrayList<ArrayList<String>> ReadFile(String filepath, ArrayList<Class<? extends Value>> columntypes, boolean header) {
        BufferedReader br;
        FileInputStream fstream;
        Pattern pattern =Pattern.compile("(.*?),|(.+$)");
        Matcher m;
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        try {
            fstream = new FileInputStream(filepath);
            br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                ArrayList<String> allMatches = new ArrayList<>();
                ArrayList<String> temp= new ArrayList<>();
                m= pattern.matcher(strLine);
                while(m.find()){
                    if(m.group(1) != null)
                        allMatches.add(m.group(1));
                    else{
                        allMatches.add(m.group());
                    }
                }
                for (int a = 0; a < allMatches.size(); a++) {
                        temp.add(allMatches.get(a));
                }
                output.add(temp);
            }
            br.close();
        }
        catch (FileNotFoundException exc){
            System.out.println("FIle not found!");
        }
        catch (IOException exc){
            System.out.println("Stream error!");
        }
        catch(NumberFormatException exc){
            System.out.println("Incorrect values for a type");
        }

        return output;
    }

    public DataFrame(){}
    public void print(){
        for (String name : names) {
            System.out.format( "%15s", name+ " ");
        }
        System.out.println();
        if(!Table.isEmpty()){
            for (int a= 0; a< Table.get(0).size();a++)
            {
                for (int b =0; b< Table.size();b++)
                {
                    System.out.format("%15s" ,Table.get(b).get(a).toString() + " ");
                }
                System.out.println();
            }
        }
    }
    public String toString(){
        String output= new String();
        for (String name : names) {
            StringBuilder sbuf = new StringBuilder();
            Formatter form=new Formatter(sbuf);
            form.format("%15s",name + " ");
            output += sbuf.toString();
        }
        output+="\n";
        if(!Table.isEmpty()){
            for (int a= 0; a< Table.get(0).size();a++)
            {
                for (int b =0; b< Table.size();b++)
                {
                    StringBuilder sbuf = new StringBuilder();
                    Formatter form=new Formatter(sbuf);
                    form.format("%15s" ,Table.get(b).get(a).toString() + " ");
                    output += sbuf.toString();
                }
                output+="\n";
            }
        }
        return output;
    }
    public void set(int row,int col,Value data){
        if(!Table.isEmpty() && col >= 0 && col < this.size() && row >=0 && row<Table.get(0).size() ) {
            if (data.getClass().getSimpleName().equals(types.get(col))) {
                Table.get(col).set(row, data);
            }
        }
    }
    public void addColumn(String name, Class<? extends Value> type, ArrayList<Value> objects){
        boolean Match=true;
        for(int a=0;a< objects.size();a++){
            if(!type.isInstance(objects.get(a)))
                Match=false;
        }
        if(Match) {
            if (!Table.isEmpty()) {
                if (Table.get(0).size() == objects.size()) {
                    names.add(name);
                    types.add(type);
                    Table.add(objects);
                }
            }
            else {
                names.add(name);
                types.add(type);
                Table.add(objects);
            }
        }
    }
    public void add(ArrayList<String> objects){
        if (types.size() == objects.size() && TypesMatch(objects)) {
            if (Table.isEmpty()) {
                for (int a = 0; a < types.size(); a++) {
                    ArrayList<Value> temp = new ArrayList<>();
                    temp.add(Value.getInstance(types.get(a)).create(objects.get(a)));
                    Table.add(temp);
                }
            } else {
                for (int a = 0; a < Table.size(); a++) {
                    Table.get(a).add(Value.getInstance(types.get(a)).create(objects.get(a)));
                }
            }
        }
    }
    protected boolean TypesMatch(ArrayList<String> objects) {
        for (int a = 0; a < types.size(); a++) {
            try{
                Value.getInstance(types.get(a)).create(objects.get(a));
            }catch (NumberFormatException exc){
                return false;
            }
        }
        return true;
    }

    public int size(){
        if(!Table.isEmpty())
            return Table.get(0).size();
        return 0;
    }
    public Object get(int row, int col){
        if(col>=0 && col<Table.size() && row<this.size() && row>=0){
            return Table.get(col).get(row);
        }
        return null;
    }
    public ArrayList<?> get(String colname){
        if(!Table.isEmpty() && names.contains(colname)) {
            return Table.get(names.indexOf(colname));
        }
        return null;
    }

    public DataFrame get(String [] cols, boolean copy){
        DataFrame output = new DataFrame();
        if(!Table.isEmpty()) {
            if (copy) {
                for (int a = 0; a < cols.length; a++) {
                    if (!names.contains(cols[a])) {
                        continue;
                    }
                    int index = names.indexOf(cols[a]);
                    output.addColumn(cols[a], types.get(index), (ArrayList<Value>) Table.get(index).clone());
                }
            } else {
                for (int a = 0; a < cols.length; a++) {
                    if (!names.contains(cols[a])) {
                        continue;
                    }
                    int index = names.indexOf(cols[a]);
                    output.addColumn(cols[a], types.get(index), Table.get(index));
                }
            }
        }
        return output;
    }

    public DataFrame iloc(int i){
        if(!Table.isEmpty() && i>=0 && i< Table.get(0).size()){
            DataFrame output = new DataFrame();
            output.names = (ArrayList<String>) names.clone();
            output.types = (ArrayList<Class<? extends Value>>) types.clone();
            ArrayList<String> temp = new ArrayList<>();
            for(int a =0;a < Table.size();a++){
                temp.add(Table.get(a).get(i).toString());
            }
            output.add(temp);
            return output;
        }
        return new DataFrame();
    }
    public ArrayList<String> ilocArray(int i){
        if(!Table.isEmpty() && i>=0 && i< Table.get(0).size()) {
            ArrayList<String> temp = new ArrayList<>();
            for (int a = 0; a < Table.size(); a++) {
                temp.add(Table.get(a).get(i).toString());
            }
            return temp;
        }
        return new ArrayList<>();
    }
    protected DataFrame sum(DataFrame other){
        if(this.names.equals(other.names) && this.types.equals(other.types) && !other.Table.isEmpty()){
            for(int a=0; a < other.Table.get(0).size();a++){
                ArrayList<String> temp = new ArrayList<>();
                for(int b =0;b<other.Table.size();b++){
                    temp.add(other.Table.get(b).get(a).toString());
                }
                this.add(temp);
            }
        }
        return this;
    }
    public DataFrame iloc(int from, int to){
        if(!Table.isEmpty() && from>=0 && from < this.size() && to >=0 && to < this.size() && from <to){
            DataFrame output = this.iloc(from);
            for(int a =from+1; a <= to;a++){
                output.sum(iloc(a));
            }
            return output;
        }
        return new DataFrame();
    }

    public Grouped groupby(String[] colname){
        ArrayList<DataFrame> output = new ArrayList<>();
        for(int a =0;a<colname.length;a++){
            if(names.contains(colname[a])){
                if( output.isEmpty()){
                    output=this.groupbyArray(colname[a]);
                }
                else {
                    ArrayList<DataFrame> tmp=new ArrayList<>();
                    for(int b =0;b< output.size();b++){
                        tmp.addAll(output.get(b).groupbyArray(colname[a]));
                    }
                    output.clear();
                    output=tmp;
                }
            }
        }
        return new Grouped(output,colname);
    }
    public Grouped groupby(String a){
        return new Grouped(groupbyArray(a),a);
    }
    private ArrayList<Value> getRow(int i){
        if(i>=0 && i< this.size()){
            ArrayList<Value> output = new ArrayList<>();
            for(int a=0; a< Table.size();a++){
                output.add(Table.get(a).get(i));
            }
            return output;
        }
        return null;
    }
    private boolean typesm(ArrayList<Value> row){
        for(int a=0;a<row.size();a++){
            if(!types.get(a).isInstance(row.get(a))){
                return false;
            }
        }
        return true;
    }
    private void addV(ArrayList<Value> row){
        if(row.size()== types.size() && typesm(row)){
            for(int a=0;a<row.size();a++){
                Table.get(a).add(row.get(a));
            }
        }
    }
    private ArrayList<DataFrame> groupbyArray(String colname){
        if(names.contains(colname) && !Table.isEmpty()){
            int indexOfCol= names.indexOf(colname);
            HashMap map = new HashMap();
            ArrayList<DataFrame> output = new ArrayList<>();
            for(int a=0;a < Table.get(indexOfCol).size();a++){
                if(map.containsKey(Table.get(indexOfCol).get(a))){
                    output.get((int)map.get(Table.get(indexOfCol).get(a))).addV(getRow(a));
                }
                else{
                    map.put(Table.get(indexOfCol).get(a),map.size());
                    output.add(new DataFrame(names.toArray(new String[0]),types));
                    output.get((int)map.get(Table.get(indexOfCol).get(a))).addV(getRow(a));
                }
            }
            return output;
        }
        throw new RuntimeException("Column not found");
    }
    public class Grouped implements Groubby{
        ArrayList<DataFrame> data;
        ArrayList<String> Cols=new ArrayList<>();
        private Grouped(ArrayList<DataFrame> input,String[] colsgr){
            data=input;
            Cols.addAll(Arrays.asList(colsgr));
        }
        private Grouped(ArrayList<DataFrame> input,String colsgr){
            data=input;
            Cols.add(colsgr);
        }
        @Override
        public DataFrame max(){
            DataFrame output= new DataFrame(data.get(0).names.toArray(new String[0]),data.get(0).types);
            for(int a=0;a<data.size();a++){
                ArrayList<Value> temp= new ArrayList<>();
                for(int b =0;b<data.get(a).Table.size();b++){
                    if(Cols.contains(data.get(a).names.get(b))){
                        temp.add(data.get(a).Table.get(b).get(0));
                        continue;
                    }
                    Value max= data.get(a).Table.get(b).get(0);
                    for(int c =1;c<data.get(a).Table.get(b).size();c++){
                        if(data.get(a).Table.get(b).get(c).greaterOrEquals(max)){
                            max = data.get(a).Table.get(b).get(c);
                        }
                    }
                    temp.add(max);
                }
                output.addV(temp);
            }
            return output;
        }
        public DataFrame min(){
            DataFrame output= new DataFrame(data.get(0).names.toArray(new String[0]),data.get(0).types);
            for(int a=0;a<data.size();a++){
                ArrayList<Value> temp= new ArrayList<>();
                for(int b =0;b<data.get(a).Table.size();b++){
                    if(Cols.contains(data.get(a).names.get(b))){
                        temp.add(data.get(a).Table.get(b).get(0));
                        continue;
                    }
                    Value min= data.get(a).Table.get(b).get(0);
                    for(int c =1;c<data.get(a).Table.get(b).size();c++){
                        if(data.get(a).Table.get(b).get(c).lessOrEquals(min)){
                            min = data.get(a).Table.get(b).get(c);
                        }
                    }
                    temp.add(min);
                }
                output.addV(temp);
            }
            return output;
        }
        private boolean isNumber(Object a){
            if(IntegerValue.class.isInstance(a)){
                return true;
            }
            else if(DoubleValue.class.isInstance(a)){
                return true;
            }
            else if(FloatValue.class.isInstance(a)){
                return true;
            }
            return false;
        }
        private Value meanOfArray(ArrayList<Value> array){
            return sumOfArray(array).div(new IntegerValue(array.size()));
        }
        private Value sumOfArray(ArrayList<Value> array) {
            Value tmp = array.get(0);
            for (int c = 1; c < array.size(); c++) {
                tmp = tmp.add(array.get(c));
            }
            return tmp;
        }
        public DataFrame mean(){
            DataFrame output= new DataFrame();
            Setup(output);
            for(int a=0;a<data.size();a++){
                ArrayList<Value> temp= new ArrayList<>();
                for(int b =0;b<data.get(a).Table.size();b++){
                    if(Cols.contains(data.get(a).names.get(b))){
                        temp.add(data.get(a).Table.get(b).get(0));
                    }
                    else if(isNumber(data.get(a).Table.get(b).get(0))) {
                        temp.add(meanOfArray(data.get(a).Table.get(b)));
                    }
                }
                output.addV(temp);
            }
            return output;
        }

        protected void Setup(DataFrame output) {
            for (int a = 0; a < data.get(0).types.size(); a++) {
                if (isNumber(data.get(0).Table.get(a).get(0)) || Cols.contains(data.get(0).names.get(a))) {
                    output.names.add(data.get(0).names.get(a));
                    output.types.add(data.get(0).types.get(a));
                    output.Table.add(new ArrayList<>());
                }
            }
        }

        private Value varOfArray(ArrayList<Value> array) {
            Value mean = meanOfArray(array);
            Value tmp = array.get(0).sub(mean).pow(new IntegerValue(2));
            for (int c = 1; c < array.size(); c++) {
                tmp = tmp.add(array.get(c).sub(mean).pow(new IntegerValue(2)));
            }
            return tmp.div(new IntegerValue(array.size()));
        }
        private Value stdOfArray(ArrayList<Value> array){
            return varOfArray(array).sqrt();
        }
        public DataFrame std(){
            DataFrame output= new DataFrame();
            Setup(output);
            for(int a=0;a<data.size();a++){
                ArrayList<Value> temp= new ArrayList<>();
                for(int b =0;b<data.get(a).Table.size();b++){
                    if(Cols.contains(data.get(a).names.get(b))){
                        temp.add(data.get(a).Table.get(b).get(0));
                        continue;
                    }
                    if(isNumber(data.get(a).Table.get(b).get(0))) {
                        temp.add(stdOfArray(data.get(a).Table.get(b)));
                    }
                }
                output.addV(temp);
            }
            return output;
        }
        public DataFrame sum(){
            DataFrame output= new DataFrame();
            Setup(output);
            for(int a=0;a<data.size();a++){
                ArrayList<Value> temp= new ArrayList<>();
                for(int b =0;b<data.get(a).Table.size();b++){
                    if(Cols.contains(data.get(a).names.get(b))){
                        temp.add(data.get(a).Table.get(b).get(0));
                        continue;
                    }
                    if(isNumber(data.get(a).Table.get(b).get(0))) {
                        temp.add(sumOfArray(data.get(a).Table.get(b)));
                    }
                }
                output.addV(temp);
            }
            return output;
        }
        public DataFrame var(){
            DataFrame output= new DataFrame();
            Setup(output);
            for(int a=0;a<data.size();a++){
                ArrayList<Value> temp= new ArrayList<>();
                for(int b =0;b<data.get(a).Table.size();b++){
                    if(Cols.contains(data.get(a).names.get(b))){
                        temp.add(data.get(a).Table.get(b).get(0));
                        continue;
                    }
                    if(isNumber(data.get(a).Table.get(b).get(0))) {
                        temp.add(varOfArray(data.get(a).Table.get(b)));
                    }
                }
                output.addV(temp);
            }
            return output;
        }
        public DataFrame apply(Applyable appl){
            DataFrame output= new DataFrame(data.get(0).names.toArray(new String[0]),data.get(0).types);
            for(int a=0;a<data.size();a++){
                output.sum(appl.apply(data.get(a)));
            }
            return output;
        }
    }
}
