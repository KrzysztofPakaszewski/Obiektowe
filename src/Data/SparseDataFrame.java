package Data;

import java.util.ArrayList;
import java.util.Arrays;

public class SparseDataFrame extends DataFrame{
    public static void main(String argv[]){
        /*
        SparseDataFrame temp = new SparseDataFrame(new String[] {"a","b"}
                ,new String[]{"Integer","Integer"},0);
        DataFrame temp2 = new DataFrame(new String[] {"a","b"}
                ,new String[]{"Integer","Integer"});
        temp2.add(new ArrayList<>(Arrays.asList(5,0)));
        temp2.add(new ArrayList<>(Arrays.asList(0,1)));
        temp2.add(new ArrayList<>(Arrays.asList(556,34)));
        temp2.add(new ArrayList<>(Arrays.asList(0,0)));
        temp2.add(new ArrayList<>(Arrays.asList(7,0)));
        temp2.add(new ArrayList<>(Arrays.asList(1,0)));
        //temp2.print();
        SparseDataFrame oif = new SparseDataFrame(temp2,0);
        //oif.print();
        temp.add(new ArrayList<>(Arrays.asList(1, 25)));
        temp.add(new ArrayList<>(Arrays.asList(0, 0)));
        temp.add(new ArrayList<>(Arrays.asList(8, 0)));
        temp.add(new ArrayList<>(Arrays.asList(5, 0)));
        temp.add(new ArrayList<>(Arrays.asList(10, 5)));
        temp.print();
        SparseDataFrame other = temp.get(new String[]{"a"},true);
        other.print();
        System.out.println(temp.size());
        temp.toDense().print();

        double b =0.0;
        SparseDataFrame temp = new SparseDataFrame("TestFiles\\sparse.csv",new String[]{"Double","Double","Double"}
        ,true,b);
        temp.print();
        */
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
            boolean TypesMatch = isTypesMatch(objects);
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
    @Override
    public int size(){
        return size;
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
            if (copy) {
                for (int a = 0; a < colnames.length; a++) {
                    if (!names.contains(colnames[a])) {
                        continue;
                    }
                    int index = names.indexOf(colnames[a]);
                    output.names.add(colnames[a]);
                    output.types.add(types.get(index));
                    output.data.add((ArrayList<C00Value>)data.get(index).clone());
                    output.size++;
                }
            } else {
                for (int a = 0; a < colnames.length; a++) {
                    if (!names.contains(colnames[a])) {
                        continue;
                    }
                    int index = names.indexOf(colnames[a]);
                    output.names.add(colnames[a]);
                    output.types.add(types.get(index));
                    output.data.add(data.get(index));
                    output.size++;
                }
            }
        }
        return output;
    }
    @Override
    public DataFrame iloc(int i){
        if(!data.isEmpty() && i>=0 && i< size){
            DataFrame output = new DataFrame(names.toArray(new String [0]),types.toArray(new String[0]));
            ArrayList<Object> temp = new ArrayList<>();
            GetRow(i, temp);
            output.add(temp);
        }
        return new DataFrame();
    }
    @Override
    public DataFrame iloc(int from, int to){
        if(!data.isEmpty() && from>=0 && from < size && to >=0 && to < size && from <to){
            DataFrame output = iloc(from);
            for(int a =from+1; a <= to;a++){
                output.sum(iloc(a));
            }
            return output;
        }
        return new DataFrame();
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