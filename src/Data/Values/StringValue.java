package Data.Values;

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
    public  Value add(Value v) {
        return new StringValue(data + v.toString());
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
    public Value sqrt(){return null;}
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
