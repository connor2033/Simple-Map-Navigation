import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

	// creating private instance variables
	private Edge[][] graphArray;
	private Node[] nodeArray;
	private int numNodes;

	// constructor for graph that creates n^2 matrix of edges and array of nodes
	public Graph(int n) {
		graphArray = new Edge[n][n];
		nodeArray = new Node[n];
		numNodes = n;

		for (int i = 0; i < n; i++) {
			nodeArray[i] = new Node(i);
		}
	}

	//returns node with given name, if it exists
	public Node getNode(int name) throws GraphException {

		try {
			return nodeArray[name];
		} catch (Exception e) {
			throw new GraphException();
		}

	}

	//inserts a new edge with given edge type and nodes
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {

		// checking if both nodes exist and if the edge already exists
		try {
			getNode(u.getName());
			getNode(v.getName());
		} catch (Exception e) {
			throw new GraphException();
		}
		if (graphArray[u.getName()][v.getName()] != null)
			throw new GraphException();

		// inserting into matrix
		graphArray[u.getName()][v.getName()] = new Edge(u, v, edgeType);
		graphArray[v.getName()][u.getName()] = new Edge(u, v, edgeType);
	}

	
	// returns an iterator of the edges incident on the given node u
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		// checking u exists
		try {
			getNode(u.getName());
		} catch (Exception e) {
			throw new GraphException();
		}

		// creating linked list of edges incident on given node
		LinkedList<Edge> nodeEdges = new LinkedList<Edge>();

		//building the linked list of edges
		for (int i = 0; i < numNodes; i++) {
			if (graphArray[u.getName()][i] != null) {
				nodeEdges.add(graphArray[u.getName()][i]);
			}
		}

		//returning iterator if there are any edges in the list
		if (nodeEdges.isEmpty())
			return null; // if node has no edges
		else
			return nodeEdges.iterator();
	}

	//returns an edge with the given nodes if it exists
	public Edge getEdge(Node u, Node v) throws GraphException {

		// checking if both nodes exist
		try {
			getNode(u.getName());
			getNode(v.getName());
		} catch (Exception e) {
			throw new GraphException();
		}

		// if the edge exists
		if (graphArray[u.getName()][v.getName()] != null) {
			return graphArray[u.getName()][v.getName()];
		} else
			throw new GraphException();
	}

	//boolean function returning true if the 2 given nodes are connected by an edge
	public boolean areAdjacent(Node u, Node v) throws GraphException {

		// checking if both nodes exist
		try {
			getNode(u.getName());
			getNode(v.getName());
		} catch (Exception e) {
			throw new GraphException();
		}

		// if there is an edge in the graph matrix between the 2 nodes
		if (graphArray[u.getName()][v.getName()] != null)
			return true;
		else
			return false;

	}

}
