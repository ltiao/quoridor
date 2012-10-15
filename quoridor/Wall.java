package quoridor;

public class Wall {

	@Override
	public String toString() {
		return northWest.toString() + orientation.name().toLowerCase().charAt(0);
	}

	Square northWest = new Square ();
	Orientation orientation = null; 

	/**
	 * @param northWest Square located at the start of the wall
	 * @param orientation direction that the wall will go in (vertical or horizontal)
	 */
	public Wall(Square northWest, Orientation orientation) {
		this.northWest = northWest;
		this.orientation = orientation;
	}

    /**
     * @param s string constructor
     */
    public Wall(String s) {
    	// TODO Should probably have preconditions to check the string is valid though that may be the job of isValidMove
    	if (s.length() > 2) {
        	this.northWest = new Square (s.substring(0, 2));
        	this.orientation = s.charAt(2) == 'h' ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        }
    }
	
    /**
     * @param row row the wall will begin from
     * @param column column the wall will begin from
     * @param orientation orientation of the wall
     * @return a wall containing the elements specified in the parameters
     */
    public Wall neighbor (int row, int column, Orientation orientation) {
    	return new Wall(this.northWest.neighbor(row, column), orientation);
    }
    
	/**
	 * @return square where the wall begins
	 */
	public Square getNorthWest() {
		return northWest;
	}

	/**
	 * @return orientation of the current wall
	 */
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
