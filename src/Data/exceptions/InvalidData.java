package Data.exceptions;

public class InvalidData extends Exception {
    public InvalidData(String column, int row,String data,String operation){
        super("Error in column: "+column+" row: "+row+" data: "+data+" during "+operation);
    }
}
