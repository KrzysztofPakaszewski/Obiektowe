package Data;

public final class C00Value {
    final Object data;
    final int row;
    public C00Value(Object temp, int Row){
        data = temp;
        row = Row;
    }
    public Object getData(){
        return data;
    }
    public int getRow(){
        return row;
    }
    public void print(){
        System.out.print("("+row + "," + data + ")" );
    }
}