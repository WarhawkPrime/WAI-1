package djikstra;

import aStar.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DjikstraAlgorithm {
	
	public DjikstraAlgorithm() {
		
	}
	
	public Path findShortestPath(Node startNode, Node goalNode, int[][] gameBoard, int ownColor) {

		final int gameBoardRows = gameBoard.length;
		final int gameBoardColumns = gameBoard[0].length;
		final int movecost = 1;

		Comparator<Node> comp = new NodeComparator();																					//sortiert nach f kosten
		PriorityQueue<Node> open = new PriorityQueue<Node>(comp);
		ArrayList<Node> closed = new ArrayList<Node>();

		startNode.setfCost(0);																											//start Node bestimmen
		calculateNodeID(startNode, gameBoardColumns );
		open.add(startNode);
		
		if( (isNodeGoal(startNode, goalNode)) == true ) {																				//start ist schon das Ziel
			return tracePathFromGoal(goalNode);
		}
																																
		while(open.isEmpty() == false) {																								//solange die openList nicht leer ist
			
			Node current = open.poll();																										//current ist die Node mit den geringsten f kosten, wird aus open entfernt
			
			if( (isNodeGoal(current, goalNode)) == true ) {																					//ist der Nachbar das Ziel? wenn ja pfad zurückgeben

				return tracePathFromGoal(current);
			}
			
			closed.add(current);																											//current wird der closed List hinzugefügt
			
			ArrayList<Node> neighbours = findAllNeighbours(current, gameBoard, gameBoardRows, gameBoardColumns, ownColor, movecost);		//alle Nachbarn bestimmen
			
			for(Node neighbour : neighbours)  {																								//für alle Nachbarn
				
				if( isInClosed(neighbour, closed) == false ) {
										
					neighbour.setgCost(current.getgCost() + movecost);																				//alle kosten für den Nachbarn bestimmen
					neighbour.sethCost(0);
					neighbour.setfCost(neighbour.getgCost() + neighbour.gethCost());
					neighbour.setParent(current);
					
					if( (isNodeGoal(neighbour, goalNode)) == true ) {																					//ist der Nachbar das Ziel? wenn ja pfad zurückgeben
						
						return tracePathFromGoal(neighbour);
					}
					
					if( (isInOpen(neighbour, open) == true) && ( getNodeCopyFromOpen(neighbour.getNodeID(), open).getfCost() <= neighbour.getfCost()) ) {	//wenn der selbe knoten schon in open ist und der in open aber kleinere f kosten hat
						//ignore Neighbour
					}
					else if ((isInOpen(neighbour, open) == true) && ( getNodeCopyFromOpen(neighbour.getNodeID(), open).getfCost() > neighbour.getfCost()) ) {	//wenn schon in der offenen ist aber neighbour ist besser
						Node nodeToReplace = getNodeCopyFromOpen(neighbour.getNodeID(), open);
						open.remove(nodeToReplace);
						open.add(neighbour);
					}
					else if( ( isInClosed(neighbour, closed) == true) && (getNodeFromClosed(neighbour, closed).getfCost() <= neighbour.getNodeID() ) ) {	//wenn der selbe knoten schon in closed ist und der in open aber kleinere f kosten hat
						//ignore Neighbour
					}
					else {
						open.add(neighbour);
					}
				}	//hier ende wenn der nachbar schon in closed war
			}																																//alle Nachbarn  durchgegangen
			//closed.add(current);																										//current wird der closed hinzugefügt
		}																															//while schleife fertig
		return traceAlternatePath(startNode, closed);																			//kein Pfad zum Tor gefunden, alternativ Ziel ist der nächste freie Platz am Tor
	}


	//Methode um die Nachbarn einer Node zu bekommen
	public ArrayList<Node> findAllNeighbours(Node currentNode, int[/*maxRows*/][/*maxCols*/] board, int gameRowNumber, int gameColumnNumber, int ownColor, int moveCost){

		int rowNumber;
		int columnNumber;
		int rowIndex = gameRowNumber - 1;
		int columnIndex = gameColumnNumber -1;

		ArrayList<Node> neighbours = new ArrayList<Node>();


		//========== Upper Neighbour ==========
		columnNumber = currentNode.getColumnPosition();
		rowNumber = ((currentNode.getRowPosition() -1));

		if(rowNumber == -1)
			rowNumber = rowIndex;

		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
			Node upperNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.UP,gameColumnNumber);
			//upperNeighbour.setgCost(currentNode.getgCost() + moveCost);
			//upperNeighbour.setParent(currentNode);
			neighbours.add(upperNeighbour);
		}


		//========== Right neighbour ==========
		columnNumber = ((currentNode.getColumnPosition()  + 1));
		rowNumber = currentNode.getRowPosition(); 

		if(columnNumber > columnIndex)
			columnNumber = 0;

		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
			Node rightNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.RIGHT, gameColumnNumber);
			//rightNeighbour.setgCost(currentNode.getgCost() + moveCost);
			//rightNeighbour.setParent(currentNode);
			neighbours.add(rightNeighbour);
		}



		//========== Lower neighbour ==========
		columnNumber = currentNode.getColumnPosition();
		rowNumber = ((currentNode.getRowPosition() + 1));

		if(rowNumber > rowIndex)
			rowNumber = 0;

		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
			Node lowerNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.DOWN, gameColumnNumber);
			//lowerNeighbour.setgCost(currentNode.getgCost() + moveCost);
			//lowerNeighbour.setParent(currentNode);
			neighbours.add(lowerNeighbour);
		}


		//========== Left neighbour ==========
		columnNumber = ((currentNode.getColumnPosition()  -1));
		rowNumber = currentNode.getRowPosition(); 

		if(columnNumber == -1)
			columnNumber = columnIndex;

		if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
			Node leftNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.LEFT, gameColumnNumber);
			//leftNeighbour.setgCost(currentNode.getgCost() + moveCost);
			//leftNeighbour.setParent(currentNode);
			neighbours.add(leftNeighbour);
		}


		return neighbours;
	}


	public Path tracePathFromGoal(Node endNode) {
		
		ArrayList<Node> tracedPathFromGoal = new ArrayList<Node>();
		Node tempNode = endNode;
		
		while(tempNode.getParent() != null) {
			tracedPathFromGoal.add(tempNode);
			tempNode = tempNode.getParent();
		}
		
		tracedPathFromGoal.add(tempNode);
		Path path = new Path(tracedPathFromGoal);
		return path;
	}
	
	public Path traceAlternatePath(Node startNode, ArrayList<Node> closed) {
		
		Integer hCost = 100000;										//niedriegste h kosten aus der closed List bekommen
		Node tempGoal = null;
		
		if(closed.size() == 1) {												//in der closed List ist nur der Start drin, also keine möglichkeit sich zu bewegen
			Path altPath = tracePathFromGoal(startNode);								//node die am nächsten am eig. Ziel dran ist wird als altetnative genommen
			return altPath;													//null da kein Pfad existiert
		}
		
		
		for(Node node : closed) {
			if( (node.gethCost() < hCost) && (node.getNodeID() != startNode.getNodeID()) ) {
				tempGoal = node;
				hCost = node.gethCost();
			}
		}
		
		Path altPath = tracePathFromGoal(tempGoal);								//node die am nächsten am eig. Ziel dran ist wird als altetnative genommen
		return altPath;
	}
	
	
	//Methode um ein Feld zu Ã¼berprÃ¼fen, ob es geblockt ist
	public boolean fieldIsBlocked(int rowNumber, int columnNumber, int[/*maxRows*/][/*maxCols*/] board, int ownColor) {


		//alles auÃŸer 0 und -ownColor ist eine Blockade
		if( board[rowNumber][columnNumber] != 0 && board[rowNumber][columnNumber] != -ownColor  ) {
			return true;
		}
		else {
			return false;
		}
	}


	public void calculateNodeID(Node node, int ColumnNumbers /*Nicht der Index, sondern die tatsÃ¤chliche Anzahl an Columns*/) {
		int ID = node.getColumnPosition() + 1  + ( node.getRowPosition() * ColumnNumbers );
		node.setNodeID(ID);
	}

	//Methode um eine neue Node zu erstellen
	public Node createNewNode(int rowNumber, int columnNumber, int [/*maxRows*/][/*maxCols*/] board, int ownColor, Direction direction, int gameBoardColumms ) {

		boolean isBlocked;

		//wenn es nicht 0 ist und nicht -ownColor ist, dann ist es eine Blockade
		if( board[rowNumber][columnNumber] != 0 && board[rowNumber][columnNumber] != -ownColor ) {
			isBlocked = true;
		}
		else {
			isBlocked = false;
		}

		Node node = new Node(rowNumber, columnNumber, isBlocked, direction);
		calculateNodeID(node, gameBoardColumms);
		return node;
	}

	
	public boolean isInClosed(Node node, ArrayList<Node> closed) {
		for(Node n : closed) {
			if(node.getNodeID() == n.getNodeID()) {
				return true;
			}
		}
		return false;
	}
	
	public Node getNodeFromClosed(Node node, ArrayList<Node> closed) {
		for(Node n : closed) {
			if(node.getNodeID() == n.getNodeID()) {
				return n;
			}
		}
		return null;
	}
	
	public boolean isInOpen(Node node, PriorityQueue<Node> open) {
		
		PriorityQueue<Node> tempQueue = new PriorityQueue<Node>(open);
		
		for(int i = 0; i < tempQueue.size(); i++) {
			Node tempNode = tempQueue.poll();
			if(node.getNodeID() == tempNode.getNodeID() ) {
				return true;
			}
		}
		return false;
	}
	
	public Node getNodeCopyFromOpen(int nodeID, PriorityQueue<Node> open) {
		
		PriorityQueue<Node> tempQueue = new PriorityQueue<Node>(open);
		
		for(int i = 0; i < tempQueue.size(); i++) {
			Node tempNode = tempQueue.poll();
			
			if(tempNode.getNodeID() == nodeID) {
				return tempNode;
			}
		}
		return null;	
	}
	
	//public Node getNodeFromOpen(int nodeID, PriorityQueue<Node> open) {
		
	//}
	
	public boolean isNodeGoal(Node node, Node goalNode) {
		if(node.getNodeID() == goalNode.getNodeID()) {
			return true;
		}
		else {
			return false;
		}
	}


}
