package quoridor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameState {
	/**
	 * A hybrid graph representation as suggested by van Rossum and Cormen et al:
	 * A hash table is used to associate each vertex with a doubly linked list of adjacent vertices
	 */
	HashMap <Square,LinkedList<Square>> adjacencyList = new HashMap <Square,LinkedList<Square>> ();
	
	/**
	 * A list to keep track of past moves.
	 */
	List <String> moves = new LinkedList<String>();
	
	Square player1Square = new Square("e9");
	Square player2Square = new Square("e1");
	LinkedList <Wall> player1Walls = new LinkedList<Wall>();
	LinkedList <Wall> player2Walls = new LinkedList<Wall>();
	
	public GameState() {
		// Initialize adjacency list
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				LinkedList<Square> adjacent = new LinkedList<Square>();
				for (int d = -1; d < 2; d++) {
					if (d != 0) { // Vertices are not self-connecting
						if (i+d >= 0 && i+d < Board.BOARD_SIZE)
							adjacent.add(new Square(i+d,j));
						if (j+d >= 0 && j+d < Board.BOARD_SIZE)
							adjacent.add(new Square(i,j+d));
					}
				}
				adjacencyList.put(new Square(i,j), adjacent);
			}
		}
	}
	
}
