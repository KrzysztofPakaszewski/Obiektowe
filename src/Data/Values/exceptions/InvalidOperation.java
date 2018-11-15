package Data.Values.exceptions;

public class InvalidOperation extends Exception {
    public InvalidOperation(String operation,String arg,String arg2){
        super("The " +operation +" operation is invalid for: "+ arg + " , " +arg2);
    }
}
