package Data;

import Data.Interfaces.Applyable;
import Data.Values.DateTimeValue;
import Data.Values.DoubleValue;
import Data.Values.StringValue;
import Data.Values.Value;
import Data.exceptions.CannotCreateValueFromString;
import Data.exceptions.Error;
import Data.exceptions.InvalidData;
import Data.exceptions.InvalidOperation;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DataFrameMultiThread  extends DataFrame{
    public DataFrameMultiThread(String[] columnnames, ArrayList<Class<? extends Value>> columntypes){
        super(columnnames,columntypes);
    }
    public DataFrameMultiThread(String filepath, String[] columnnames,ArrayList<Class<? extends Value>> columntypes)
            throws Error, CannotCreateValueFromString, IOException{
        super(filepath,columnnames,columntypes);
    }
    public DataFrameMultiThread(String filePath, ArrayList<Class<? extends Value>> columntypes)
            throws Error,CannotCreateValueFromString,IOException{
        super(filePath,columntypes);
    }
    public DataFrameMultiThread(){
        super();
    }
    public DataFrameMultiThread(DataFrame other){
        names=other.names;
        types=other.types;
        Table= other.Table;
    }
    public Grouped groupbyMT(String a)throws Error{
        return new Grouped(groupby(a));
    }
    public Grouped groupByMT(String[] a)throws Error{
        return new Grouped(groupby(a));
    }
    /*
    private ArrayList<DataFrameMultiThread> groupbyArray(String colname)throws Error{
        if(names.contains(colname) && !Table.isEmpty()){
            int indexOfCol= names.indexOf(colname);
            Set<Value> set = new LinkedHashSet<>(Table.get(indexOfCol));
            System.out.println(Runtime.getRuntime().availableProcessors());
            ExecutorService es = Executors.newFixedThreadPool(10);
            ArrayList<DataFrameMultiThread> output=new ArrayList<>();
            while(output.size()< set.size()){
                output.add(new DataFrameMultiThread(names.toArray(new String[0]),types));
            }
            List<Callable<String>> callables= new ArrayList<>();
            Object[] values = set.toArray();
            for(int a=0;a < set.size();a++){
                final int index =a;
                callables.add(new Callable () {
                    @Override
                    public String call() throws Exception{
                        int size= Table.get(indexOfCol).size();
                        for(int b=0;b< size;b++){
                            if(Table.get(indexOfCol).get(b).equals(values[index])){
                                output.get(index).addV(getRow(b));
                            }
                        }
                        return null;
                    }
                }
                );
            }
            try {
                //List<Future<String>> future=
                es.invokeAll(callables);
                for (int a=0;a<future.size();a++) {
                    System.out.println(a);
                    System.out.println(future.get(a).get());
                }

            }catch (InterruptedException e){
                throw new Error(e.getMessage());
            }catch (CancellationException e){
                throw new RuntimeException("ERROR");
            }
            catch (java.util.concurrent.ExecutionException e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
            return output;
        }
        throw new Error("Column not found");
    }
    public Grouped groupBY(String a)throws Error{
        return new Grouped(groupbyArray(a),a);
    }
    public Grouped groupBY(String[] colname) throws Error{
        ArrayList<DataFrameMultiThread> output = new ArrayList<>();
        for(int a =0;a<colname.length;a++){
            if(names.contains(colname[a])){
                if( output.isEmpty()){
                    output=this.groupbyArray(colname[a]);
                }
                else {
                    ArrayList<DataFrameMultiThread> tmp=new ArrayList<>();
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
*/
    public static class Grouped extends DataFrame.Grouped{
        public Grouped(ArrayList<DataFrameMultiThread> input,String[] colsgr){
            data = input;
            Cols.addAll(Arrays.asList(colsgr));
        }
        public Grouped(ArrayList<DataFrameMultiThread> input,String colsgr){
            super();
            data = input;
            Cols=new ArrayList<>();
            Cols.add(colsgr);
        }

        public Grouped(DataFrame.Grouped grp){
            data=grp.data;
            Cols=grp.Cols;
        }
        @Override
        protected void LoopAppl(DataFrame df,DataFrame output, Applyable apl)throws InvalidData {
            ExecutorService es = Executors.newFixedThreadPool(Cols.size());
            List<Callable<String>> callables = new ArrayList<>();
            ArrayList<Value> temp= new ArrayList<>();
            while(temp.size()<output.names.size()){
                temp.add(null);
            }
            int a =0;
            for(int b =0;b<df.Table.size();b++){
                if (Cols.contains(df.names.get(b))) {
                    temp.set(a,df.Table.get(b).get(0));
                    a++;
                    continue;
                }
                final int indexB=b;
                final int indexA = a;
                if(output.names.get(a).equals(df.names.get(b))) {
                    callables.add(new Callable() {
                        @Override
                        public Object call() throws Exception {
                            apl.operate(df, temp, indexB, indexA);
                            return null;
                    }});
                    a++;
                }
            }
            try {
                es.invokeAll(callables);
                es.shutdown();
            }catch (InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
            output.addV(temp);
        }
        @Override
        public DataFrame apply(Applyable appl)throws InvalidData, Error,CannotCreateValueFromString{
            ArrayList<DataFrame> tmp= new ArrayList<>();
            while(data.size()> tmp.size()){
                tmp.add(null);
            }
            ExecutorService es = Executors.newFixedThreadPool(data.size());
            List<Callable<String>> callables = new ArrayList<>();
            for(int a=0;a<data.size();a++){
                final int index = a;
                callables.add(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        tmp.set(index,appl.apply(data.get(index)));
                        return null;
                    }
                });
            }
            try{
                es.invokeAll(callables);
            }catch (InterruptedException e){
                throw new Error(e.getMessage());
            }
            DataFrame output= tmp.get(0);
            for(int a=1;a< tmp.size();a++){
                output.sum(tmp.get(a));
            }
            return output;
        }
    }
}
