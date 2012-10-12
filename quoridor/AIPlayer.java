package quoridor;

import java.util.List;

public class AIPlayer implements Player {

	@Override
	public String getMove(GameState gs) {
		List<String> validMoves = gs.validMoves();
		//System.out.println(validMoves);
		System.out.println(minimax (gs, 3));
		return validMoves.get((int)(Math.random() * (validMoves.size() + 1)));
	}

	public int minimax (GameState gs, int depth) {
		if (gs.isOver() || depth <= 0) {
			return gs.heuristic();
		}
		int a = Integer.MIN_VALUE;
		for (String e:gs.validMoves()) {
			GameState child = new GameState(gs);
			child.move(e);
			a = Math.max(a, -minimax(child, depth-1));
		}
		return a;
	}
	
}
