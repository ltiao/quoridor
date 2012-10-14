package quoridor;

import java.util.LinkedList;

public class Pawn {

	int id;
	Square position;
	LinkedList <Wall> walls = new LinkedList<Wall>();

	public Pawn(int id) {
		if (id == 0) {
			position = new Square("e9");
		} else if (id == 1) {
			position = new Square("e1");
		}
	}
	
}
