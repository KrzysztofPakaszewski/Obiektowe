package Data.Tests;

import Data.C00Value;
import Data.DataFrame;
import Data.SparseDataFrame;
import Data.Values.DoubleValue;
import Data.Values.IntegerValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SparseDataFrameTest {

    @Test
    void addingCorrectRowsTest(){
        try {
            SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                    new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)), new IntegerValue(0));
            df.add(new ArrayList<String>(Arrays.asList("1", "0")));
            df.add(new ArrayList<String>(Arrays.asList("1", "26")));
            df.add(new ArrayList<String>(Arrays.asList("0", "6")));
            df.add(new ArrayList<String>(Arrays.asList("0", "0")));
            assertEquals(4, df.size());
            assertEquals("0", df.get(2, 0).toString());
            assertEquals("26", df.get(1, 1).toString());
        }
        catch (Exception e)
        {
            fail();
        }
    }
    @Test
    void testgetFunctionThatReturnsColumn(){
        try {
            SparseDataFrame df = new SparseDataFrame(new String[]{"kol1", "kol2", "kol3"},
                    new ArrayList<>(Arrays.asList(DoubleValue.class, DoubleValue.class, DoubleValue.class)), new DoubleValue(0));
            df.add(new ArrayList<>(Arrays.asList("1.78", "2.5", "0.0")));
            df.add(new ArrayList<>(Arrays.asList("7.13", "0.0", "5.21")));
            df.add(new ArrayList<>(Arrays.asList("5.65", "0.0", "0.0")));
            df.add(new ArrayList<>(Arrays.asList("0.0", "17.48", "0.0")));
            df.add(new ArrayList<>(Arrays.asList("0.0", "0.0", "2.0")));
            ArrayList<C00Value> temp = new ArrayList<C00Value>();
            temp.addAll(Arrays.asList(new C00Value(new DoubleValue(5.21), 1), new C00Value(new DoubleValue(2.0), 4)));
            assertEquals(temp.get(0).getData().toString(), df.get("kol3").get(0).getData().toString());
            assertEquals(temp.get(0).getRow(), df.get("kol3").get(0).getRow());
            assertEquals(temp.get(1).getData().toString(), df.get("kol3").get(1).getData().toString());
            assertEquals(temp.get(0).getRow(), df.get("kol3").get(0).getRow());
        }
        catch (Exception e)
        {
            fail();
        }
    }
    @Test
    void testgetFunctionThatReturnsDataFrameShallowCopy(){
        try {
            SparseDataFrame df = new SparseDataFrame(new String[]{"1", "2", "3"},
                    new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class, IntegerValue.class)), new IntegerValue(0));
            df.add(new ArrayList<>(Arrays.asList("0", "5", "6")));
            df.add(new ArrayList<>(Arrays.asList("0", "0", "6")));
            assertEquals("6", df.get(0, 2).toString());
            assertEquals(2, df.size());
            DataFrame df2 = df.get(new String[]{"1", "3"}, false);
            assertEquals(2, df2.size());
            assertEquals("0", df2.get(0, 0).toString());
            assertEquals("6", df2.get(0, 1).toString());
            df2.add(new ArrayList<>(Arrays.asList("10", "12")));
            assertEquals(3, df2.size());
            assertEquals(3, df.get("3").size());
        }
        catch (Exception e)
        {
            fail();
        }
    }
    @Test
    void testgetFunctionThatReturnsDataFrameDeepCopy() {
        try {
            SparseDataFrame df = new SparseDataFrame(new String[]{"1", "2", "3"},
                    new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class, IntegerValue.class)), new IntegerValue(0));
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
            assertEquals(2, df.get("3").size());
        }
        catch (Exception e)
        {
            fail();
        }
    }
    @Test
    void testIlocFuncionOne(){
        try {
            SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                    new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)), new IntegerValue(1));
            df.add(new ArrayList<>(Arrays.asList("1", "0")));
            df.add(new ArrayList<>(Arrays.asList("0", "0")));
            DataFrame df2 = df.iloc(1);
            assertEquals(2, df.size());
            assertEquals(1, df2.size());
            assertEquals("0", df2.get(0, 1).toString());
            df2.add(new ArrayList<>(Arrays.asList("1", "0")));
            df2.add(new ArrayList<>(Arrays.asList("1", "1")));
            assertEquals(3, df2.size());
            assertEquals(2, df.size());
        }
        catch (Exception e)
        {
            fail();
        }
    }
    @Test
    void testIlocFunctionMultiple(){
        try {
            SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                    new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)), new IntegerValue(0));
            df.add(new ArrayList<>(Arrays.asList("45", "0")));
            df.add(new ArrayList<>(Arrays.asList("0", "67")));
            df.add(new ArrayList<>(Arrays.asList("1", "0")));
            df.add(new ArrayList<>(Arrays.asList("0", "98")));
            DataFrame df2 = df.iloc(0, 2);
            assertEquals(3, df2.size());
            assertEquals(4, df.size());
            assertEquals("0", df2.get(2, 1).toString());
            assertEquals("67", df2.get(1, 1).toString());
            df2.add(new ArrayList<>(Arrays.asList("0", "7")));
            assertEquals(4, df2.size());
            assertEquals(4, df.size());
        }
        catch (Exception e)
        {
            fail();
        }
    }
    @Test
    void testToDense(){
        try {
            SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                    new ArrayList<>(Arrays.asList(IntegerValue.class, IntegerValue.class)), new IntegerValue(0));
            df.add(new ArrayList<>(Arrays.asList("0", "1")));
            df.add(new ArrayList<>(Arrays.asList("0", "8")));
            df.add(new ArrayList<>(Arrays.asList("5", "1")));
            df.add(new ArrayList<>(Arrays.asList("90", "0")));
            df.add(new ArrayList<>(Arrays.asList("5", "0")));
            df.add(new ArrayList<>(Arrays.asList("0", "0")));
            DataFrame df2 = df.toDense();
            assertEquals(6, df2.size());
            ArrayList<String> tmp = new ArrayList<>(Arrays.asList("0", "0", "5", "90", "5", "0"));
            assertEquals(tmp.get(0), df2.get("a").get(0).toString());
        }catch (Exception e)
        {
            fail();
        }
    }
    @Test
    void testReadingFileToSparseDataFrame(){
        try {
            SparseDataFrame df = new SparseDataFrame("TestFiles\\sparse.csv",
                    new ArrayList<>(Arrays.asList(DoubleValue.class, DoubleValue.class, DoubleValue.class)), new DoubleValue(0.0));
            assertEquals(300, df.size());
            assertEquals("0.5806866214364547", df.get(0, 2).toString());
            assertEquals("0.8420230750119814", df.get(4, 0).toString());
            assertEquals("0.0", df.get(7, 1).toString());
        }
        catch (Exception e)
        {
            fail();
        }
    }


}