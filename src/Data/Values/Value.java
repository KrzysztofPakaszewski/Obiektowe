package Data.Values;

import Data.C00Value;
import Data.Values.exceptions.CannotCreateValueFromString;
import Data.Values.exceptions.InvalidOperation;

public abstract class Value implements Cloneable{
    private static IntegerValue instanceInteger = new IntegerValue(0);
    private static DoubleValue instanceDouble = new DoubleValue(0);
    private static FloatValue instanceFloat = new FloatValue(0);
    private static StringValue instanceString = new StringValue("");
    private static DateTimeValue instanceDate = new DateTimeValue();
    private static C00Value instanceC00Value = new C00Value(instanceInteger,0);
    protected Value clone(){
        try {
            return getInstance(this.getClass()).create(this.toString());
        }
        catch (CannotCreateValueFromString e){
            throw new RuntimeException("impossible to get");
        }
    }

    public static Value getInstance(Class<? extends Value> clazz) {
        if(clazz.isInstance(instanceInteger)){
            return instanceInteger;
        }
        else if(clazz.isInstance(instanceDouble)){
            return instanceDouble;
        }
        else if(clazz.isInstance(instanceFloat)){
            return instanceFloat;
        }
        else if(clazz.isInstance(instanceString)){
            return instanceString;
        }else if(clazz.isInstance(instanceDate)){
            return instanceDate;
        }
        else if(clazz.isInstance(instanceC00Value)){
            return instanceC00Value;
        }
        else{
            throw new RuntimeException("Unsupported type");
        }
    }
    @Override
    public abstract String toString();
    public abstract Value add(Value v) throws InvalidOperation;
    public abstract Value sub(Value v)throws InvalidOperation;
    public abstract Value mul(Value v)throws InvalidOperation;
    public abstract Value div(Value v)throws InvalidOperation;
    public abstract Value pow(Value v)throws InvalidOperation;
    public abstract Value sqrt()throws InvalidOperation;
    public abstract boolean equals(Value v);
    public abstract boolean lessOrEquals(Value v);
    public abstract boolean greaterOrEquals(Value v);
    public abstract boolean notEquals(Value v);
    @Override
    public abstract boolean equals(Object other);
    @Override
    public abstract int hashCode();
    public abstract Value create(String s) throws CannotCreateValueFromString;
}
