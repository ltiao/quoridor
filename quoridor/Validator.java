package quoridor;

import java.util.Iterator;
import java.util.List;

public class Validator {

	GameState gs = new GameState();

	/**
	 * Check the validity of a given sequence of moves.
	 * The sequence is valid if and only if each (space separated)
	 * move in the list is valid,
	 * starting from the initial position of the game.
	 * When the game has been won, no further moves are valid.
	 * @param moves a list of successive moves
	 * @return validity of the list of moves
	 */
	public boolean check(List<String> moves) {
		boolean valid = true;
		Iterator<String> itr = moves.iterator();
		while (valid && itr.hasNext()) {
			//System.out.println(gs);
			valid &= gs.move(itr.next());
		}
		return valid;
	}
	
}