package Data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {
    @Test
    void addingCorrectRowsTest(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new String[]{"Integer", "Double", "String"});
        df.add(new ArrayList<>(Arrays.asList(1, 2.5, "auwn")));
        df.add(new ArrayList<>(Arrays.asList(5, 5.23,"other")));
        df.add(new ArrayList<>(Arrays.asList(23, 12.42,"next")));
        df.add(new ArrayList<>(Arrays.asList(832, 14.2,"teststring")));
        assertEquals(4,df.size());
        assertEquals(1,df.get(0,0));
        assertEquals("next",df.get(2,2));
        assertEquals(14.2,df.get(3,1));
        assertEquals(null,df.get(5,0));
    }
    @Test
    void addingInvalidRowsDoesNothing(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new String[]{"Integer", "Double", "String"});
        df.add(new ArrayList<>(Arrays.asList(1, 2.5, "auwn")));
        df.add(new ArrayList<>(Arrays.asList(5, 5.23,"other")));
        df.add(new ArrayList<>(Arrays.asList(5, 5.23,"other",34)));
        df.add(new ArrayList<>(Arrays.asList(5, 5.23,23)));
        df.add(new ArrayList<>(Arrays.asList(5, 5.23)));
        assertEquals(2,df.size());
        assertEquals(null,df.get(2,0));
    }
    @Test
    void testingAddColumnFunction(){
        DataFrame df =new DataFrame(new String[]{"a", "b"},
                new String[]{"Integer", "String"});
        df.add(new ArrayList<>(Arrays.asList(1, "auwn")));
        df.addColumn("c","Double",new ArrayList<>(Arrays.asList(5.0)));
        assertEquals(5.0,df.get(0,2));
        df.add(new ArrayList<>(Arrays.asList(7, "owafg",8.22)));
        assertEquals(8.22,df.get(1,2));
    }
    @Test
    void testgetFunctionThatReturnsColumn(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new String[]{"Integer", "Double", "String"});
        df.add(new ArrayList<>(Arrays.asList(1, 2.5, "auwn")));
        df.add(new ArrayList<>(Arrays.asList(5, 89.12,"other")));
        df.add(new ArrayList<>(Arrays.asList(5, 1.5,"other")));
        df.add(new ArrayList<>(Arrays.asList(5, 17.48)));
        df.add(new ArrayList<>(Arrays.asList(5, 90.01,"uyguyw")));
        ArrayList<Object> temp = new ArrayList<Object>();
        temp.addAll(Arrays.asList(2.5, 89.12, 1.5, 90.01));
        assertEquals(temp,df.get("kol2"));
    }
    @Test
    void testgetFunctionThatReturnsDataFrameShallowCopy(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new String[]{"Boolean", "Double","Integer"});
        df.add(new ArrayList<>(Arrays.asList(true, 2.5,3)));
        df.add(new ArrayList<>(Arrays.asList(false, 1.75,78)));
        assertEquals(2,df.size());
        DataFrame df2 = df.get(new String[]{"kol1","kol3"},false);
        assertEquals(2,df2.size());
        assertEquals(true,df2.get(0,0));
        df2.add(new ArrayList<>(Arrays.asList(true, 7)));
        assertEquals(3,df2.size());
        assertEquals(3,df.size());
    }
    @Test
    void testgetFunctionThatReturnsDataFrameDeepCopy(){
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"},
                new String[]{"Boolean", "Double","Integer"});
        df.add(new ArrayList<>(Arrays.asList(true, 2.5,3)));
        df.add(new ArrayList<>(Arrays.asList(false, 1.75,78)));
        assertEquals(2,df.size());
        DataFrame df2 = df.get(new String[]{"kol1","kol2"},true);
        assertEquals(2,df2.size());
        assertEquals(true,df2.get(0,0));
        df2.add(new ArrayList<>(Arrays.asList(true, 2.5)));
        assertEquals(3,df2.size());
        assertEquals(2,df.size());
    }
    @Test
    void testIlocFuncionOne(){
        DataFrame df = new DataFrame(new String[]{"a", "b"},
                new String[]{"Boolean", "Character"});
        df.add(new ArrayList<>(Arrays.asList(true, 'b')));
        df.add(new ArrayList<>(Arrays.asList(false, 'i')));
        DataFrame df2 = df.iloc(1);
        assertEquals(1,df2.size());
        assertEquals(2,df.size());
        assertEquals('i' ,df2.get(0,1));
        df2.add(new ArrayList<>(Arrays.asList(true, 'q')));
        df2.add(new ArrayList<>(Arrays.asList(true, 'w')));
        assertEquals(3,df2.size());
        assertEquals(2,df.size());
    }
    @Test
    void testIlocFunctionMultiple(){
        DataFrame df = new DataFrame(new String[]{"a", "b"},
                new String[]{"Boolean", "Character"});
        df.add(new ArrayList<>(Arrays.asList(true, 'b')));
        df.add(new ArrayList<>(Arrays.asList(false, 'i')));
        df.add(new ArrayList<>(Arrays.asList(true, 'q')));
        df.add(new ArrayList<>(Arrays.asList(true, 'w')));
        DataFrame df2 = df.iloc(0,2);
        assertEquals(3,df2.size());
        assertEquals(4,df.size());
        assertEquals('q',df2.get(2,1));
        assertEquals('i',df2.get(1,1));
        df2.add(new ArrayList<>(Arrays.asList(false, 'o')));
        assertEquals(4,df2.size());
        assertEquals(4,df.size());
    }
    @Test
    void testReadingFilesToDataFrame(){
        DataFrame df = new DataFrame("TestFiles\\data.csv", new String[]{"Double","Double","Double"},
                true);
        assertEquals(1000000,df.size());
        assertEquals(-2000.0,df.get(0,1));
        assertEquals(0.0004000004000004,df.get(4,0));
        assertEquals(-1999.995999996,df.get(2,1));
    }

}