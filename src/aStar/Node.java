package aStar;

public class Node {


	private int nodeID;
	private int rowPosition;
	private int columnPosition;
	private boolean isBlocked;
	
	private int gCost;
	private int hCost;
	private int fCost;
	private Node Parent;
	
	private Direction direction;
	private Path pathToStart;
	
	public Node(int rowPosition, int columnPosition, boolean isBlocked, Direction direction) {
		this.setNodeID(nodeID);
		
		this.setRowPosition(rowPosition);
		this.setColumnPosition(columnPosition);
		this.setBlocked(isBlocked);
		
		this.setgCost(0);
		this.sethCost(0);
		this.setfCost(0);
		this.setParent(null);
		
		this.setDirection(direction);
		this.setPathToStart(null);
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public int getRowPosition() {
		return rowPosition;
	}

	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}

	public int getColumnPosition() {
		return columnPosition;
	}

	public void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public int getfCost() {
		return fCost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
	}

	public Node getParent() {
		return Parent;
	}

	public void setParent(Node parent) {
		Parent = parent;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Path getPathToStart() {
		return pathToStart;
	}

	public void setPathToStart(Path pathToStart) {
		this.pathToStart = pathToStart;
	}
	
	public Direction getNextDirection() {
		return pathToStart.getNextDirection(nodeID);
	}

}
