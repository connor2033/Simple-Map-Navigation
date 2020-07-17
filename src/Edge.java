
public class Edge {
	
	//creating private instance variables
	private Node nodeOne;
	private Node nodeTwo;
	private int edgeType;
	
	//constructor for edge setting end point nodes and type
	public Edge(Node u, Node v, int type){
		this.nodeOne = u;
		this.nodeTwo = v;
		this.edgeType = type;
	}
	
	//returns nodeOne
	public Node firstEndpoint() {
		return this.nodeOne;
	}
	
	//returns nodeTwo
	public Node secondEndpoint() {
		return this.nodeTwo;
	}
	
	//returns type of edge
	public int getType() {
		return this.edgeType;
	}
	
}
