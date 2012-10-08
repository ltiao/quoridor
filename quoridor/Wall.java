package quoridor;

public class Wall {

	Square northWest = new Square ();
	Orientation orientation = null; 

	public Wall(Square northWest, Orientation orientation) {
		this.northWest = northWest;
		this.orientation = orientation;
	}

    public Wall(String s) {
    	// TODO Should probably have preconditions to check the string is valid though that may be the job of isValidMove
    	if (s.length() > 2) {
        	this.northWest = new Square (s.substring(0, 2));
        	this.orientation = s.charAt(2) == 'h' ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        }
    }
	
    public Wall neighbor (int row, int column, Orientation orientation) {
    	return new Wall(this.northWest.neighbor(row, column), orientation);
    }
    
	public Square getNorthWest() {
		return northWest;
	}

	public Orientation getOrientation() {
		return orientation;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Wall) {
			Wall c = (Wall)obj;
			return (c.northWest.getRow()==northWest.getRow() && c.northWest.getColumn()==northWest.getColumn() && c.orientation == orientation);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return orientation.ordinal()*northWest.hashCode();
	}

	
}
