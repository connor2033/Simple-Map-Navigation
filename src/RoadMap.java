import java.io.*;
import java.util.*;

public class RoadMap {

	
	//All Required Instance Variables
	private Graph Map;
	private Stack<Node> pathStack = new Stack<Node>();
	private int scale;
	private int startName;
	private int destName;
	private int nodeWidth;
	private int nodeLength;
	private int money;
	private int toll;
	private int gain;

	//RoadMap Constructor
	//Uses input file to properly construct a graph based on contents of file
	//returns exceptions if file not found or problem with graph
	public RoadMap(String mapBufferFile) throws MapException, GraphException {
		try {

			//taking file as input to read in with bufferedReader
			File mapFile = new File(mapBufferFile);
			BufferedReader mapBuffer = new BufferedReader(new FileReader(mapFile));

			//reading in initial variables
			scale = Integer.parseInt(mapBuffer.readLine());
			startName = Integer.parseInt(mapBuffer.readLine());
			destName = Integer.parseInt(mapBuffer.readLine());
			nodeWidth = Integer.parseInt(mapBuffer.readLine());
			nodeLength = Integer.parseInt(mapBuffer.readLine());
			money = Integer.parseInt(mapBuffer.readLine());
			toll = Integer.parseInt(mapBuffer.readLine());
			gain = Integer.parseInt(mapBuffer.readLine());

			//creating Map graph with the correct number of nodes
			Map = new Graph(nodeWidth * nodeLength);

			//Creating variables to help in construction of graph
			String currLine = mapBuffer.readLine();
			String downOne = mapBuffer.readLine();
			String downTwo = mapBuffer.readLine();

			Node prevNode = null;
			Node nextNode = null;

			char nextBlock = 0;
			char lastBlock = 0;
			int numNodes = -1;

			// going down line by line of input file using buffered reader
			while (currLine != null) {

				// looping through chars of current line
				for (int i = 0; i < currLine.length(); i++) {

					char curr = currLine.charAt(i);

					// if edge or house block
					if (curr == 'T' || curr == 'F' || curr == 'C' || curr == 'X') {

						//setting calues
						if (i > 0)
							lastBlock = currLine.charAt(i - 1);
						if (i < currLine.length() - 1)
							nextBlock = currLine.charAt(i + 1);
						if (nextBlock == '+')
							nextNode = Map.getNode(numNodes + 1);
						if (lastBlock == '+')
							prevNode = Map.getNode(numNodes);

						// inserting horizontal edges/roads
						if (nextBlock == '+') {
							nextNode = Map.getNode(numNodes + 1);
							if (curr == 'T') {
								Map.insertEdge(prevNode, nextNode, 1);
							} else if (curr == 'F') {
								Map.insertEdge(prevNode, nextNode, 0);
							} else if (curr == 'C') {
								Map.insertEdge(prevNode, nextNode, -1);
							}
						}

					}
					// if block's a node
					else {
						Node currNode = Map.getNode(numNodes + 1);
						numNodes++;

						// inserting vertical edges/roads
						if (downTwo != null) {
							if (downOne.charAt(i) != 'X' && downTwo.charAt(i) == '+') {
								nextNode = Map.getNode(numNodes + nodeWidth);

								if (downOne.charAt(i) == 'T') {
									Map.insertEdge(currNode, nextNode, 1);
								} else if (downOne.charAt(i) == 'F') {
									Map.insertEdge(currNode, nextNode, 0);
								} else if (downOne.charAt(i) == 'C') {
									Map.insertEdge(currNode, nextNode, -1);
								}
							}
						}
					}

				}

				// incrementing char lines
				currLine = downOne;
				downOne = downTwo;
				downTwo = mapBuffer.readLine();
			}
			// closing buffer
			mapBuffer.close();
		}
		// catching if input file is invalid
		catch (Exception e) {
			throw new MapException();
		}
	}

	//returning Map
	public Graph getGraph() {
		return Map;
	}

	//returning name of starting node
	public int getStartingNode() {
		return startName;
	}

	//returning name of destination node
	public int getDestinationNode() {
		return destName;
	}

	//returns initial amount of money
	public int getInitialMoney() {
		return money;
	}

	//Recursive function that returns an iterator containing the nodes of an available path from start to end
	//Does this by building a stack (private instance variable 'pathStack') and returning the stack with .iterator
	//Takes initial money, toll, and reward gain all into account
	//Mainly based on recursive path algorithm for graphs as discussed in class
	public Iterator<Node> findPath(int start, int destination, int initialMoney) throws GraphException {

		//initializing variables
		Node startNode = Map.getNode(start);
		Node destNode = Map.getNode(destination);
		Iterator<Node> finalPath = null;
		int Money = initialMoney;

		//pushing and marking current node, and checking if it is the destination
		startNode.setMark(true);
		pathStack.push(startNode);
		if (startNode == destNode) {
			finalPath = pathStack.iterator();
			return finalPath;
		}
		//looping through edges that are incident on the current node (using incidentEdges function) and recursively finding an available path
		else {
			Iterator<Edge> nodeEdges = Map.incidentEdges(startNode);
			Edge currEdge = null;

			while (nodeEdges.hasNext()) {
				currEdge = nodeEdges.next();

				Node nextNode = currEdge.secondEndpoint();
				if (currEdge.secondEndpoint().getName() == startNode.getName()) {
					nextNode = currEdge.firstEndpoint();
				}

				if (nextNode.getMark() == false) {

					// if edge is reward
					if (currEdge.getType() == -1) {
						if (findPath(nextNode.getName(), destination, Money + gain) != null) {
							finalPath = pathStack.iterator();
							return finalPath;
						}
					}
					// if edge is public
					else if (currEdge.getType() == 0) {
						if (findPath(nextNode.getName(), destination, Money) != null) {
							finalPath = pathStack.iterator();
							return finalPath;
						}
					}
					// if edge is toll
					else if (currEdge.getType() == 1 && Money >= toll) {
						if (findPath(nextNode.getName(), destination, Money - toll) != null) {
							finalPath = pathStack.iterator();
							return finalPath;
						}
					}

				}
			}

			//if no path was found from the current node, then it is popped from the top of stack, unmarked, and null is returned for recursion
			pathStack.pop();
			startNode.setMark(false);
			return null;

		}
	}

}
