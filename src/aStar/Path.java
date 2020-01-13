package aStar;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Path {
	
	private NodeComparator nodeComparator;
	
	private ArrayList<Node> pathInNodes;
	private PriorityQueue<Node> pathInQueue;
	
	public Path(ArrayList<Node> pathInNodes) {
		this.setPathInNodes(pathInNodes);
		this.nodeComparator = new NodeComparator();
		this.pathInQueue = new PriorityQueue(nodeComparator);
	}

	public ArrayList<Node> getPathInNodes() {
		return pathInNodes;
	}

	public void setPathInNodes(ArrayList<Node> pathInNodes) {
		this.pathInNodes = pathInNodes;
	}

	public PriorityQueue<Node> getPathInQueue() {
		return pathInQueue;
	}

	public void setPathInQueue(PriorityQueue<Node> pathInQueue) {
		this.pathInQueue = pathInQueue;
	}
	
	public Node getNextNode( int ownNodeID ) {
        if(pathInNodes.size() == 1 ) {
            return null;
        }
        else {
            for(int j = 0; j < pathInNodes.size(); j++) {
                if(pathInNodes.get(j).getParent().getNodeID() == ownNodeID) {
                    return pathInNodes.get(j);
                }
            }
        }
        return null;
    }

	
	//Holt sich die nächste richtung 
    public Direction getNextDirection(int ownID) {
        if(pathInNodes.size() == 1 ) {
            return null;
        }
        else {
            for(int j = 0; j < pathInNodes.size(); j++) {
                if(pathInNodes.get(j).getParent().getNodeID() == ownID ) {
                    return pathInNodes.get(j).getDirection();
                }
            }
        }
        return null;
    }
    
  //Methode die die Liste zur Queue bringt
    public void sortNodesToQueue(int ownRowNumber, int ownColumnNumber) {
        //start aus der queue raussortieren, start ist die node pos der eigenen Node, muss übergeben werden
        for(Node node : pathInNodes) {
            if(node.getRowPosition() == ownRowNumber && node.getColumnPosition() == ownColumnNumber ) {
                continue;
            }
            pathInQueue.add(node);
        }
    }
    
  //testet node auf höchste g kosten, sollte das Ziel sein
    public boolean hasHighestG(Node nodeToTest) {
        for(Node node : pathInNodes) {
            if(nodeToTest.getgCost() < node.getgCost() ) {
                return false;
            }
        }
        return false;
    }
    
    public PriorityQueue<Node> getQueue() {
        return pathInQueue;
    }
    
    public Direction getNextDirectionByQueue() {
        if( ! pathInQueue.isEmpty() ) {
            return pathInQueue.poll().getDirection();
        }
        return null;
    }

    //vergleiche h von start zu ziel / 2 mit anzahl knoten im Pfad. 
    //wenn anzahl knoten im Pfad < h(start zu ziel) :
    //    stein ist geblockt, anderen Stein nehmen
}

