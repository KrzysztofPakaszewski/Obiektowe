package Data.Tests;

import Data.DataFrame;
import Data.DataFrameDB;
import Data.DataFrameMultiThread;
import Data.Values.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataFramesTimeTest {
    private String username="";
    private String password="";
    private String url ="";
    void testSingleThread(DataFrame df,ArrayList<Double> time)throws Exception{
        for(int b=0;b< columns.size();b++) {
            long StartTime = System.nanoTime();
            for (int a = 0; a < 10; a++) {
                df.groupby(columns.get(b)).max();
            }
            long EndTime = System.nanoTime();
            time.add(((double)(EndTime-StartTime)/10)/1000000000);
        }
    }
    void testMultiThread(DataFrameMultiThread df,ArrayList<Double> time)throws Exception{
        for(int b=0;b< columns.size();b++) {
            long StartTime = System.nanoTime();
            for (int a = 0; a < 10; a++) {
                df.groupby(columns.get(b)).max();
            }
            long EndTime = System.nanoTime();
            time.add(((double)(EndTime-StartTime)/10)/1000000000);
        }
    }
    void testSQL(String url, String user,String psw,String tab,ArrayList<Double> time)throws Exception{
        DataFrameDB db = new DataFrameDB(url,user,psw);
        for(int b=0;b< columns.size();b++) {
            long StartTime = System.nanoTime();
            for (int a = 0; a < 10; a++) {
                db.max(new ArrayList<>(Arrays.asList(columns.get(b))), tab);
            }
            long EndTime = System.nanoTime();
            time.add(((double)(EndTime-StartTime)/10)/1000000000);
        }
    }
    void reset()throws Exception{
        PrintWriter pw = new PrintWriter(new File("TestFiles\\TimeTest.csv"));
        pw.write("type,1mln by id,1mnl by date,3mln by id,3mln by date,5mln by id,5mln by date\n");
        pw.close();
    }
    ArrayList<String> paths= new ArrayList<>(Arrays.asList("TestFiles\\groupby.csv","TestFiles\\large_groupby.csv",
            "TestFiles\\large_groupby5.csv"));
    ArrayList<Class<? extends Value>> typesgroupby= new ArrayList<>(Arrays.asList(StringValue.class,
            DateTimeValue.class, DoubleValue.class, DoubleValue.class));
    ArrayList<Class<? extends Value>>typesofLarge= new ArrayList<>(Arrays.asList(IntegerValue.class,
            DateTimeValue.class, StringValue.class,DoubleValue.class, DoubleValue.class));
    ArrayList<String> columns= new ArrayList<>(Arrays.asList("id","date"));
    @Test
    void dataFrameTest1mln(){
        try {
            reset();
            ArrayList<Double> time= new ArrayList<>();
            testSingleThread(new DataFrame(paths.get(0),typesgroupby),time);
            FileWriter fw = new FileWriter(new File("TestFiles\\TimeTest.csv"),true);
            PrintWriter pw = new PrintWriter(fw);
            String build= "";
            for(int a=0;a<time.size();a++){
                build +=","+ time.get(a);
            }
            pw.write("single thread"+build);
            pw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
    @Test
    void dataFrameTest3mln(){
        try {
            ArrayList<Double> time= new ArrayList<>();
            testSingleThread(new DataFrame(paths.get(1),typesofLarge),time);
            FileWriter fw = new FileWriter(new File("TestFiles\\TimeTest.csv"),true);
            PrintWriter pw = new PrintWriter(fw);
            String build= "";
            for(int a=0;a<time.size();a++){
                build +=","+ time.get(a);
            }
            pw.write(build);
            pw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
    @Test
    void dataFrameTest5mln(){
        try {
            ArrayList<Double> time= new ArrayList<>();
            testSingleThread(new DataFrame(paths.get(2),typesofLarge),time);
            FileWriter fw = new FileWriter(new File("TestFiles\\TimeTest.csv"),true);
            PrintWriter pw = new PrintWriter(fw);
            String build= "";
            for(int a=0;a<time.size();a++){
                build +=","+ time.get(a);
            }
            pw.write(build+"\n");
            pw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
    ArrayList<String>tables = new ArrayList<>(Arrays.asList("1mln","3mln","5mln"));
    @Test
    void dataFrameDBTest(){
        try {
            ArrayList<Double> time= new ArrayList<>();
            for(int a=0;a< tables.size();a++){
                testSQL(url,username,password, tables.get(a),time);
            }
            FileWriter fw = new FileWriter(new File("TestFiles\\TimeTest.csv"),true);
            PrintWriter pw = new PrintWriter(fw);
            String build= "";
            for(int a=0;a<time.size();a++){
                build +=","+ time.get(a);
            }
            pw.write("SQL"+build+"\n");
            pw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
    //Multi thread,5.2329443559,3.2520239343000004
    @Test
    void dataFrameMTTest1mln(){
        try {
            ArrayList<Double> time= new ArrayList<>();
            testMultiThread(new DataFrameMultiThread(paths.get(0),typesgroupby),time);
            FileWriter fw = new FileWriter(new File("TestFiles\\TimeTest.csv"),true);
            PrintWriter pw = new PrintWriter(fw);
            String build= "";
            for(int a=0;a<time.size();a++){
                build +=","+ time.get(a);
            }
            pw.write("Multi thread"+build);
            pw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
    @Test
    void dataFrameMTTest3mln(){
        try {
            ArrayList<Double> time= new ArrayList<>();
            testMultiThread(new DataFrameMultiThread(paths.get(1),typesofLarge),time);
            FileWriter fw = new FileWriter(new File("TestFiles\\TimeTest.csv"),true);
            PrintWriter pw = new PrintWriter(fw);
            String build= "";
            for(int a=0;a<time.size();a++){
                build +=","+ time.get(a);
            }
            pw.write(build);
            pw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
    @Test
    void dataFrameMTTest5mln(){
        try {
            ArrayList<Double> time= new ArrayList<>();
            testMultiThread(new DataFrameMultiThread(paths.get(2),typesofLarge),time);
            FileWriter fw = new FileWriter(new File("TestFiles\\TimeTest.csv"),true);
            PrintWriter pw = new PrintWriter(fw);
            String build= "";
            for(int a=0;a<time.size();a++){
                build +=","+ time.get(a);
            }
            pw.write(build+"\n");
            pw.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }
}