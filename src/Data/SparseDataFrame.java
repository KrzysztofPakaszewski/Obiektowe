package Data;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame{
    public static void main(String argv[]){
    }
    public SparseDataFrame(DataFrame other, Object hide){
        super(other.names.toArray(new String[0]),other.types.toArray(new String[0]));
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
    public SparseDataFrame(String[] columnnames, String[] columntypes,
                           Object hide){
        super(columnnames,columntypes);
        for(int a =0; a< columntypes.length;a++)
        {
            ArrayList<C00Value> temp = new ArrayList<>();
            data.add(temp);
        }
        size=0;
        tohide = hide;
    }
    public SparseDataFrame(){size=0;}
    public SparseDataFrame(String filePath, String[] columntypes, boolean header,Object hide){
        tohide=hide;
        size=0;
        for(int a =0; a< columntypes.length;a++)
        {
            ArrayList<C00Value> temp = new ArrayList<>();
            data.add(temp);
        }
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

    private Object tohide;
    private ArrayList<ArrayList<C00Value>> data =
            new ArrayList<ArrayList<C00Value>>();
    private int size;

    public void print(){
        for(int a =0; a< data.size();a++)
        {
            System.out.print(names.get(a) + "\t");
            for(int b =0; b< data.get(a).size();b++){
                data.get(a).get(b).print();
            }
            System.out.println();
        }
    }

    public void add(ArrayList<Object> objects){
        if (types.size() == objects.size()) {
            boolean TypesMatch = TypesMatch(objects);
            if(TypesMatch){
                for( int a =0; a< objects.size();a++){
                    if(!objects.get(a).equals(tohide)){
                        data.get(a).add(new C00Value(objects.get(a),size));
                    }
                }
                size++;
            }
        }
    }
    public DataFrame toDense(){
        DataFrame output = new DataFrame(names.toArray(new String [0]),types.toArray(new String[0]));
        for(int a=0; a< size; a++) {
            output.add(ilocArray(a));
        }
        return output;
    }
    public void addColumnSparse(String name, String type, ArrayList<C00Value> objects){
        boolean Match= true;
        for(int a=0;a< objects.size();a++){
            if(!type.equals(objects.get(a).getData().getClass().getSimpleName()))
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
    public Object get(int row, int col){
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
        return null;
    }
    @Override
    public ArrayList<C00Value> get(String coln){
        if(!data.isEmpty() && names.contains(coln)) {
            return data.get(names.indexOf(coln));
        }
        return null;
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
    public SparseDataFrame iloc(int i){
        if(!data.isEmpty() && i>=0 && i< size){
            SparseDataFrame output = new SparseDataFrame(names.toArray(new String [0]),types.toArray(new String[0]),this.tohide);
            ArrayList<Object> temp = new ArrayList<>();
            this.GetRow(i,temp);
            output.add(temp);
            return output;
        }
        return new SparseDataFrame();
    }
    @Override
    public SparseDataFrame iloc(int from, int to){
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
    public ArrayList<Object> ilocArray(int i){
        if(!data.isEmpty() && i>=0 && i< size) {
            ArrayList<Object> temp = new ArrayList<>();
            GetRow(i, temp);
            return temp;
        }
        return new ArrayList<>();
    }

    protected void GetRow(int i, ArrayList<Object> temp) {
        for (int a = 0; a < data.size(); a++) {
            boolean isfound = false;
            for (int b = 0; b < data.get(a).size(); b++) {
                if (data.get(a).get(b).row == i) {
                    temp.add(data.get(a).get(b).data);
                    isfound = true;
                }
            }
            if (!isfound) {
                temp.add(tohide);
            }
        }
    }
}