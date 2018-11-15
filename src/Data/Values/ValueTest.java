package Data.Values;

import Data.Values.exceptions.CannotCreateValueFromString;
import Data.Values.exceptions.InvalidOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueTest {
    @Test
    void toStringTests(){
        IntegerValue tmp = new IntegerValue(25);
        IntegerValue tmp2 = new IntegerValue(0.25);
        DoubleValue tmp3 = new DoubleValue((float)0.56);
        DoubleValue tmp4 = new DoubleValue(5);
        FloatValue tmp5 = new FloatValue(12);
        FloatValue tmp6 = new FloatValue((double)5.61);
        StringValue tmp7 = new StringValue("lkjwnf");
        DateTimeValue tmp8 = new DateTimeValue(678321);
        DateTimeValue tmp9;
        try {
            tmp9 = new DateTimeValue("1999-10-16");
            assertEquals("1999-10-16",tmp9.toString());
        }
        catch (CannotCreateValueFromString e){
            fail();
        }
        assertEquals("25",tmp.toString());
        assertEquals("5.0",tmp4.toString());
        assertEquals("lkjwnf",tmp7.toString());
    }
    @Test
    void calculatingTest(){
        IntegerValue tmp = new IntegerValue(25);
        DoubleValue tmp2 = new DoubleValue(0.56);
        FloatValue tmp3 = new FloatValue(5.61);
        try {
            assertEquals("25", tmp.add(tmp2).toString());
            assertEquals("6.17", tmp3.add(tmp2).toString());
            assertEquals("14", tmp.mul(tmp2).toString());
            assertEquals("3.1416000000000004", tmp2.mul(tmp3).toString());
            assertEquals("0.022400000000000003", tmp2.div(tmp).toString());
            assertEquals("6", tmp.pow(tmp2).toString());
        }
        catch (InvalidOperation e){
            fail();
        }
    }
    @Test
    void compareTests(){
        IntegerValue tmp = new IntegerValue(25);
        IntegerValue tmp2 = new IntegerValue(0.25);
        DoubleValue tmp3 = new DoubleValue((float)0.56);
        DoubleValue tmp4 = new DoubleValue(5);
        FloatValue tmp5 = new FloatValue(12);
        FloatValue tmp6 = new FloatValue((double)5.61);
        StringValue tmp7 = new StringValue("lkjwnf");
        DateTimeValue tmp8 = new DateTimeValue(678321);
        try {
            DateTimeValue tmp9 = new DateTimeValue("1999-10-16");
            assertTrue(tmp8.lessOrEquals(tmp9));
        }
        catch (CannotCreateValueFromString e){
            fail();
        }
        String a = "56";
        String b = "2.0";
        assertTrue(tmp.greaterOrEquals(tmp2));
        assertTrue(tmp4.lessOrEquals(tmp5));
        assertTrue(tmp2.notEquals(tmp3));
        assertFalse(tmp6.lessOrEquals(tmp3));
    }

}