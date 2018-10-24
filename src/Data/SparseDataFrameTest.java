package Data;

import Data.Values.DoubleValue;
import Data.Values.IntegerValue;
import Data.Values.Value;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SparseDataFrameTest {

    @Test
    void addingCorrectRowsTest(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a","b"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)),new IntegerValue(0));
        df.add(new ArrayList<String>(Arrays.asList("1", "0")));
        df.add(new ArrayList<String>(Arrays.asList("1", "26")));
        df.add(new ArrayList<String>(Arrays.asList("0", "6")));
        df.add(new ArrayList<String>(Arrays.asList("0", "0")));
        assertEquals(4,df.size());
        assertEquals("0",df.get(2,0).toString());
        assertEquals("26",df.get(1,1).toString());
        assertEquals(null,df.get(5,0));
    }
    @Test
    void addingInvalidRowsDoesNothing(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a","b"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)),new IntegerValue(0));
        df.add(new ArrayList<String>(Arrays.asList("1.45", "0")));
        df.add(new ArrayList<String>(Arrays.asList("0", "26","jnaw")));
        df.add(new ArrayList<String>(Arrays.asList("0", "6")));
        df.add(new ArrayList<String>(Arrays.asList("1", "0","uhw")));
        assertEquals(1,df.size());
        assertEquals("6",df.get(0,1).toString());
    }
    @Test
    void testgetFunctionThatReturnsColumn(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"kol1", "kol2", "kol3"},
                new ArrayList<>(Arrays.asList(DoubleValue.class, DoubleValue.class,DoubleValue.class)),new DoubleValue(0));
        df.add(new ArrayList<>(Arrays.asList("1.78", "2.5", "0.0")));
        df.add(new ArrayList<>(Arrays.asList("7.13", "0.0","5.21")));
        df.add(new ArrayList<>(Arrays.asList("5.65", "0.0","0.0")));
        df.add(new ArrayList<>(Arrays.asList("0.0", "17.48","0.0")));
        df.add(new ArrayList<>(Arrays.asList("0.0", "0.0","2.0")));
        ArrayList<C00Value> temp = new ArrayList<C00Value>();
        temp.addAll(Arrays.asList(new C00Value(new DoubleValue(5.21),1),new C00Value(new DoubleValue(2.0),4)));
        assertEquals(temp.get(0).data.toString(),df.get("kol3").get(0).data.toString());
        assertEquals(temp.get(0).row,df.get("kol3").get(0).row);
        assertEquals(temp.get(1).data.toString(),df.get("kol3").get(1).data.toString());
        assertEquals(temp.get(0).row,df.get("kol3").get(0).row);
    }
    @Test
    void testgetFunctionThatReturnsDataFrameShallowCopy(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"1", "2","3"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class,IntegerValue.class)),new IntegerValue(0));
        df.add(new ArrayList<>(Arrays.asList("0", "5","6")));
        df.add(new ArrayList<>(Arrays.asList("0", "0","6")));
        assertEquals("6",df.get(0,2).toString());
        assertEquals(2,df.size());
        DataFrame df2 = df.get(new String[]{"1","3"},false);
        assertEquals(2,df2.size());
        assertEquals("0",df2.get(0,0).toString());
        assertEquals("6",df2.get(0,1).toString());
        df2.add(new ArrayList<>(Arrays.asList("10","12")));
        assertEquals(3,df2.size());
        assertEquals(3,df.get("3").size());
    }
    @Test
    void testgetFunctionThatReturnsDataFrameDeepCopy() {
        SparseDataFrame df = new SparseDataFrame(new String[]{"1", "2", "3"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class,IntegerValue.class)),new IntegerValue(0));
        df.add(new ArrayList<>(Arrays.asList("0", "653", "8")));
        df.add(new ArrayList<>(Arrays.asList("0", "0", "8")));
        assertEquals("8", df.get(0, 2).toString());
        assertEquals(2, df.size());
        DataFrame df2 = df.get(new String[]{"1", "3"}, true);
        assertEquals(2, df2.size());
        assertEquals("0", df2.get(0, 0).toString());
        assertEquals("8", df2.get(0, 1).toString());
        df2.add(new ArrayList<>(Arrays.asList("12", "54")));
        assertEquals(3, df2.size());
        assertEquals(2,df.get("3").size());
    }
    @Test
    void testIlocFuncionOne(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)),new IntegerValue(1));
        df.add(new ArrayList<>(Arrays.asList("1", "0")));
        df.add(new ArrayList<>(Arrays.asList("0", "0")));
        DataFrame df2 = df.iloc(1);
        assertEquals(2,df.size());
        assertEquals(1,df2.size());
        assertEquals("0" ,df2.get(0,1).toString());
        df2.add(new ArrayList<>(Arrays.asList("1", "0")));
        df2.add(new ArrayList<>(Arrays.asList("1", "1")));
        assertEquals(3,df2.size());
        assertEquals(2,df.size());
    }
    @Test
    void testIlocFunctionMultiple(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)),new IntegerValue(0));
        df.add(new ArrayList<>(Arrays.asList("45", "0")));
        df.add(new ArrayList<>(Arrays.asList("0", "67")));
        df.add(new ArrayList<>(Arrays.asList("1", "0")));
        df.add(new ArrayList<>(Arrays.asList("0", "98")));
        DataFrame df2 = df.iloc(0,2);
        assertEquals(3,df2.size());
        assertEquals(4,df.size());
        assertEquals("0",df2.get(2,1).toString());
        assertEquals("67",df2.get(1,1).toString());
        df2.add(new ArrayList<>(Arrays.asList("0", "7")));
        assertEquals(4,df2.size());
        assertEquals(4,df.size());
    }
    @Test
    void testToDense(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)),new IntegerValue(0));
        df.add(new ArrayList<>(Arrays.asList("0", "1")));
        df.add(new ArrayList<>(Arrays.asList("0", "8")));
        df.add(new ArrayList<>(Arrays.asList("5", "1")));
        df.add(new ArrayList<>(Arrays.asList("90","0" )));
        df.add(new ArrayList<>(Arrays.asList("5", "0")));
        df.add(new ArrayList<>(Arrays.asList("0", "0")));
        DataFrame df2 = df.toDense();
        assertEquals(6,df2.size());
        ArrayList<String> tmp = new ArrayList<>(Arrays.asList("0","0","5","90","5","0"));
        assertEquals(tmp.get(0), df2.get("a").get(0).toString());
    }
    @Test
    void testReadingFileToSparseDataFrame(){
        SparseDataFrame df = new SparseDataFrame("TestFiles\\sparse.csv",
                new ArrayList<>(Arrays.asList(DoubleValue.class, DoubleValue.class,DoubleValue.class)),
                true,new DoubleValue(0.0));
        assertEquals(300,df.size());
        assertEquals("0.5806866214364547",df.get(0,2).toString());
        assertEquals("0.8420230750119814",df.get(4,0).toString());
        assertEquals("0.0",df.get(7,1).toString());
    }


}