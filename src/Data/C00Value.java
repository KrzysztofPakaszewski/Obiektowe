package Data;

import Data.Values.Value;

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
    public void print(){
        System.out.print("("+row + "," + data.toString() + ")" );
    }
    public String toString(){
        return "("+row + "," + data + ")";
    }
    public Value add(Value v){
        return null;
    }
    public Value sub(Value v){
        return null;
    }
    public Value mul(Value v){
        return null;
    }
    public Value sqrt(){return null;}
    public Value div(Value v){
        return null;

    }
    public Value pow(Value v){
        return null;

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
    public Value create(String s){
        BufferedReader br;
        Pattern pattern =Pattern.compile("\\((.*?),(.*)\\)");
        Matcher m;
        m= pattern.matcher(s);
        m.find();
        try {
            return new C00Value(Value.getInstance(data.getClass()).create(m.group(1)),Integer.parseInt(m.group(2)));
        }
        catch (NumberFormatException exc){
            throw new RuntimeException("Wrong parameter");
        }
        catch (IndexOutOfBoundsException exc){
            throw new RuntimeException("Wrong string");
        }
    }
}