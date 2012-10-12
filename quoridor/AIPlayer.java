package quoridor;

import java.util.List;

public class AIPlayer implements Player {

	@Override
	public String getMove(GameState gs) {
		List<String> validMoves = gs.validMoves();
		return validMoves.get((int)(Math.random() * (validMoves.size() + 1)));
	}

}
