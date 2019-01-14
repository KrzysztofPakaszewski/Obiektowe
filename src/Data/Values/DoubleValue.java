package Data.Values;

import Data.exceptions.CannotCreateValueFromString;
import Data.exceptions.InvalidOperation;

public class DoubleValue extends Value{
    private final double data;
    public DoubleValue(int a){
        data = (double)a;
    }
    public DoubleValue(double a){
        data = a;
    }
    public DoubleValue(float a){
        data = (double)a;
    }
    public String toString(){
        return ((Double)data).toString();
    }
    public  Value add(Value v) throws InvalidOperation{
        try{
            return new DoubleValue(data + Double.parseDouble(v.toString()));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("add","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

        }
    }
    public  Value sub(Value v)throws InvalidOperation{
        try{
        return new DoubleValue(data - Double.parseDouble(v.toString()));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("sub","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

        }
    }
    public  Value mul(Value v)throws InvalidOperation{
        try{
        return new DoubleValue(data * Double.parseDouble(v.toString()));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("mul","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

        }
    }
    public  Value div(Value v)throws InvalidOperation{
        try{
        return new DoubleValue(data / Double.parseDouble(v.toString()));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("div","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

        }
    }
    public  Value pow(Value v)throws InvalidOperation{
        try{
        return new DoubleValue( Math.pow(data, Double.parseDouble(v.toString())));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("pow","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

        }
    }
    public Value sqrt(){
        return new DoubleValue( Math.sqrt(data));
    }
    public  boolean equals(Value v){
        return this.toString().equals(v.toString());
    }
    public  boolean lessOrEquals(Value v){
        return data < Double.parseDouble(v.toString()) || this.equals(v);
    }
    public  boolean greaterOrEquals(Value v){
        return data > Double.parseDouble(v.toString()) || this.equals(v);
    }
    public  boolean notEquals(Value v){
        return !this.equals(v);
    }
    public  boolean equals(Object other){
        return equals((Value)other);
    }
    public  int hashCode(){
        return ((Double)data).hashCode();
    }
    public  Value create(String s)throws CannotCreateValueFromString {
        try{
        return new DoubleValue(java.lang.Double.valueOf(s));
        }
        catch (NumberFormatException e){
            throw new CannotCreateValueFromString("Argument is not a number: ",s);
        }
    }
}
