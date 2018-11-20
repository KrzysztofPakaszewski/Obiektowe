package Data.Values;

import Data.exceptions.InvalidOperation;

public class StringValue extends Value {
    private final java.lang.String data;
    public StringValue(int a){
        data = Integer.toString(a);
    }
    public StringValue(double a){
        data = Double.toString(a);
    }
    public StringValue(Float a){
        data = Float.toString(a);
    }
    public StringValue(String a){
        data = a;
    }
    public String toString(){
        return data;
    }
    public  Value add(Value v) throws InvalidOperation{
        throw new InvalidOperation("add","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public  Value sub(Value v)throws InvalidOperation {
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
                +this.toString(),"");

    }
    public  boolean equals(Value v){
        return this.toString().equals(v.toString());
    }
    public  boolean lessOrEquals(Value v){
        return data.compareTo(v.toString()) < 0 || this.equals(v);
    }
    public  boolean greaterOrEquals(Value v){
        return data.compareTo(v.toString()) > 0 || this.equals(v);
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
    public  Value create(String s){
        return new StringValue(s);
    }
}
