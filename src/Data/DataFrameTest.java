package Data;

import Data.Values.DateTimeValue;
import Data.Values.DoubleValue;
import Data.Values.IntegerValue;
import Data.Values.StringValue;
import Data.Values.exceptions.InvalidData;
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
    @Test
    void testGroupByMethod(){
        DataFrame df = new DataFrame(new String[]{"a", "b","c"},
                new ArrayList<>(Arrays.asList(IntegerValue.class, StringValue.class,IntegerValue.class)));
        df.add(new ArrayList<>(Arrays.asList("1", "a","5")));
        df.add(new ArrayList<>(Arrays.asList("1", "b","6")));
        df.add(new ArrayList<>(Arrays.asList("1", "a","7")));
        df.add(new ArrayList<>(Arrays.asList("1", "c","8")));
        df.add(new ArrayList<>(Arrays.asList("1", "d","9")));
        df.add(new ArrayList<>(Arrays.asList("2", "e","10")));
        df.add(new ArrayList<>(Arrays.asList("2", "e","11")));
        df.add(new ArrayList<>(Arrays.asList("2", "e","12")));
        df.add(new ArrayList<>(Arrays.asList("2", "f","13")));
        df.add(new ArrayList<>(Arrays.asList("3", "g","14")));
        df.add(new ArrayList<>(Arrays.asList("3", "h","15")));
        df.add(new ArrayList<>(Arrays.asList("3", "i","16")));
    }
    @Test
    void testGroubBy(){
        DataFrame df = new DataFrame("TestFiles\\groupby.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        String max="            id           date          total            val \n" +
                "             a     2258-11-19 4.346642001099709 4217.868976728472 \n" +
                "             b     2258-11-19 4.807014586734234 4346.9719668735615 \n" +
                "             c     2258-11-19 3.960721375679458 4675.680484845829 \n" +
                "             d     2258-11-19 4.274853518537133 4407.648453539924 \n" +
                "             e     2258-11-19 4.4547269076092615 4435.7321384069755 \n" +
                "             f     2258-11-19 4.545008086656558 4222.350755792783 \n" +
                "             g     2258-11-19 3.9911859868397443 4067.8392638708215 \n" +
                "             h     2258-11-19 4.06528240097237 5158.377979273736 \n" +
                "             i     2258-11-19 4.335953952904688 4291.961777317559 \n" +
                "             j     2258-11-19 4.331044455231287 4443.200918877539 \n";
        String min="            id           date          total            val \n" +
                "             a     1985-02-04 -4.209783021005242 -4403.820876900747 \n" +
                "             b     1985-02-04 -5.587524007511189 -4664.020759403934 \n" +
                "             c     1985-02-04 -4.329755822351952 -4145.990922340222 \n" +
                "             d     1985-02-04 -4.316880714832668 -4517.0271418832135 \n" +
                "             e     1985-02-04 -4.145678790207904 -4327.537961306649 \n" +
                "             f     1985-02-04 -4.164148653906187 -4096.993092302666 \n" +
                "             g     1985-02-04 -4.292637695527077 -3956.074803573609 \n" +
                "             h     1985-02-04 -4.137013302383809 -4555.71580506026 \n" +
                "             i     1985-02-04 -4.113436325830335 -4122.294574402988 \n" +
                "             j     1985-02-04 -4.4534804420164456 -5205.645177392999 \n";
        String mean="            id          total            val \n" +
                "             a 3.132543408247686E-4 2.5086060150764435 \n" +
                "             b -0.0029217169301415315 -3.324249725633629 \n" +
                "             c -0.0020268035145300217 1.2632324759347489 \n" +
                "             d -0.00466556008262599 0.09650491001811846 \n" +
                "             e 0.0029916365701851038 -1.6470754989695315 \n" +
                "             f 0.008399816403964979 2.0521033637926007 \n" +
                "             g 0.003503310718187648 -1.092516194367054 \n" +
                "             h -0.0017555921481194696 6.633511026570021 \n" +
                "             i -0.006876525697398157 -2.2446430463287954 \n" +
                "             j 3.0751172395176567E-4 -1.3069591970544758 \n";
        String std="            id          total            val \n" +
                "             a 1.0034851856889877 997.0884034308415 \n" +
                "             b 1.0012920183346032 998.0542274063642 \n" +
                "             c 1.003649945910948 1003.8207205561268 \n" +
                "             d 1.0017116378387145 999.201079519034 \n" +
                "             e 0.9981821460843147 998.2236914316202 \n" +
                "             f 0.9985637715078177 997.20407921484 \n" +
                "             g 1.0010579295553128 1000.953145880962 \n" +
                "             h 0.9982140615171747 1003.5903444815388 \n" +
                "             i 1.0023043577406485 1003.7547707819875 \n" +
                "             j 1.0005439912414695 1003.1756917328001 \n";
        String var="            id          total            val \n" +
                "             a 1.006982517897262 994185.2842562645 \n" +
                "             b 1.0025857059805836 996112.2408437146 \n" +
                "             c 1.0073132139270489 1007656.0390178217 \n" +
                "             d 1.00342620538152 998402.797312003 \n" +
                "             e 0.9963675967614881 996450.5381353705 \n" +
                "             f 0.9971296057679172 994415.975602717 \n" +
                "             g 1.0021169783255697 1001907.2002489943 \n" +
                "             h 0.9964313126106138 1007193.5795365736 \n" +
                "             i 1.0046140255458937 1007523.6398676004 \n" +
                "             j 1.0010882784094097 1006361.468483582 \n";
        String sum="            id          total            val \n" +
                "             a 31.32543408247686 250860.60150764434 \n" +
                "             b -292.17169301415316 -332424.9725633629 \n" +
                "             c -202.68035145300215 126323.24759347488 \n" +
                "             d -466.55600826259905 9650.491001811846 \n" +
                "             e 299.1636570185104 -164707.54989695316 \n" +
                "             f 839.9816403964979 205210.33637926006 \n" +
                "             g 350.3310718187648 -109251.6194367054 \n" +
                "             h -175.55921481194696 663351.1026570022 \n" +
                "             i -687.6525697398157 -224464.30463287953 \n" +
                "             j 30.751172395176567 -130695.91970544758 \n";

        DataFrame.Grouped df2= df.groupby("id");
        try{
            assertEquals(max,df2.max().toString());
            assertEquals(min,df2.min().toString());
            assertEquals(mean,df2.mean().toString());
            assertEquals(std,df2.std().toString());
            assertEquals(var,df2.var().toString());
            assertEquals(sum,df2.sum().toString());
        }
        catch (InvalidData e){
            fail();
        }

    }
    @Test
    void testgroupbymulti(){
        DataFrame mainframe = new DataFrame("TestFiles\\groubymulti.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        DataFrame resultmax = new DataFrame("TestFiles\\results\\max.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        DataFrame resultmin = new DataFrame("TestFiles\\results\\min.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        DataFrame resultmean = new DataFrame("TestFiles\\results\\mean.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        DataFrame resultstd = new DataFrame("TestFiles\\results\\std.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        DataFrame resultsum = new DataFrame("TestFiles\\results\\sum.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        DataFrame resultvar = new DataFrame("TestFiles\\results\\var.csv",
                new ArrayList<>(Arrays.asList(StringValue.class,
                        DateTimeValue.class,DoubleValue.class,DoubleValue.class)),true);
        DataFrame.Grouped df2 = mainframe.groupby(new String[]{"id","date"});
        try {
            assertEquals(resultmax.toString(),df2.max().toString());
            assertEquals(resultmin.toString(),df2.min().toString());
            assertEquals(resultmean.toString(), df2.mean().toString());
            //assertEquals(resultstd.toString(),df2.std().toString());
            assertEquals(resultsum.toString(), df2.sum().toString());
            //assertEquals(resultvar.toString(),df2.var().toString());
        }
        catch (InvalidData e){
            fail();
        }
    }

}