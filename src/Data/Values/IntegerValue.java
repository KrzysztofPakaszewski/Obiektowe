package Data.Values;

import Data.Values.exceptions.CannotCreateValueFromString;
import Data.Values.exceptions.InvalidOperation;

public class IntegerValue extends Value {
    private final java.lang.Integer data;
    public IntegerValue(int a){
        data = a;
    }
    public IntegerValue(double a){
        data = (int)a;
    }
    public IntegerValue(float a){
        data = (int)a;
    }
    public String toString(){
        return data.toString();
    }
    public  Value add(Value v) throws InvalidOperation{
        try{
        return new IntegerValue(data + Float.parseFloat(v.toString()));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("add","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());
        }
    }
    public  Value sub(Value v)throws InvalidOperation{
        try {
            return new IntegerValue(data - Float.parseFloat(v.toString()));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("sub","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());
        }
    }
    public  Value mul(Value v)throws InvalidOperation{
        try{
        return new IntegerValue(data * Float.parseFloat(v.toString()));
    }
        catch (NumberFormatException e){
            throw new InvalidOperation("mul","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());
    }
    }
    public  Value div(Value v)throws InvalidOperation{
        try{
        return new IntegerValue(data / Float.parseFloat(v.toString()));
        }
            catch (NumberFormatException e){
                throw new InvalidOperation("div","("+this.getClass().getSimpleName()+")"
                        +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());
        }
    }
    public  Value pow(Value v)throws InvalidOperation{
        try{
        return new IntegerValue((int) Math.pow(data, Float.parseFloat(v.toString())));
        }
        catch (NumberFormatException e){
            throw new InvalidOperation("pow","("+this.getClass().getSimpleName()+")"
                    +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());
        }
    }
    public Value sqrt(){
        return new IntegerValue( Math.sqrt(data));
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
        return data.hashCode();
    }
    public  Value create(String s)throws CannotCreateValueFromString {
        try{
        return new IntegerValue(java.lang.Integer.valueOf(s));
        }
        catch (NumberFormatException e){
            throw new CannotCreateValueFromString("Argument is not a number: " ,s);
        }
    }
}