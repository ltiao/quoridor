package quoridor;

import java.util.List;

/**
 * @author louistiao
 * Forrest Gump. Naive, simple-minded but good-hearted.
 * He practically never tries to block you and only 
 * seeks to reach his own goal by the fastest means
 * possible with help from his uncanny athleticism.
 * tl;dr - doesn't place walls, uses shortest paths
 */
public class ForrestGump extends AIPlayer {

	@Override
	public String getMove(GameState gs) {
		List <Square> path = gs.shortestPathToWin();
		if (gs.isValidTraversal(path.get(0))) {
			return path.get(0).toString();
		} else {
			List <String> validMoves = gs.validMoves();
			return validMoves.get((int)(Math.random() * validMoves.size()));
		}
	}
	
}
