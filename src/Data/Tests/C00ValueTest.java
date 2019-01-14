package Data.Tests;

import Data.C00Value;
import Data.Values.DoubleValue;
import Data.Values.IntegerValue;
import Data.Values.StringValue;


import static org.junit.jupiter.api.Assertions.*;

class C00ValueTest {

    @org.junit.jupiter.api.Test
    void TestingC00Value() {
        C00Value val = new C00Value(new IntegerValue(5),2);
        C00Value val2 = new C00Value(new StringValue("str"),5);
        C00Value val3 = new C00Value(new DoubleValue(12.0),8);
        C00Value val4 = new C00Value(new IntegerValue(2),1);
        // Values
        assertEquals("5",val.getData().toString());
        assertEquals("str",val2.getData().toString());
        assertEquals("12.0",val3.getData().toString());
        assertEquals("2",val4.getData().toString());
        // Rows
        assertEquals(2,val.getRow());
        assertEquals(5,val2.getRow());
        assertEquals(8,val3.getRow());
        assertEquals(1,val4.getRow());

    }
}