package Data.Values;

public class FloatValue extends Value{
    private final java.lang.Float data;
    public FloatValue(int a){
        data = (float)a;
    }
    public FloatValue(double a){
        data = (float)a;
    }
    public FloatValue(float a){
        data = a;
    }
    public String toString(){
        return data.toString();
    }
    public  Value add(Value v) {
        return new FloatValue(data + Float.parseFloat(v.toString()));
    }
    public  Value sub(Value v){
        return new FloatValue(data - Float.parseFloat(v.toString()));
    }
    public  Value mul(Value v){
        return new FloatValue(data * Float.parseFloat(v.toString()));
    }
    public  Value div(Value v){
        return new FloatValue(data / Float.parseFloat(v.toString()));
    }
    public  Value pow(Value v){
        return new FloatValue( Math.pow(data, Float.parseFloat(v.toString())));
    }
    public Value sqrt(){return new FloatValue(Math.sqrt(data));}
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
        return equals((Value) other);
    }
    public  int hashCode(){
        return data.hashCode();
    }
    public  Value create(String s){
        return new IntegerValue(java.lang.Float.valueOf(s));
    }
}
