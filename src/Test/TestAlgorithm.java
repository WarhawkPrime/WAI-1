package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

import aStar.*;
import djikstra.*;

public class TestAlgorithm {
	
	@Test
	void testTest() {
		
		char c = 'J';
		
		int a=1;
		int b=2;
		assertEquals(3, a+b);
	}

	
	@Test
	void testAStar2Labyrinth() {

		int numRows;
		int numColumns;
		int[][] board;

		String filename = "C:\\Users\\denni\\git\\AStarAlgorithm\\src\\test2"; //filename

		if (new File(filename).exists() == false) {
			throw new RuntimeException("Game file " + filename + " not found");
		}



		ArrayList<String> lines = new ArrayList<>();
		try {
			BufferedReader f = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = f.readLine();
				if (line == null) break;
				lines.add(line);
			}
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		numRows = lines.size();
		numColumns = lines.get(0).split("\t").length;
		board = new int[numRows][numColumns];



		for (int row=0; row<numRows; row++) {
			String line = lines.get(row);
			String [] fields = line.split("\t");
			assert fields.length == numColumns;
			for (int column=0; column<numColumns; column++) {
				board[row][column] = Integer.valueOf(fields[column]);
			}
		}



		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}


		AStarAlgorithm astar = new AStarAlgorithm();
		//DjikstraAlgorithm astar = new DjikstraAlgorithm();
		
		Node start = null;
		Node end = null;

		ArrayList<Node> startL = new ArrayList<Node>();
		ArrayList<Node> endL = new ArrayList<Node>();

		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				if(board[i][j] == 1) {
					//start = astar.createNewNode(i, j, board, 1, null);
				}
				if(board[i][j] == -1) {
					end = astar.createNewNode(i, j, board, 1, null, numColumns );
				}
			}
		}

		start = astar.createNewNode(2, 2, board, 1, null, numColumns);
		
		long startTime = System.nanoTime(); 
		Path foundPath = astar.findShortestPath(start, end, board, 1);
		long estimatedTime = System.nanoTime() - startTime;
		
		start.setPathToStart(foundPath);
		int nodeCount = foundPath.getPathInNodes().size();

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		for(int k = 0; k < foundPath.getPathInNodes().size(); k++ ) {
			board[foundPath.getPathInNodes().get(k).getRowPosition()][foundPath.getPathInNodes().get(k).getColumnPosition()] = 5;
			System.out.println("Schritt " + k +":   " + " R " + foundPath.getPathInNodes().get(k).getRowPosition() + " C " + foundPath.getPathInNodes().get(k).getColumnPosition());
		}



		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		board[start.getRowPosition()][start.getColumnPosition()] = 1;
		board[end.getRowPosition()][end.getColumnPosition()] = -1;

		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		double mod = Math.pow(10, -6);
		
		System.out.println();
		System.out.println( "Gemessene Zeit in Nanosekunden: " + estimatedTime + " " + "ns");
		System.out.println( "Gemessene Zeit in Millisekunden: " + estimatedTime * mod + " " + "ns");
		System.out.println();


		assertEquals(1, nodeCount);
	}

}
