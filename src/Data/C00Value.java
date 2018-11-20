package Data;

import Data.Values.Value;
import Data.exceptions.CannotCreateValueFromString;
import Data.exceptions.Error;
import Data.exceptions.InvalidOperation;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class C00Value extends Value {
    final Value data;
    final int row;
    public C00Value(Value input, int Row){
        data = input;
        row = Row;
    }
    public Value getData(){
        return data;
    }
    public int getRow(){
        return row;
    }
    public String toString(){
        return "("+row + "," + data + ")";
    }
    public Value add(Value v) throws InvalidOperation{
        throw new InvalidOperation("add","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public Value sub(Value v)throws InvalidOperation{
        throw new InvalidOperation("sub","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public Value mul(Value v)throws InvalidOperation{
        throw new InvalidOperation("mul","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());

    }
    public Value sqrt()throws InvalidOperation{
        throw new InvalidOperation("sqrt","("+this.getClass().getSimpleName()+")"
                +this.toString(),"");

    }
    public Value div(Value v)throws InvalidOperation{
        throw new InvalidOperation("div","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());


    }
    public Value pow(Value v)throws InvalidOperation{
        throw new InvalidOperation("pow","("+this.getClass().getSimpleName()+")"
                +this.toString(),"("+v.getClass().getSimpleName()+")"+v.toString());


    }
    public boolean equals(Value v){
        return toString().equals(v.toString());
    }
    public boolean lessOrEquals(Value v){
        return false;
    }
    public boolean greaterOrEquals(Value v){
        return false;
    }
    public boolean notEquals(Value v){
        return !equals(v);
    }
    @Override
    public boolean equals(Object other){
        return toString().equals(other.toString());
    }
    @Override
    public int hashCode(){
        return data.hashCode();
    }
    public Value create(String s) throws CannotCreateValueFromString,Error {
        Pattern pattern =Pattern.compile("\\((.*?),(.*)\\)");
        Matcher m;
        m= pattern.matcher(s);
        m.find();
        try {
            return new C00Value(Value.getInstance(this.getClass()).create(m.group(1)),Integer.parseInt(m.group(2)));
        }
        catch (NumberFormatException exc){
            throw new CannotCreateValueFromString("Wrong parameter",s);
        }
        catch (IndexOutOfBoundsException exc){
            throw new CannotCreateValueFromString("Wrong string",s);
        }
        catch (Error e){
            throw new Error(e.getMessage());
        }
    }
}