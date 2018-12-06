package Data;

import Data.Values.*;
import Data.exceptions.CannotCreateValueFromString;
import Data.exceptions.Error;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFrameDB extends DataFrame{
    private Connection connection=null;
    public DataFrameDB(String url,String username, String password)throws Error{
        for(int a=0;a< 3 ;a++){
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection= DriverManager.getConnection(url,username,password);
                break;
            }
            catch (SQLException e){
                if(a==2){
                    throw new Error("Connection cannot be established");
                }
            }catch (Exception e){
                throw new Error("Class Driver not found");
            }
        }
    }
    static Class<? extends Value> convertToValue(String type){
        Pattern intpatter= Pattern.compile("INT");
        Matcher m = intpatter.matcher(type);
        if(m.find() || type.equals("DECIMAL")){
            return IntegerValue.class;
        }
        else if(type.equals("FLOAT"))
            return FloatValue.class;
        else if(type.equals("DOUBLE"))
            return DoubleValue.class;
        else if(type.equals("DATE")){
            return DateTimeValue.class;
        }
        else
            return StringValue.class;

    }
    public static DataFrame select(DataFrameDB db,String resultColumns, String table, String where,String groupby)throws Error, CannotCreateValueFromString {
        try {
            Statement stmt = db.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT "+resultColumns+" FROM "+table+" " + where +" "+ groupby);
            int colcount= rs.getMetaData().getColumnCount();
            ArrayList<Class<? extends Value>> types= new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            for(int a=1;a<colcount+1;a++){
                types.add(convertToValue(rs.getMetaData().getColumnTypeName(a)));
                names.add(rs.getMetaData().getColumnName(a));
            }
            DataFrame output = new DataFrame(names.toArray(new String[0]),types);
            while(rs.next()){
                ArrayList<String> tmp = new ArrayList<>();
                for(int a=1;a< colcount+1;a++){
                    tmp.add(rs.getString(a));
                }
                output.add(tmp);
            }
            return output;
        }catch (SQLException e){
            throw new Error(e.getMessage());
        }
    }
    public int size(String table)throws Error{
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + table);
            rs.next();
            return Integer.parseInt(rs.getString(1));
        }catch (SQLException e){
            throw new Error(e.getMessage());
        }
    }
    public DataFrame toDataFrame(String table)throws Error,CannotCreateValueFromString{
        return select(this,"*",table,"","");
    }
    public DataFrame min(ArrayList<String > columnGroupedBy,String table)throws Error,CannotCreateValueFromString{
        return operateOnGroupedColumns(columnGroupedBy,table,"min");
    }
    public DataFrame max(ArrayList<String> columnGroupedBy,String table)throws Error,CannotCreateValueFromString{
        return operateOnGroupedColumns(columnGroupedBy,table,"max");
    }
    public DataFrame groupby(ArrayList<String > columnGroupedBy,String table)throws Error, CannotCreateValueFromString{
        String groupedBy=new String();
        for(int a=0;a< columnGroupedBy.size();a++){
            if(a!=0){
                groupedBy+=", ";
            }
            groupedBy+= columnGroupedBy.get(a);
        }
        return select(this,"*",table,"","group by "+groupedBy);
    }
    public DataFrame operateOnGroupedColumns(ArrayList<String > columnGroupedBy,String table,String operation)throws Error,CannotCreateValueFromString{
        try {
            String columns=new String();
            String groupedBy= new String();
            Statement stmt = connection.createStatement();
            ResultSet rs= stmt.executeQuery("SELECT * from "+table+" limit 1");
            for (int a=1;a<rs.getMetaData().getColumnCount()+1;a++){
                if( a!=1){
                    columns+=", ";
                }
                if(columnGroupedBy.contains(rs.getMetaData().getColumnName(a))){
                    columns += rs.getMetaData().getColumnName(a) + " ";
                }
                else{
                    columns+=operation+"("+rs.getMetaData().getColumnName(a)+") ";
                }
            }
            for(int a=0;a< columnGroupedBy.size();a++){
                if(a!=0){
                    groupedBy+=", ";
                }
                groupedBy+= columnGroupedBy.get(a);
            }
            return select(this, columns, table, "", "group by " + groupedBy);
        }catch (SQLException e){
            throw new Error(e.getMessage());
        }
    }
    public DataFrame get(ArrayList<String> colnames,String table) throws Error, CannotCreateValueFromString{
        String columns = new String();
        for (int a=0;a<colnames.size();a++) {
            if (a != 0) {
                columns += ", ";
            }
            columns+= colnames.get(a);
        }
        return select(this,columns,table,"","");
    }
    boolean writeAsString(Class<? extends Value> type){
        if(type.isInstance(new StringValue("")) || type.isInstance(new DateTimeValue())){
            return true;
        }
        else
            return false;
    }
    public void add(ArrayList<String> values,String table,ArrayList<Class<? extends Value>> types)throws Error{
        if(values.size() != types.size())
            throw new Error("Size does not match");
        String val= new String();
        val = "(";
        for(int a=0;a< values.size();a++){
            if(a!=0){
                val+=",";
            }
            if(writeAsString(types.get(a))){
                val+= "'"+values.get(a)+"'";
            }
            else {
                val+= values.get(a);
            }
        }
        val+=") ";
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO "+table+" Values "+val);
        }catch (SQLException e){
            throw new Error(e.getMessage());
        }
    }
}
