package aStar;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	
	@Override
	public int compare(Node n1, Node n2) {
		
		int result = 0;
		
		if(n1.getfCost() < n2.getfCost()) {
			return -1;
		}
		if(n1.getfCost() > n2.getfCost() ) {
			return 1;
		}
		return result;
	}

}

