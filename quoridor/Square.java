package quoridor;

import java.util.LinkedList;
import java.util.List;

// TODO Square should be inherited from a Point object so point can be
// also be used by the coordinate system for rendering the ASCII game board.
// All properties inherited except:
// Constructor should modulo integer parameters by Board.BOARD_SIZE to guarantee perfect hashing.
// toString should print coordinates in Quoridor text format.

public class Square {
	private int row;
	private int column;
	
    public Square() {
    }
	
    public Square(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    public Square(String s) {
    	// TODO Should probably have preconditions to check the string is valid though that may be the job of isValidMove
    	if (s.length() > 1) {
        	this.row = s.charAt(1)-'1';
        	this.column = s.charAt(0)-'a';    	
        }
    }

    public Square(Square sq) {
    	this.row = sq.getRow();
    	this.column = sq.getColumn();
    }
    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    
    //Precondition
    public Square neighbor(int row, int column) {
    	return new Square(this.row+row, this.column+column);
    }
    
    public List<Square> neighborhood (int r) {
		List <Square> neighbors = new LinkedList<Square>();
    	for (int d = -r; d < r+1; d++) {
			if (d != 0) {
				if (row+d >= 0 && row+d < 9) {
					neighbors.add(new Square(row+d,column));
				}
				if (column+d >= 0 && column+d < 9) {
					neighbors.add(new Square(row,column+d));
				}
			}
		}
    	return neighbors;
    }
    
    @Override
	public String toString() {
    	//return "[Row: "+row+", Column: "+column+"]";
    	char row = '1';
    	char column = 'a';
    	row += this.row;
    	column += this.column;
    	return ""+column+row;
    }

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Square) {
			Square c = (Square)obj;
			return (c.row==row && c.column==column);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 9*row+column;
	}
	
	public boolean isCardinalTo(Square sq) {
		return (row - sq.row != 0) ^ (column - sq.column != 0);
	}
	
	public Square opposite(Square sq) {
		return new Square(2*sq.row - row, 2*sq.column - column);
	}
    
    
}
