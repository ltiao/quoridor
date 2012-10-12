package quoridor;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameState {
	static final int BOARD_SIZE = 9;
	static final char PLAYER_1_ICON = 'A';
	static final char PLAYER_2_ICON = 'B';
	/**
	 * A hybrid graph representation as suggested by van Rossum and Cormen et al:
	 * A hash table is used to associate each vertex with a doubly linked list of adjacent vertices
	 */
	protected HashMap <Square,LinkedList<Square>> adjacencyList = new HashMap <Square,LinkedList<Square>> ();
	protected HashMap <Square,Orientation> wallLookup = new HashMap<Square,Orientation>();
	Square player1Square = new Square("e9");
	Square player2Square = new Square("e1");
	LinkedList <Wall> player1Walls = new LinkedList<Wall>();
	LinkedList <Wall> player2Walls = new LinkedList<Wall>();
	Integer turn = 0;

	public GameState() {
		// Initialize adjacency list
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				LinkedList<Square> adjacent = new LinkedList<Square>();
				for (int d = -1; d < 2; d++) {
					if (d != 0) { // Vertices are not self-connecting
						if (i+d >= 0 && i+d < BOARD_SIZE)
							adjacent.add(new Square(i+d,j));
						if (j+d >= 0 && j+d < BOARD_SIZE)
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
			Wall wall = new Wall(move);
			valid &= isValidWallPlacement(wall);
			if (turn%2 == 0) {
				valid &= player1Walls.size() < 10;
				if (valid) {
					player1Walls.add(wall);
					if (wall.getOrientation() == Orientation.HORIZONTAL) {
						removeEdge(wall.northWest, wall.northWest.neighbor(1, 0));
						removeEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
						wallLookup.put(new Square((wall.getNorthWest().getRow()+1)<<1,((wall.getNorthWest().getColumn()+1)<<1)+1), wall.getOrientation());
						wallLookup.put(new Square((wall.getNorthWest().getRow()+1)<<1,((wall.getNorthWest().getColumn()+1)<<1)-1), wall.getOrientation());
					} else {
						removeEdge(wall.northWest, wall.northWest.neighbor(0, 1));
						removeEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
						wallLookup.put(new Square(((wall.getNorthWest().getRow()+1)<<1)+1,(wall.getNorthWest().getColumn()+1)<<1), wall.getOrientation());
						wallLookup.put(new Square(((wall.getNorthWest().getRow()+1)<<1)-1,(wall.getNorthWest().getColumn()+1)<<1), wall.getOrientation());
					}
					turn++;
				}
			} else {
				valid &= player2Walls.size() < 10;
				if (valid) {
					player2Walls.add(wall);
					if (wall.getOrientation() == Orientation.HORIZONTAL) {
						removeEdge(wall.northWest, wall.northWest.neighbor(1, 0));
						removeEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
						wallLookup.put(new Square((wall.getNorthWest().getRow()+1)<<1,((wall.getNorthWest().getColumn()+1)<<1)+1), wall.getOrientation());
						wallLookup.put(new Square((wall.getNorthWest().getRow()+1)<<1,((wall.getNorthWest().getColumn()+1)<<1)-1), wall.getOrientation());
					} else {
						removeEdge(wall.northWest, wall.northWest.neighbor(0, 1));
						removeEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
						wallLookup.put(new Square(((wall.getNorthWest().getRow()+1)<<1)+1,(wall.getNorthWest().getColumn()+1)<<1), wall.getOrientation());
						wallLookup.put(new Square(((wall.getNorthWest().getRow()+1)<<1)-1,(wall.getNorthWest().getColumn()+1)<<1), wall.getOrientation());
					}
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

	protected boolean isValidSyntax (String move) {
		return false;
	}

	protected boolean isWallPlacement (String move) {
		return move.length() == 3;
	}

	protected boolean isTraversal (String move) {
		return move.length() == 2;
	}
	
	public Integer turn () {
		return turn;
	}
	
	
	/**
	 * The player who's turn it is.
	 * @return 0 if player 1, 1 if player 2.
	 */
	public Integer currentPlayer () {
		return turn%2;
	}

	public Square currentPlayerPosition () {
		return currentPlayer () == 0 ? player1Square : player2Square;
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
		for (int i = 0; i < BOARD_SIZE; i++) {
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
			if (wall.orientation==Orientation.HORIZONTAL) {
				addEdge(wall.northWest, wall.northWest.neighbor(1, 0));
				addEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
			} else {
				addEdge(wall.northWest, wall.northWest.neighbor(0, 1));
				addEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
			}
			return true;
		} else {
			if (wall.orientation==Orientation.HORIZONTAL) {
				addEdge(wall.northWest, wall.northWest.neighbor(1, 0));
				addEdge(wall.northWest.neighbor(0, 1), wall.northWest.neighbor(1,1));
			} else {
				addEdge(wall.northWest, wall.northWest.neighbor(0, 1));
				addEdge(wall.northWest.neighbor(1, 0), wall.northWest.neighbor(1,1));
			}
			return false;
		}
	}

	// Need to check preconditions
	public void removeEdge (Square a, Square b) {
		adjacencyList.get(a).remove(b);
		adjacencyList.get(b).remove(a);
	}
	
	// Need to check preconditions
	public void addEdge (Square a, Square b) {
		adjacencyList.get(a).add(b);
		adjacencyList.get(b).add(a);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Turn: "+turn+" | Player to Move: "+currentPlayer()+"\n");
		sb.append("Valid Moves: "+validMoves()+"\n");
		sb.append("   ");
		for (char c = 'a' ; c < 'j' ; c++)
			sb.append(c+"   ");
		sb.append("\n");
		for (int i = 0 ; i < 2*BOARD_SIZE+1 ; i++) {
			for (int j = 0 ; j < 2*BOARD_SIZE+1 ; j++) {
				if (j == 0) {		
					if (i%2==0)
						sb.append(" ");
					else
						sb.append((i+1)>>1);
				}
				sb.append (print (i,j));
			}
		}
		return sb.toString();
	}
	
	private boolean hasPlayer (int i, int j) {
		// TODO Should restrict return values to be injective. (The rounding down of division could is potentially problematic).  
		Square transformedPosition = new Square((i-1)>>1,(j-1)>>1);
		return player1Square.equals(transformedPosition) || player2Square.equals(transformedPosition);
	}

	private boolean hasWall (int i, int j) {
		return wallLookup.containsKey(new Square(i, j));
	}
	
	private char player (int i, int j) {
		Square transformedPosition = new Square((i-1)>>1,(j-1)>>1);
		return player1Square.equals(transformedPosition) ? PLAYER_1_ICON : PLAYER_2_ICON;
	}

	// TODO Kind of a first world problem, but could make use of Java's unicode support to render a more aesthetic board.
	private String print (int i, int j) {
		StringBuilder sb = new StringBuilder();
		if ((i+j)%2 == 0) {
			if (j%2 == 0) {
				sb.append ("+");
			} else {
				if (hasPlayer(i,j))
					sb.append (" "+player(i,j)+" ");
				else
					sb.append ("   ");	
			}
		} else {
			if (i%2 == 0) {
				if (hasWall(i,j))
					sb.append ("###");
				else
					sb.append ("---");
			} else {
				if (hasWall(i,j))
					sb.append ("#");
				else
					sb.append ("|");		
			}

		}
		if (j == 2*BOARD_SIZE) 
			sb.append ("\n");
		return sb.toString();
	}
	
	public Integer score () {
		
		return 0;
	}
	
	public List<String> validMoves() {
		List<String> validMoves = new LinkedList<String>();
		if (currentPlayer() == 0) {
			for (Square s:adjacencyList.get(player1Square)) {
				validMoves.add(s.toString());
			}
		} else {
			for (Square s:adjacencyList.get(player2Square)) {
				validMoves.add(s.toString());
			}	
		}
		for (int i = 0; i < BOARD_SIZE ; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				for (Orientation o: Orientation.values()) {
					Wall wall = new Wall(new Square(i, j), o);
					if (isValidWallPlacement(wall)) {
						validMoves.add(wall.toString());
					}
				}
			}
		}
		return validMoves;
	}
	
}
