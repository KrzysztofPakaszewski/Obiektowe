package Data;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame{
    public static void main(String argv[]){
    	SparseDataFrame temp = new SparseDataFrame(new String[] {"a","b"}
    	,new String[]{"Integer","Integer"});
    	temp.add(new ArrayList<>(Arrays.asList(1, 25)));
        
    }
    public SparseDataFrame(String[] columnnames, String[] columntypes,
    		Object hide=0){
    	super(columnnames,columntypes);
    	tohide = hide;
    }
    private object tohide;
    private ArrayList<ArrayList<C00Value>> data = 
    		new ArrayList<ArrayList<C00Value>>();
    private ArrayList<int> size = new ArrayList<int>();
    public void add(ArrayList<Object> objects){
    	if (types.size() == objects.size()) {
            boolean TypesMatch = true;
            for (int a = 0; a < types.size(); a++) {
                if (!types.get(a).equals(objects.get(a).getClass().getSimpleName())) {
                    TypesMatch = false;
                    break;
                }
            }
            if(TypesMatch){
            	for( int a =0; a< objects.size();a++){
            		if(objects.get(a) != tohide){
            			data.get(a).add(new C00Value(objects.get(a),size.get(a)))
            		}
            		size.get(a)++;
            	}
            }
    	}
    }
    public ArrayList<Object> toDense(){
    	ArrayList<Object> output = new ArrayList<Object>();
    	return output;
    }
}