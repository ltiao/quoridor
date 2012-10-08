package quoridor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Validator {

	// TODO complete this class using your project code
	// you must implement the no-arg constructor and the check method
	
	// you may add extra fields and methods to this class
	// but the ProvidedTests code will only call the specified methods

	/**
	 * A hybrid of graph representation suggestions by van Rossum and Cormen et al:
	 * A hash table is used to associate each vertex with a doubly linked list of adjacent vertices
	 */
	HashMap <Square,LinkedList<Square>> adjacencyList = new HashMap <Square,LinkedList<Square>> ();
	
	Square player1Square = new Square("e9");
	Square player2Square = new Square("e1");
	LinkedList <Wall> walls = new LinkedList<Wall>();
	
	public Validator() {
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				LinkedList<Square> temp = new LinkedList<Square>();
				for (int d = -1; d < 2; d++) {
					if (d != 0) { // Vertices are not self-connecting
						if (i+d >= 0 && i+d < Board.BOARD_SIZE)
							temp.add(new Square(i+d,j));
						if (j+d >= 0 && j+d < Board.BOARD_SIZE)
							temp.add(new Square(i,j+d));
					}
				}
				adjacencyList.put(new Square(i,j), temp);
			}
		}
		//updatePawnSquareAdjacency();
	}
	
	public void displayAdjacencyList () {
		for (Square e:adjacencyList.keySet()) {
			System.out.println(e+": "+adjacencyList.get(e));
		}
	}

	/**
	 * Check the validity of a given sequence of moves.
	 * The sequence is valid if and only if each (space separated)
	 * move in the list is valid,
	 * starting from the initial position of the game.
	 * When the game has been won, no further moves are valid.
	 * @param moves a list of successive moves
	 * @return validity of the list of moves
	 */
	public boolean check(String moves) {
		// TODO Game string not valid if there are more strings after game over
		
		//This block is for testing only
		//StringBuilder fun = new StringBuilder();
		//Display board = new Board();
		
		boolean valid = true;
		StringTokenizer st = new StringTokenizer(moves);
		for (int i = 0; st.hasMoreTokens() && valid == true ; i++) {
			String temp = st.nextToken();
			//This block is for testing only
			//fun.append(temp+" ");
			//System.out.println("Turn: "+i+" | Player: "+i%2);
			//board.display(fun.toString());
			
			if (temp.length() == 3) {
				valid &= isValidWallPlacement(new Wall(temp));
			} else {
				if (i%2==0) {
					if (isValidTraversal(player1Square, new Square(temp))) {
						player1Square = new Square (temp);
					} else {
						return false;
					}
				} else {
					if (isValidTraversal(player2Square, new Square(temp))) {
						player2Square = new Square (temp);
					} else {
						return false;
					}
				}	
			}
		}
		return valid;
	}
	
	/**
	 * <b>General Movement:</b>
	 * A pawn can move to a square directly adjacent to itself, provided
	 * it is not obstructed by a wall or pawn. See below.
	 * <br/>
	 * <b>Wall obstruction:</b>
	 * Say the current square has coordinate (x, y).
	 * The only walls that can possibly obstruct the
	 * pawn are those at (x, y), (x, y-1), (x-1, y) and (x-1, y-1).
	 * <br/>
	 * <b>Pawn obstruction:</b>
	 * When a pawn, say B is on a square directly adjacent to pawn A,
	 * then pawn A can move to any square directly adjacent to pawn B,
	 * and vice versa.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean isValidTraversal (Square a, Square b) {
		if (a.equals(b)) {
			return false;
		}
		if (adjacencyList.get(a).contains(b)) {
			return true;
		} else {
			if (adjacencyList.get(a).contains(player1Square) || adjacencyList.get(a).contains(player2Square)) {
				return adjacencyList.get(player1Square).contains(b) || adjacencyList.get(player2Square).contains(b);
			}
		}
		return false;
	}
	
	public boolean isOver (String moves) {
		StringTokenizer st = new StringTokenizer(moves);
		for (int i = 0; st.hasMoreTokens() ; i++) {
			if (i%2==0) {
				if (st.nextToken().endsWith("1")) {
					return true;
				}
			} else {
				if (st.nextToken().endsWith("9")) {
					return true;
				}
			}
		}
		return false;
	}
	
	public LinkedList <Square> shortestPath (Square src, Square dest) {
		// TODO Dijkstra's algorithm
		LinkedList <Square> temp = new LinkedList<Square>();
		temp.add(new Square("e8"));
		return temp;
	}
	
	/**
	 * @param src
	 * @param dest
	 * @return 0 if no path exists
	 */
	public int distance (Square src, Square dest) {
		return shortestPath (src, dest).size ();
	}
	
	public boolean hasPath (Square src, Square dest) {
		return distance (src, dest) > 0;
	}
	
	public boolean hasPathToGoal () {
		boolean player1HasPath = false;
		boolean player2HasPath = false;
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			if (!player1HasPath)
				player1HasPath |= hasPath(player1Square, new Square(0,i));
			if (!player2HasPath)
				player2HasPath |= hasPath(player2Square, new Square(8,i));
		}
		return player1HasPath && player2HasPath;
	}
	
	public boolean isValidWallPlacement (Wall wall) {
		
		// Check wall is not intersecting existing wall
		if (wall.orientation == Orientation.HORIZONTAL) {
			if (walls.contains(wall) || walls.contains(wall.neighbor(0, 0, Orientation.VERTICAL)) || walls.contains(wall.neighbor(0, -1, Orientation.HORIZONTAL)) || walls.contains(wall.neighbor(0, 1, Orientation.HORIZONTAL))) {
				return false;
			}
		} else {
			if (walls.contains(wall) || walls.contains(wall.neighbor(0, 0, Orientation.HORIZONTAL)) || walls.contains(wall.neighbor(-1, 0, Orientation.VERTICAL)) || walls.contains(wall.neighbor(1, 0, Orientation.VERTICAL))) {
				return false;
			}
		}
		
		// If the wall does not intersect existing walls, proceed to update the graph
		// to remove associated edges
		HashMap <Square, LinkedList<Square>> backup = new HashMap<Square, LinkedList<Square>>(adjacencyList);
		if (wall.orientation==Orientation.HORIZONTAL) {
			removeEdge(wall.northWest, wall.northWest.neighbor(1, 0));
			removeEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
		} else {
			removeEdge(wall.northWest, wall.northWest.neighbor(0, 1));
			removeEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
		}
		
		// If we remove paths to the goal for any player 
		// by the placement of a wall, undo it, otherwise
		// store the new wall in the list of walls
		if (hasPathToGoal()) {
			walls.add(wall);
			return true;
		} else {
			adjacencyList = backup;
			return false;
		}
	}
	
	// Need to check preconditions
	public void removeEdge (Square a, Square b) {
		adjacencyList.get(a).remove(b);
		adjacencyList.get(b).remove(a);
	}
	
	// This is not really the same thing as "addEdge" so not names as such.
	// It is intended to "undo" a removeEdge.
	public void unremoveEdge (Square a, Square b) {
		adjacencyList.get(a).add(b);
		adjacencyList.get(b).add(a);
	}
	
	// TODO This is so wrong LMFAO. I must've been high or something #YOLO #SWAG
	public int[][] adjacencyMatrix () {
		int[][] adjacencyMatrix = new int[81][81];
		int i = 0;
		for (Square e:adjacencyList.keySet()) {
			// System.out.println(e+": "+adjacencyList.get(e));
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				if (adjacencyList.get(e).contains(new Square(i,j))) {
					adjacencyMatrix[i][j]=1;
				} else {
					adjacencyMatrix[i][j]=0;
				}
			}
			i++;
		}
		return adjacencyMatrix;
	}
	
	public void updatePawnSquareAdjacency() {
		
		
		
		for (Square e:adjacencyList.get(player1Square)) {
			if (!e.equals(player2Square)) {
				adjacencyList.get(e).addAll(adjacencyList.get(player1Square));
				adjacencyList.get(e).remove(e);
				adjacencyList.get(e).remove(player1Square);
			}
		}
		
		for (Square e:adjacencyList.get(player2Square)) {
			adjacencyList.get(e).addAll(adjacencyList.get(player2Square));
			adjacencyList.get(e).remove(e);
			adjacencyList.get(e).remove(player2Square);
		}
	}
	
}