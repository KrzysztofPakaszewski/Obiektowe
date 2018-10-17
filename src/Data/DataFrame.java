package Data;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFrame {
    public static void main(String [] argv) {
    }
    protected ArrayList<String> names = new ArrayList<String>();
    protected ArrayList<String> types = new ArrayList<String>();
    private ArrayList<ArrayList<Object>> Table= new ArrayList<ArrayList<Object>>();

    public DataFrame(String[] columnnames, String[] columntypes){
        for(int a =0; a< columntypes.length;a++)
        {
                names.add(columnnames[a]);
                types.add(columntypes[a]);
        }
    }
    public DataFrame(String filePath, String[] columntypes, boolean header){
        ArrayList<ArrayList<Object>> temp = ReadFile(filePath,columntypes,header);
        if(!temp.isEmpty()) {
            int a;
            String[] columnnames;
            columnnames= handlingColumnNames(columntypes.length, header, temp);
            for(int c =0; c< columntypes.length;c++)
            {
                names.add(columnnames[c]);
                types.add(columntypes[c]);
            }
            if(header)
                a=1;
            else
                a=0;
            for(;a< temp.size();a++){
                this.add(temp.get(a));
            }
        }
    }

    protected String[] handlingColumnNames(int length, boolean header, ArrayList<ArrayList<Object>> temp) {
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

    protected ArrayList<ArrayList<Object>> ReadFile(String filepath, String[] columntypes, boolean header) {
        BufferedReader br;
        FileInputStream fstream;
        Pattern pattern =Pattern.compile("(.*?),|(.+$)");
        Matcher m;
        ArrayList<ArrayList<Object>> output = new ArrayList<ArrayList<Object>>();
        try {
            fstream = new FileInputStream(filepath);
            br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            boolean FirstLine = true;
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                ArrayList<String> allMatches = new ArrayList<>();
                ArrayList<Object> temp= new ArrayList<>();
                m= pattern.matcher(strLine);
                while(m.find()){
                    if(m.group(1) != null)
                        allMatches.add(m.group(1));
                    else{
                        allMatches.add(m.group());
                    }
                }
                for (int a = 0; a < allMatches.size(); a++) {
                    if(FirstLine && header){
                        temp.add(allMatches.get(a));
                    }
                    else if (columntypes[a] == "Integer") {
                        temp.add(Integer.parseInt(allMatches.get(a)));
                    } else if (columntypes[a] == "Double") {
                        temp.add(Double.parseDouble(allMatches.get(a)));
                    } else {
                        Class clazz = Class.forName(columntypes[a]);
                        clazz.getMethod("FromString", String.class).invoke(null, allMatches.get(a));
                    }
                }
                if(FirstLine){
                    FirstLine = false;
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
        catch (ClassNotFoundException exc){
            System.out.println("The Class does not exist");
        }
        catch (NoSuchMethodException exc){
            System.out.println("Class does not have method \"FromString\" to create object from string");
        }
        catch (InvocationTargetException exc){
            System.out.println("Wrong data");
        }
        catch (IllegalAccessException exc){
            System.out.println("Illegal access");
        }
        catch(NumberFormatException exc){
            System.out.println("Incorrect values for a type");
        }

        return output;
    }

    public DataFrame(){}
    public void print(){
        for (String name : names) {
            System.out.format( "%10s", name+ " ");
        }
        System.out.println();
        if(!Table.isEmpty()){
            for (int a= 0; a< Table.get(0).size();a++)
            {
                for (int b =0; b< Table.size();b++)
                {
                    System.out.format("%10s" ,Table.get(b).get(a) + " ");
                }
                System.out.println();
            }
        }
    }
    public void set(int row,int col,Object data){
        if(!Table.isEmpty() && col >= 0 && col < this.size() && row >=0 && row<Table.get(0).size() ) {
            if (data.getClass().getSimpleName().equals(types.get(col))) {
                Table.get(col).set(row, data);
            }
        }
    }
    public void addColumn(String name, String type, ArrayList<Object> objects){
        boolean Match=true;
        for(int a=0;a< objects.size();a++){
            if(!type.equals(objects.get(a).getClass().getSimpleName()))
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
    public void add(ArrayList<Object> objects){
        if (types.size() == objects.size()) {
            boolean TypesMatch = TypesMatch(objects);
            if (TypesMatch) {
                if(Table.isEmpty()){
                    for (int a = 0; a < types.size(); a++) {
                        ArrayList<Object> temp = new ArrayList<>();
                        temp.add(objects.get(a));
                        Table.add(temp);
                    }
                }
                else {
                    for (int a = 0; a < Table.size(); a++) {
                        Table.get(a).add(objects.get(a));
                    }
                }
            }
        }
    }
    protected boolean TypesMatch(ArrayList<Object> objects) {
        for (int a = 0; a < types.size(); a++) {
            if (!types.get(a).equals(objects.get(a).getClass().getSimpleName())) {
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
                    output.addColumn(cols[a], types.get(index), (ArrayList<Object>) Table.get(index).clone());
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
            DataFrame output = new DataFrame(names.toArray(new String [0]),types.toArray(new String[0]));
            ArrayList<Object> temp = new ArrayList<>();
            for(int a =0;a < Table.size();a++){
                temp.add(Table.get(a).get(i));
            }
            output.add(temp);
            return output;
        }
        return new DataFrame();
    }
    public ArrayList<Object> ilocArray(int i){
        if(!Table.isEmpty() && i>=0 && i< Table.get(0).size()) {
            ArrayList<Object> temp = new ArrayList<>();
            for (int a = 0; a < Table.size(); a++) {
                temp.add(Table.get(a).get(i));
            }
            return temp;
        }
        return new ArrayList<>();
    }
    protected DataFrame sum(DataFrame other){
        if(this.names.equals(other.names) && this.types.equals(other.types) && !other.Table.isEmpty()){
            for(int a=0; a < other.Table.get(0).size();a++){
                ArrayList<Object> temp = new ArrayList<>();
                for(int b =0;b<other.Table.size();b++){
                    temp.add(other.Table.get(b).get(a));
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
}
