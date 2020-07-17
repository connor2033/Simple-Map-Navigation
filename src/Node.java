
public class Node {
	
	//creating private instance variables
	private int nodeName;
	private boolean nodeMark;
	
	//constructor for Node setting name and mark values
	public Node(int name){
		this.nodeName = name;
		this.nodeMark = false;
	}
	
	//setting the boolean mark value
	public void setMark(boolean mark){
		this.nodeMark = mark;
	}
	
	//returns the boolean mark value
	boolean getMark() {
		return this.nodeMark;
	}
	
	//returns the name integer
	public int getName() {
		return this.nodeName;
	}	
	
}
