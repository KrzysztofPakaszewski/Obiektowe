package Data.Values;

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
    public  Value add(Value v) {
        return new IntegerValue(data + Float.parseFloat(v.toString()));
    }
    public  Value sub(Value v){
        return new IntegerValue(data - Float.parseFloat(v.toString()));
    }
    public  Value mul(Value v){
        return new IntegerValue(data * Float.parseFloat(v.toString()));
    }
    public  Value div(Value v){
        return new IntegerValue(data / Float.parseFloat(v.toString()));
    }
    public  Value pow(Value v){
        return new IntegerValue((int) Math.pow(data, Float.parseFloat(v.toString())));
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
        return data.equals(other);
    }
    public  int hashCode(){
        return data.hashCode();
    }
    public  Value create(String s){
        return new IntegerValue(java.lang.Integer.valueOf(s));
    }
}
