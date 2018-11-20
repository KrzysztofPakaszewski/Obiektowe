package Data.Values;

import Data.exceptions.CannotCreateValueFromString;
import Data.exceptions.Error;
import Data.exceptions.InvalidOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeValue extends Value {
    public static SimpleDateFormat DateFormat = new SimpleDateFormat("y-MM-dd");
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
    public DateTimeValue(String a)throws CannotCreateValueFromString {
        try{
            data= DateFormat.parse(a);
        }
        catch (ParseException exc){
            throw new CannotCreateValueFromString("wrong format: ",a);
        }

    }
    public String toString(){
        return DateFormat.format(data);
    }
    public  Value add(Value v) throws InvalidOperation {
        throw new InvalidOperation("add","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());
    }
    public  Value sub(Value v)throws InvalidOperation{
        throw new InvalidOperation("sub","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public  Value mul(Value v)throws InvalidOperation{
        throw new InvalidOperation("mul","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public  Value div(Value v)throws InvalidOperation{
        throw new InvalidOperation("div","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public  Value pow(Value v)throws InvalidOperation{
        throw new InvalidOperation("pow","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public Value sqrt()throws InvalidOperation{
        throw new InvalidOperation("sqrt","("+this.getClass().getSimpleName()+")"
                +this.toString(),"")    ;

    }
    public  boolean equals(Value v){
        return this.toString().equals(v.toString());
    }
    public  boolean lessOrEquals(Value v)throws InvalidOperation{
        Date temp ;
        try{
            temp= DateFormat.parse(v.toString());
        }
        catch (ParseException exc){
            throw new InvalidOperation("<=",this.toString(),v.toString());
        }
        return data.compareTo(temp) <0 || this.equals(v);
    }
    public  boolean greaterOrEquals(Value v)throws InvalidOperation{
        Date temp ;
        try{
            temp= DateFormat.parse(v.toString());
        }
        catch (ParseException exc){
            throw new InvalidOperation("=>",this.toString(),v.toString());
        }
        return data.compareTo(temp) >0 || this.equals(v);
    }
    public  boolean notEquals(Value v){
        return !this.equals(v);
    }
    public  boolean equals(Object other){
        return equals((Value)other);
    }
    public  int hashCode(){
        return data.hashCode();
    }
    public  Value create(String s)throws CannotCreateValueFromString {
        return new DateTimeValue(s);
    }
}
