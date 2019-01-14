package Data;

import Data.exceptions.CannotCreateValueFromString;
import Data.Values.Value;
import Data.exceptions.Error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SparseDataFrame extends DataFrame{

    public static void main(String argv[]){
    }
    public SparseDataFrame(DataFrame other, Value hide)throws CannotCreateValueFromString,Error{
        super(other.names.toArray(new String[0]),other.types);
        for(int a =0; a< other.names.size();a++)
        {
            ArrayList<C00Value> temp = new ArrayList<>();
            data.add(temp);
        }
        tohide = hide;
        size=0;
        for(int a=0; a< other.size();a++){
            this.add(other.ilocArray(a));
            size++;
        }
    }
    public SparseDataFrame(String[] columnnames, ArrayList<Class<? extends Value>> columntypes,
                           Value hide){
        super(columnnames,columntypes);
        for(int a =0; a< columntypes.size();a++)
        {
            ArrayList<C00Value> temp = new ArrayList<>();
            data.add(temp);
        }
        size=0;
        tohide = hide;
    }
    public SparseDataFrame(){size=0;}
    public SparseDataFrame(String filePath,String[] columnnames, ArrayList<Class<? extends Value>> columntypes,Value hide)
            throws IOException,CannotCreateValueFromString,Error{
        tohide=hide;
        size=0;
        for(int a =0; a< columntypes.size();a++)
        {
            ArrayList<C00Value> temp = new ArrayList<>();
            data.add(temp);
        }
        types=columntypes;
        names=new ArrayList<>(Arrays.asList(columnnames));
        ReadFile(filePath,false);
    }
    public SparseDataFrame(String filePath, ArrayList<Class<? extends Value>> columntypes,Value hide)
        throws IOException,CannotCreateValueFromString,Error{
        tohide=hide;
        size=0;
        for(int a =0; a< columntypes.size();a++)
        {
            ArrayList<C00Value> temp = new ArrayList<>();
            data.add(temp);
        }
        types=columntypes;
        ReadFile(filePath,true);
    }

    private Value tohide;
    private ArrayList<ArrayList<C00Value>> data =
            new ArrayList<ArrayList<C00Value>>();
    private int size;
    public void add(ArrayList<String> objects)throws CannotCreateValueFromString,Error{
        if (types.size() == objects.size()) {
            for( int a =0; a< objects.size();a++){
                if(!objects.get(a).equals(tohide.toString())){
                    data.get(a).add(new C00Value(Value.getInstance(types.get(a)).create(objects.get(a)),size));
                }
            }
            size++;
        }
    }
    public DataFrame toDense()throws CannotCreateValueFromString,Error{
        DataFrame output = new DataFrame(names.toArray(new String [0]),types);
        for(int a=0; a< size; a++) {
            output.add(ilocArray(a));
        }
        return output;
    }
    public void addColumnSparse(String name, Class<? extends Value> type, ArrayList<C00Value> objects){
        boolean Match= true;
        for(int a=0;a< objects.size();a++){
            if(!type.isInstance(objects.get(a).getData()))
                Match=false;
        }
        if(Match) {
            if (!data.isEmpty()) {
                if( size >= objects.get(objects.size()-1).getRow()) {
                    names.add(name);
                    types.add(type);
                    data.add(objects);
                }
            }else{
                names.add(name);
                types.add(type);
                data.add(objects);
            }
        }
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public Value get(int row, int col)throws Error{
        if(row>=0 && row< this.size() && col>=0 && col < types.size()){
            if(col < data.size()) {
                for (int a = 0; a < data.get(col).size(); a++) {
                    if (data.get(col).get(a).row == row) {
                        return data.get(col).get(a).data;
                    } else if (data.get(col).get(a).row > row)
                        return tohide;
                }
            }
            return tohide;
        }
        throw new Error("cell on row:"+ row+" and col:" +col +" does not exist");
    }
    @Override
    public ArrayList<C00Value> get(String coln)throws Error{
        if(!data.isEmpty() && names.contains(coln)) {
            return data.get(names.indexOf(coln));
        }
        throw new Error("there is no such column");
    }
    @Override
    public SparseDataFrame get(String[] colnames, boolean copy){
        SparseDataFrame output = new SparseDataFrame();
        if(!data.isEmpty()) {
            output.tohide=tohide;
            output.size=size;
            if (copy) {
                for (int a = 0; a < colnames.length; a++) {
                    if (!names.contains(colnames[a])) {
                        continue;
                    }
                    int index = names.indexOf(colnames[a]);
                    output.addColumnSparse(colnames[a],types.get(index),(ArrayList<C00Value>)data.get(index).clone());
                }
            } else {
                for (int a = 0; a < colnames.length; a++) {
                    if (!names.contains(colnames[a])) {
                        continue;
                    }
                    int index = names.indexOf(colnames[a]);
                    output.addColumnSparse(colnames[a],types.get(index),data.get(index));
                }
            }
        }
        return output;
    }
    @Override
    public SparseDataFrame iloc(int i)throws CannotCreateValueFromString,Error{
        if(!data.isEmpty() && i>=0 && i< size){
            SparseDataFrame output = new SparseDataFrame(names.toArray(new String [0]),types,this.tohide);
            ArrayList<String> temp = new ArrayList<>();
            this.GetRow(i,temp);
            output.add(temp);
            return output;
        }
        return new SparseDataFrame();
    }
    @Override
    public SparseDataFrame iloc(int from, int to)throws CannotCreateValueFromString,Error{
        if(!data.isEmpty() && from>=0 && from < size && to >=0 && to < size && from <to){
            SparseDataFrame output = this.iloc(from);
            for(int a =from+1; a <= to;a++){
                output.add(iloc(a).ilocArray(0));
            }
            return output;
        }
        return new SparseDataFrame();
    }
    @Override
    public ArrayList<String> ilocArray(int i){
        if(!data.isEmpty() && i>=0 && i< size) {
            ArrayList<String> temp = new ArrayList<>();
            GetRow(i, temp);
            return temp;
        }
        return new ArrayList<>();
    }

    protected void GetRow(int i, ArrayList<String> temp) {
        for (int a = 0; a < data.size(); a++) {
            boolean isfound = false;
            for (int b = 0; b < data.get(a).size(); b++) {
                if (data.get(a).get(b).row == i) {
                    temp.add(data.get(a).get(b).data.toString());
                    isfound = true;
                }
            }
            if (!isfound) {
                temp.add(tohide.toString());
            }
        }
    }

}
