package Data.Tests;

import Data.DataFrame;
import Data.DataFrameMultiThread;
import Data.Values.DateTimeValue;
import Data.Values.DoubleValue;
import Data.Values.StringValue;
import Data.exceptions.CannotCreateValueFromString;
import Data.exceptions.Error;
import Data.exceptions.InvalidData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameMultiThreadTest {

    @Test
    void testingMax(){
        try{
            DataFrameMultiThread dfm = new DataFrameMultiThread("TestFiles\\groupby.csv",
                    new ArrayList<>(Arrays.asList(StringValue.class,
                            DateTimeValue.class, DoubleValue.class, DoubleValue.class)));
            DataFrameMultiThread.Grouped grp= dfm.groupbyMT("id");

            System.out.println(grp.max().toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
    @Test
    void test() {
        try {
            DataFrame df = new DataFrame("TestFiles\\groupby.csv",
                    new ArrayList<>(Arrays.asList(StringValue.class,
                            DateTimeValue.class, DoubleValue.class, DoubleValue.class)));
            DataFrameMultiThread dfm =new DataFrameMultiThread(df);
            String dfS, dfmS;
            long StartTimeDFS = System.nanoTime();
            dfS = df.groupby("id").max().toString();
            long EndTimeDFS = System.nanoTime();
            long StartTimeDFMS = System.nanoTime();
            dfmS= dfm.groupby("id").max().toString();
            long EndTimeDFMS = System.nanoTime();
            assertEquals(dfS,dfmS);
            System.out.println("One Thread time:  \t"+(EndTimeDFS-StartTimeDFS));
            System.out.println("Multi Thread time:\t"+(EndTimeDFMS-StartTimeDFMS));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }

    }
}