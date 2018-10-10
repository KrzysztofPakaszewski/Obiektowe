import java.util.ArrayList;
import java.util.Arrays;

public class DataFrame {
    public static void main(String [] argv) {
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new String[]{"Integer", "Double", "String"});
        df.add(new ArrayList<>(Arrays.asList(1, 2.5, "auwn")));
        DataFrame df2 = df.get(new String[] {"kol1","kol2"},false);
        df2.set(0,1,2.78);
        df.print();
        df2.add(new ArrayList<>(Arrays.asList(5, 5.23)));
        df2.add(new ArrayList<>(Arrays.asList(23, 12.42)));
        df2.add(new ArrayList<>(Arrays.asList(832, 14.2)));
        System.out.println(df.size());
        System.out.println(df2.size());
        df2.print();
        System.out.println();
        DataFrame df4 = df2.iloc(1,3);
        df4.print();
    }
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> types = new ArrayList<String>();
    private ArrayList<ArrayList<Object>> Table= new ArrayList<ArrayList<Object>>();

    public DataFrame(String[] columnnames, String[] columntypes){
        for(int a =0; a< columntypes.length;a++)
        {
                names.add(columnnames[a]);
                types.add(columntypes[a]);
        }
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
        if(data.getClass().getSimpleName().equals(types.get(col))){
            Table.get(col).set(row,data);
        }
    }
    public void addColumn(String name, String type, ArrayList<Object> objects){
        if(!Table.isEmpty()) {
            if(Table.get(0).size() == objects.size()) {
                names.add(name);
                types.add(type);
                Table.add(objects);
            }
        }
        else{
            names.add(name);
            types.add(type);
            Table.add(objects);
        }
    }
    public void add(ArrayList<Object> objects){
        if (types.size() == objects.size()) {
            boolean TypesMatch = true;
            for (int a = 0; a < types.size(); a++) {
                if (!types.get(a).equals(objects.get(a).getClass().getSimpleName())) {
                    TypesMatch = false;
                    break;
                }
            }
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
    public int size(){
        if(!Table.isEmpty())
            return Table.get(0).size();
        return 0;
    }
    public ArrayList<?> get(String colname){
        if(names.contains(colname)) {
            return Table.get(names.indexOf(colname));
        }
        return null;
    }

    public DataFrame get(String [] cols, boolean copy){
        DataFrame output = new DataFrame();
        if(copy){
            for(int a =0; a< cols.length;a++)
            {
                if(!names.contains(cols[a])){
                    continue;
                }
                int index= names.indexOf(cols[a]);
                output.addColumn(cols[a],(String)types.get(index),(ArrayList<Object>) Table.get(index).clone());
            }
        }
        else{
            for(int a =0; a< cols.length;a++)
            {
                if(!names.contains(cols[a])){
                    continue;
                }
                int index= names.indexOf(cols[a]);
                output.addColumn(cols[a],types.get(index),Table.get(index));
            }
        }
        return output;
    }
    public DataFrame iloc(int i){
        if(!Table.isEmpty() && i>=0 && i< Table.get(0).size()){
            String[] NamString= new String[names.size()];
            NamString= names.toArray(NamString);
            String[] TypString = new String[types.size()];
            TypString= types.toArray(TypString);
            DataFrame output = new DataFrame(NamString,TypString);
            ArrayList<Object> temp = new ArrayList<>();
            for(int a =0;a < Table.size();a++){
                temp.add(Table.get(a).get(i));
            }
            output.add(temp);
            return output;
        }
        return new DataFrame();
    }
    public DataFrame sum(DataFrame other){
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
