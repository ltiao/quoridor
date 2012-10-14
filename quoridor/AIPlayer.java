package quoridor;

import java.util.List;

public class AIPlayer implements Player {

	String best = new String();
	
	@Override
	public String getMove(GameState gs) {
		List<String> validMoves = gs.validMoves();
		//System.out.println(validMoves);
		System.out.println(alphabeta(gs, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true));
		System.out.println(negamax(gs, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		//System.out.println(best);
		return validMoves.get((int)(Math.random() * validMoves.size()));
	}
	
	public int alphabeta(GameState node, int depth, int alpha, int beta, boolean maxPlayer ) {
		if (depth == 0 || node.isOver()) {
			return heuristic(node);
		}
		if (maxPlayer) {
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				alpha = Math.max(alpha, alphabeta(child, depth-1, alpha, beta, false));
				if (beta <= alpha) {
					break;
				}
			}
			return alpha;
		} else {
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				beta = Math.min(beta, alphabeta(child, depth-1, alpha, beta, true));
				if (beta <= alpha) {
					break;
				}
			}
			return beta;
		}
	}
	
	public int heuristic(GameState gs) {
		return gs.player2Square.getRow()-8;
	}
	
	public int negamax(GameState node, int depth, int alpha, int beta, int color) {
		if (depth == 0 || node.isOver()) {
			return color*heuristic(node);
		} else {
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				int val = -negamax(child, depth-1, -beta, -alpha, -color);
				if (val >= beta) {
					return val;
				}
				if (val >= alpha) {
					alpha = val;
				}
			}
			return alpha;
		}
	}
	
}
