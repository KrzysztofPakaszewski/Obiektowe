package Data;

import static org.junit.jupiter.api.Assertions.*;

class C00ValueTest {

    @org.junit.jupiter.api.Test
    void TestingC00Value() {
        C00Value val = new C00Value(5,2);
        C00Value val2 = new C00Value("str",5);
        C00Value val3 = new C00Value(12.0,8);
        C00Value val4 = new C00Value(false,1);
        // Values
        assertEquals(5,val.getData());
        assertEquals("str",val2.getData());
        assertEquals(12.0,val3.getData());
        assertEquals(false,val4.getData());
        // Rows
        assertEquals(2,val.getRow());
        assertEquals(5,val2.getRow());
        assertEquals(8,val3.getRow());
        assertEquals(1,val4.getRow());

    }
}