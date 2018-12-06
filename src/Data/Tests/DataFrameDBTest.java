package Data.Tests;

import Data.DataFrame;
import Data.DataFrameDB;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameDBTest {
    private String username= "";
    private String password= "";
    private String url="";

    @Test
    void printWholeTable(){
        try{
            DataFrameDB db=new DataFrameDB(url,username,password);
            String expected="          isbn          title         author           year \n" +
                    " 1234567891234 W poszukiwaniu straconego czasu  Marcel Proust           2008 \n" +
                    " 1234567891235 Komu bije dzwon Ernest Hemingway           2008 \n" +
                    " 1234567891236 Slonce tez wstaje Ernest Hemingway           2008 \n" +
                    " 1234567891237 Stary czlowiek i morze Ernest Hemingway           2008 \n" +
                    " 1234567891238 Bracia Karamazow Fiodor Dostojewski           2008 \n" +
                    " 1234567891239 Zbrodnia i kara Fiodor Dostojewski           2008 \n" +
                    " 1234567891240 Kronika Ptaka nakrecacza Haruki Murakami           2008 \n" +
                    " 1234567891241 Tancz tancz tancz Haruki Murakami           2008 \n" +
                    " 1234567891242 Przygoda z owca Haruki Murakami           2008 \n" +
                    " 1234567891243          Snieg    Orhan Pamuk           2004 \n" +
                    " 1234567891244 Nazwyam sie Czerwien    Orhan Pamuk           2008 \n" +
                    " 1234567891245     Nowe zycie    Orhan Pamuk           2008 \n" +
                    " 1234567891246        Solaris  Stanislaw Lem           2008 \n" +
                    " 1234567891247 Nowy wspanialy swiat  Aldous Huxley           2007 \n" +
                    " 1234567891248       Rok 1984  George Orwell           2003 \n" +
                    " 1234567891249 Mechaniczna pomarancza Antoni Burgess           1999 \n" +
                    " 1234567891250          tytul             Ja           2018 \n" +
                    " 1234567891251    drugi tytu?             Ja           2017 \n";

            assertEquals(expected,DataFrameDB.select(db,"*","books","","").toString());
        }catch (Exception e){
            fail();
        }

    }
    @Test
    void testEfficiency(){
        try {
            DataFrameDB db = new DataFrameDB(url, username, password);
            DataFrame df = DataFrameDB.select(db,"*","books","","");
            long StartTimeDF = System.nanoTime();
            df.groupby("author").max();
            long EndTimeDF = System.nanoTime();
            long StartTimeDB = System.nanoTime();
            db.max(new ArrayList<>(Arrays.asList("author")),"books");
            long EndTimeDB = System.nanoTime();
            System.out.println("Max");
            System.out.println("Czas dla DataFrame:   "+ (EndTimeDF-StartTimeDF) + " nanoseconds");
            System.out.println("Czas dla DataFrameDB: "+ (EndTimeDB-StartTimeDB)+ " nanoseconds");

            StartTimeDF = System.nanoTime();
            df.groupby("author").min();
            EndTimeDF = System.nanoTime();
            StartTimeDB = System.nanoTime();
            db.min(new ArrayList<>(Arrays.asList("author")),"books");
            EndTimeDB = System.nanoTime();
            System.out.println("Min");
            System.out.println("Czas dla DataFrame:   "+ (EndTimeDF-StartTimeDF) + " nanoseconds");
            System.out.println("Czas dla DataFrameDB: "+ (EndTimeDB-StartTimeDB)+ " nanoseconds");
        }catch (Exception e){
            fail();
        }
    }

}