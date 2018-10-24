package Data.Values;

public class DoubleValue extends Value{
    private final java.lang.Double data;
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
        return data.toString();
    }
    public  Value add(Value v) {
        return new DoubleValue(data + Float.parseFloat(v.toString()));
    }
    public  Value sub(Value v){
        return new DoubleValue(data - Float.parseFloat(v.toString()));
    }
    public  Value mul(Value v){
        return new DoubleValue(data * Float.parseFloat(v.toString()));
    }
    public  Value div(Value v){
        return new DoubleValue(data / Float.parseFloat(v.toString()));
    }
    public  Value pow(Value v){
        return new DoubleValue((int) Math.pow(data, Float.parseFloat(v.toString())));
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
        return new DoubleValue(java.lang.Double.valueOf(s));
    }
}
