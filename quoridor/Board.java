package quoridor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Board implements Display {

	static final int BOARD_SIZE = 9;
	static final char PLAYER_1_ICON = 'A';
	static final char PLAYER_2_ICON = 'B';

	Square player1Square = new Square("e9");
	Square player2Square = new Square("e1");
	LinkedList <Wall> walls = new LinkedList<Wall>();
	private HashMap <Square,Orientation> wallLookup = new HashMap<Square,Orientation>();
	
	@Override
	public void display(String moves) {
		// FIXME Need to reset fields after running display
		parse(moves);
		System.out.print("   ");
		for (char c = 'a' ; c < 'j' ; c++)
			System.out.print(c+"   ");
		System.out.println ();
		for (int i = 0 ; i < 2*BOARD_SIZE+1 ; i++) {
			for (int j = 0 ; j < 2*BOARD_SIZE+1 ; j++) {
				if (j == 0) {		
					if (i%2==0)
						System.out.print(" ");
					else
						System.out.print((i+1)>>1);
				}
				print (i,j);
			}
		}
	}

	private void parse (String moves) {
	     StringTokenizer st = new StringTokenizer(moves);
	     for (int i = 0; st.hasMoreTokens() ; i++) {
	    	 String temp = st.nextToken();
	    	 if (temp.length() == 3) {
	    		// walls.put (new Square (temp), translateOrientation (temp));
	    		 walls.add(new Wall(temp));
	    	 } else {
	    		 if (i%2==0) {
	    			 player1Square = new Square (temp);
	    		 } else {
	    			 player2Square = new Square (temp);
	    		 }
	    	 }
	     }
	     // FIXME Fix this somewhat hacky approach. Should still use Hash tables for constant lookup but the key cannot be Square. 
	     // and figure out an useful value. Orientation is pretty useless.
	     // Square should be strictly used to model actual squares on the game, not as a coordinate system for rendering the ASCII game board.
	     for (Wall e : walls) {
	    	 if (e.getOrientation() == Orientation.HORIZONTAL) {
	    		 wallLookup.put(new Square((e.getNorthWest().getRow()+1)<<1,((e.getNorthWest().getColumn()+1)<<1)+1), e.getOrientation());
	    		 wallLookup.put(new Square((e.getNorthWest().getRow()+1)<<1,((e.getNorthWest().getColumn()+1)<<1)-1), e.getOrientation());
	    	 } else {
	    		 wallLookup.put(new Square(((e.getNorthWest().getRow()+1)<<1)+1,(e.getNorthWest().getColumn()+1)<<1), e.getOrientation());
	    		 wallLookup.put(new Square(((e.getNorthWest().getRow()+1)<<1)-1,(e.getNorthWest().getColumn()+1)<<1), e.getOrientation());
	    	 }
	     }
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
	private void print (int i, int j) {
		if ((i+j)%2 == 0) {
			if (j%2 == 0) {
				System.out.print ("+");
			} else {
				if (hasPlayer(i,j))
					System.out.print (" "+player(i,j)+" ");
				else
					System.out.print ("   ");	
			}
		} else {
			if (i%2 == 0) {
				if (hasWall(i,j))
					System.out.print ("###");
				else
					System.out.print ("---");
			} else {
				if (hasWall(i,j))
					System.out.print ("#");
				else
					System.out.print ("|");		
			}

		}
		if (j == 2*BOARD_SIZE) 
			System.out.println ();
	}
	
}