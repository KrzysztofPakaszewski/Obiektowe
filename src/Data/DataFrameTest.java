package Data;

import Data.Values.DoubleValue;
import Data.Values.IntegerValue;
import Data.Values.StringValue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {
    @Test
    void addingCorrectRowsTest(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, DoubleValue.class, StringValue.class)));
        df.add(new ArrayList<String>(Arrays.asList("1", "2.5", "auwn")));
        df.add(new ArrayList<>(Arrays.asList("5", "5.23","other")));
        df.add(new ArrayList<>(Arrays.asList("23", "12.42","next")));
        df.add(new ArrayList<>(Arrays.asList("832", "14.2","teststring")));
        assertEquals(4,df.size());
        assertEquals("1",df.get(0,0).toString());
        assertEquals("next",df.get(2,2).toString());
        assertEquals("14.2",df.get(3,1).toString());
        assertEquals(null,df.get(5,0));
    }
    @Test
    void addingInvalidRowsDoesNothing(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, DoubleValue.class, StringValue.class)));
        df.add(new ArrayList<>(Arrays.asList("1", "2.5", "auwn")));
        df.add(new ArrayList<>(Arrays.asList("5", "5.23","other")));
        df.add(new ArrayList<>(Arrays.asList("5", "5.23","other","34")));
        df.add(new ArrayList<>(Arrays.asList("5", "bje","23")));
        df.add(new ArrayList<>(Arrays.asList("5", "5.23")));
        df.add(new ArrayList<>(Arrays.asList("7.56", "5.23","other")));
        assertEquals(2,df.size());
        assertEquals(null,df.get(2,0));
    }
    @Test
    void testgetFunctionThatReturnsColumn(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, DoubleValue.class, StringValue.class)));
        df.add(new ArrayList<>(Arrays.asList("1", "2.5", "auwn")));
        df.add(new ArrayList<>(Arrays.asList("5", "89.12","other")));
        df.add(new ArrayList<>(Arrays.asList("5", "1.5","other")));
        df.add(new ArrayList<>(Arrays.asList("5", "17.48")));
        df.add(new ArrayList<>(Arrays.asList("5", "90.01","uyguyw")));
        ArrayList<Object> temp = new ArrayList<Object>();
        temp.addAll(Arrays.asList("2.5", "89.12", "1.5", "90.01"));
        assertEquals(temp.get(0),df.get("kol2").get(0).toString());
        assertEquals(temp.get(1),df.get("kol2").get(1).toString());
        assertEquals(temp.get(2),df.get("kol2").get(2).toString());
        assertEquals(temp.get(3),df.get("kol2").get(3).toString());
    }
    @Test
    void testgetFunctionThatReturnsDataFrameShallowCopy(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, DoubleValue.class, IntegerValue.class)));
        df.add(new ArrayList<>(Arrays.asList("1", "2.5","3")));
        df.add(new ArrayList<>(Arrays.asList("0", "1.75","78")));
        assertEquals(2,df.size());
        DataFrame df2 = df.get(new String[]{"kol1","kol3"},false);
        assertEquals(2,df2.size());
        assertEquals("1",df2.get(0,0).toString());
        df2.add(new ArrayList<>(Arrays.asList("1", "7")));
        assertEquals(3,df2.size());
        assertEquals(3,df.size());
    }
    @Test
    void testgetFunctionThatReturnsDataFrameDeepCopy(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, DoubleValue.class, IntegerValue.class)));
        df.add(new ArrayList<>(Arrays.asList("1", "2.5","3")));
        df.add(new ArrayList<>(Arrays.asList("0", "1.75","78")));
        assertEquals(2,df.size());
        DataFrame df2 = df.get(new String[]{"kol1","kol2"},true);
        assertEquals(2,df2.size());
        assertEquals("1",df2.get(0,0).toString());
        df2.add(new ArrayList<>(Arrays.asList("1", "2.5")));
        assertEquals(3,df2.size());
        assertEquals(2,df.size());
    }
    @Test
    void testIlocFuncionOne(){
        DataFrame df = new DataFrame(new String[]{"a", "b"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, StringValue.class)));
        df.add(new ArrayList<>(Arrays.asList("1", "b")));
        df.add(new ArrayList<>(Arrays.asList("0", "i")));
        DataFrame df2 = df.iloc(1);
        assertEquals(1,df2.size());
        assertEquals(2,df.size());
        assertEquals("i" ,df2.get(0,1).toString());
        df2.add(new ArrayList<>(Arrays.asList("1", "q")));
        df2.add(new ArrayList<>(Arrays.asList("1", "w")));
        assertEquals(3,df2.size());
        assertEquals(2,df.size());
    }
    @Test
    void testIlocFunctionMultiple(){
        DataFrame df = new DataFrame(new String[]{"a", "b"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, StringValue.class)));
        df.add(new ArrayList<>(Arrays.asList("1", "b")));
        df.add(new ArrayList<>(Arrays.asList("0", "i")));
        df.add(new ArrayList<>(Arrays.asList("1", "q")));
        df.add(new ArrayList<>(Arrays.asList("1", "w")));
        DataFrame df2 = df.iloc(0,2);
        assertEquals(3,df2.size());
        assertEquals(4,df.size());
        assertEquals("q",df2.get(2,1).toString());
        assertEquals("i",df2.get(1,1).toString());
        df2.add(new ArrayList<>(Arrays.asList("0", "o")));
        assertEquals(4,df2.size());
        assertEquals(4,df.size());
    }
    @Test
    void testReadingFilesToDataFrame(){
        DataFrame df = new DataFrame("TestFiles\\data.csv",
                new ArrayList<>(Arrays.asList(DoubleValue.class, DoubleValue.class, DoubleValue.class)),
                true);
        assertEquals(1000000,df.size());
        assertEquals("-2000.0",df.get(0,1).toString());
        assertEquals("4.000004000004E-4",df.get(4,0).toString());
        assertEquals("-1999.995999996",df.get(2,1).toString());
    }

}