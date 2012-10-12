package quoridor;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameState {
	/**
	 * A hybrid graph representation as suggested by van Rossum and Cormen et al:
	 * A hash table is used to associate each vertex with a doubly linked list of adjacent vertices
	 */
	protected HashMap <Square,LinkedList<Square>> adjacencyList = new HashMap <Square,LinkedList<Square>> ();
	List <String> moves = new LinkedList<String>();
	Square player1Square = new Square("e9");
	Square player2Square = new Square("e1");
	LinkedList <Wall> player2Walls = new LinkedList<Wall>();
	LinkedList <Wall> player1Walls = new LinkedList<Wall>();
	Integer turn = 0;

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
	
	/**
	 * Mutator method for mutating game state. Return false if invalid move.
	 * @param move
	 */
	public boolean move (String move) {
		boolean valid = true;
		if (isWallPlacement(move)) {
			valid &= isValidWallPlacement(new Wall(move));
			if (turn%2 == 0) {
				System.out.println(player1Walls.size());
				valid &= player1Walls.size() < 10;
				if (valid) {
					player1Walls.add(new Wall(move));
					turn++;
				}
			} else {
				System.out.println(player2Walls.size());
				valid &= player2Walls.size() < 10;
				if (valid) {
					player2Walls.add(new Wall(move));
					turn++;
				}
			}
		} else {
			if (turn%2 == 0) {
				valid = isValidTraversal(player1Square, new Square(move));
				if (valid) {
					player1Square = new Square (move);
					turn++;
				}
			} else {
				valid = isValidTraversal(player2Square, new Square(move));
				if (valid) {
					player2Square = new Square (move);
					turn++;
				}
			}
		}
		return valid;
	}
	
	protected boolean isWallPlacement (String move) {
		return move.length() == 3;
	}
	
	protected boolean isTraversal (String move) {
		return move.length() == 2;
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
		if (b.equals(player1Square) || b.equals(player2Square)) {
			return false;
		}
		if (adjacencyList.get(a).contains(player1Square) || adjacencyList.get(a).contains(player2Square)) {
			return adjacencyList.get(player1Square).contains(b) || adjacencyList.get(player2Square).contains(b);
		} else if (adjacencyList.get(a).contains(b)) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if any player has reached the other side
	 */
	public boolean isOver() {
		return player1Square.getRow() == 0 || player2Square.getRow() == 8;
	}

	protected void displayAdjacencyList () {
		for (Square e:adjacencyList.keySet()) {
			System.out.println(e+": "+adjacencyList.get(e));
		}
	}

	protected List<Square> shortestPath (Square src, Square dest) {
		List<Square> path = new LinkedList<Square>();
		Queue <Square> queue = new LinkedList<Square>();
		HashMap <Square,Square> parentNode = new HashMap<Square,Square>();
		// enqueue start configuration onto queue
		queue.add(src);
		// mark start configuration
		parentNode.put(src, null);
		while (!queue.isEmpty()) {
			Square t = queue.poll();
			if (t.equals(dest)) {
				while (!t.equals(src)) {
					path.add(t);
					t = parentNode.get(t);
				}
				Collections.reverse(path);
				return path;
			}
			for (Square e: adjacencyList.get(t)) {
				if (!parentNode.containsKey(e)) {
					parentNode.put(e, t);
					queue.add(e);
				}
			}
		}
		return path;
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

		LinkedList<Wall> walls = new LinkedList<Wall>();
		walls.addAll(player1Walls);
		walls.addAll(player2Walls);

		// Check wall is not being placed at border
		if (wall.northWest.getColumn()==8 || wall.northWest.getRow()==8) {
			return false;
		}

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
			//walls.add(wall);
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


}
