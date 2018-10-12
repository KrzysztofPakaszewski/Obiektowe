package Data;

public class C00Value {
	private Object data;
	private int row;
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
		System.out.println("("+(String)data + "," + row + ")" );
	}
}
