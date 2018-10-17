package Data;

import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SparseDataFrameTest {
    @Test
    void addingCorrectRowsTest(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a","b"},
                new String[]{"Integer", "Integer"},0);
        df.add(new ArrayList<>(Arrays.asList(1, 0)));
        df.add(new ArrayList<>(Arrays.asList(0, 26)));
        df.add(new ArrayList<>(Arrays.asList(0, 6)));
        df.add(new ArrayList<>(Arrays.asList(0, 0)));
        assertEquals(4,df.size());
        assertEquals(0,df.get(2,0));
        assertEquals(26,df.get(1,1));
        assertEquals(null,df.get(5,0));
    }
    @Test
    void addingInvalidRowsDoesNothing(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a","b"},
                new String[]{"Integer", "Integer"},0);
        df.add(new ArrayList<>(Arrays.asList(false, 0)));
        df.add(new ArrayList<>(Arrays.asList(0, 26,"iaubwd")));
        df.add(new ArrayList<>(Arrays.asList(0, 6)));
        df.add(new ArrayList<>(Arrays.asList(0, 0,"other")));
        assertEquals(1,df.size());
        assertEquals(6,df.get(0,1));
    }
    @Test
    void testgetFunctionThatReturnsColumn(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"kol1", "kol2", "kol3"},
                new String[]{"Double", "Double", "Double"},0.0);
        df.add(new ArrayList<>(Arrays.asList(1.78, 2.5, 0.0)));
        df.add(new ArrayList<>(Arrays.asList(7.13, 0.0,5.21)));
        df.add(new ArrayList<>(Arrays.asList(5.65, 0.0,0.0)));
        df.add(new ArrayList<>(Arrays.asList(0.0, 17.48,0.0)));
        df.add(new ArrayList<>(Arrays.asList(0.0, 0.0,2.0)));
        ArrayList<C00Value> temp = new ArrayList<C00Value>();
        temp.addAll(Arrays.asList(new C00Value(5.21,1),new C00Value(2.0,4)));
        assertEquals(temp.get(0).data,df.get("kol3").get(0).data);
        assertEquals(temp.get(0).row,df.get("kol3").get(0).row);
        assertEquals(temp.get(1).data,df.get("kol3").get(1).data);
        assertEquals(temp.get(0).row,df.get("kol3").get(0).row);
    }
    @Test
    void testgetFunctionThatReturnsDataFrameShallowCopy(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"1", "2","3"},
                new String[]{"Character","Character","Character"},'a');
        df.add(new ArrayList<>(Arrays.asList('a', 'c','w')));
        df.add(new ArrayList<>(Arrays.asList('a', 'a','w')));
        assertEquals('w',df.get(0,2));
        assertEquals(2,df.size());
        DataFrame df2 = df.get(new String[]{"1","3"},false);
        assertEquals(2,df2.size());
        assertEquals('a',df2.get(0,0));
        assertEquals('w',df2.get(0,1));
        df2.add(new ArrayList<>(Arrays.asList('v','o')));
        assertEquals(3,df2.size());
        assertEquals(3,df.get("3").size());
    }
    @Test
    void testgetFunctionThatReturnsDataFrameDeepCopy() {
        SparseDataFrame df = new SparseDataFrame(new String[]{"1", "2", "3"},
                new String[]{"Character", "Character", "Character"}, 'a');
        df.add(new ArrayList<>(Arrays.asList('a', 'c', 'w')));
        df.add(new ArrayList<>(Arrays.asList('a', 'a', 'w')));
        assertEquals('w', df.get(0, 2));
        assertEquals(2, df.size());
        DataFrame df2 = df.get(new String[]{"1", "3"}, true);
        assertEquals(2, df2.size());
        assertEquals('a', df2.get(0, 0));
        assertEquals('w', df2.get(0, 1));
        df2.add(new ArrayList<>(Arrays.asList('v', 'o')));
        assertEquals(3, df2.size());
        assertEquals(2,df.get("3").size());
    }
    @Test
    void testIlocFuncionOne(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                new String[]{"Boolean", "Boolean"},true);
        df.add(new ArrayList<>(Arrays.asList(true, false)));
        df.add(new ArrayList<>(Arrays.asList(false, false)));
        DataFrame df2 = df.iloc(1);
        assertEquals(2,df.size());
        assertEquals(1,df2.size());
        assertEquals(false ,df2.get(0,1));
        df2.add(new ArrayList<>(Arrays.asList(true, false)));
        df2.add(new ArrayList<>(Arrays.asList(true, true)));
        assertEquals(3,df2.size());
        assertEquals(2,df.size());
    }
    @Test
    void testIlocFunctionMultiple(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                new String[]{"Character", "Character"},'b');
        df.add(new ArrayList<>(Arrays.asList('a', 'b')));
        df.add(new ArrayList<>(Arrays.asList('b', 'i')));
        df.add(new ArrayList<>(Arrays.asList('d', 'b')));
        df.add(new ArrayList<>(Arrays.asList('b', 'w')));
        DataFrame df2 = df.iloc(0,2);
        assertEquals(3,df2.size());
        assertEquals(4,df.size());
        assertEquals('b',df2.get(2,1));
        assertEquals('i',df2.get(1,1));
        df2.add(new ArrayList<>(Arrays.asList('b', 'o')));
        assertEquals(4,df2.size());
        assertEquals(4,df.size());
    }
    @Test
    void testToDense(){
        SparseDataFrame df = new SparseDataFrame(new String[]{"a", "b"},
                new String[]{"Integer", "Integer"},0);
        df.add(new ArrayList<>(Arrays.asList(0, 1)));
        df.add(new ArrayList<>(Arrays.asList(0, 8)));
        df.add(new ArrayList<>(Arrays.asList(5, 1)));
        df.add(new ArrayList<>(Arrays.asList(90,0 )));
        df.add(new ArrayList<>(Arrays.asList(5, 0)));
        df.add(new ArrayList<>(Arrays.asList(0, 0)));
        DataFrame df2 = df.toDense();
        assertEquals(6,df2.size());
        ArrayList<Object> tmp = new ArrayList<>(Arrays.asList(0,0,5,90,5,0));
        assertEquals(tmp, df2.get("a"));
    }
    @Test
    void testReadingFileToSparseDataFrame(){
        SparseDataFrame df = new SparseDataFrame("TestFiles\\sparse.csv", new String[]{"Double","Double","Double"},
                true,0.0);
        assertEquals(300,df.size());
        assertEquals(0.5806866214364547,df.get(0,2));
        assertEquals(0.8420230750119814,df.get(4,0));
        assertEquals(0.0,df.get(7,1));
    }


}