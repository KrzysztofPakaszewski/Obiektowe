package Data.Values;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeValue extends Value {
    private SimpleDateFormat DateFormat = new SimpleDateFormat("y MM dd HH:mm:ss");
    private final Date data;
    public DateTimeValue(long a){
        data = new Date(a);
    }
    public DateTimeValue(){
        data = new Date();
    }
    public DateTimeValue(float a){
        data = new Date((long)a);
    }
    public DateTimeValue(int a){
        data = new Date(a);
    }
    public DateTimeValue(double a){
        data = new Date((long)a);
    }
    public DateTimeValue(String a){
        try{
            data= DateFormat.parse(a);
        }
        catch (ParseException exc){
            throw new RuntimeException("Unparseable string",exc);
        }

    }
    public String toString(){
        return DateFormat.format(data);
    }
    public  Value add(Value v) {
        return null;
    }
    public  Value sub(Value v){
        return null;
    }
    public  Value mul(Value v){
        return null;
    }
    public  Value div(Value v){
        return null;
    }
    public  Value pow(Value v){
        return null;
    }
    public  boolean equals(Value v){
        return this.toString().equals(v.toString());
    }
    public  boolean lessOrEquals(Value v){
        Date temp ;
        try{
            temp= DateFormat.parse(v.toString());
        }
        catch (ParseException exc){
            throw new RuntimeException("Unparseable string",exc);
        }
        return data.compareTo(temp) <0 || this.equals(v);
    }
    public  boolean greaterOrEquals(Value v){Date temp ;
        try{
            temp= DateFormat.parse(v.toString());
        }
        catch (ParseException exc){
            throw new RuntimeException("Unparseable string",exc);
        }
        return data.compareTo(temp) >0 || this.equals(v);
    }
    public  boolean notEquals(Value v){
        return !this.equals(v);
    }
    public  boolean equals(Object other){
        return data.equals(other);
    }
    public  int hashCode(){
        return data.hashCode();
    }
    public  Value create(String s){
        return new DateTimeValue(s);
    }
}
