package Data.Values.exceptions;

public class CannotCreateValueFromString extends Exception {
    public CannotCreateValueFromString(String message, String arg){
        super(message + arg);
    }
}
