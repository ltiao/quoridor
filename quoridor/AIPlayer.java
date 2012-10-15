package quoridor;

public abstract class AIPlayer implements Player {

	String bestMove = new String();
	
	public abstract String getMove(GameState gs);
	
	public int negamax (GameState node, int depth) {
		int best = Integer.MIN_VALUE;
		int val;
		if (node.isOver() || depth == 0) {
			return heuristic(node);
		}
		for (String move: node.validMoves()) {
			GameState child = new GameState(node);
			child.move(move);
			val = -negamax(child, depth-1);
			if (val > best) {
				best = val;
				bestMove = move;
			}
		}
		return best;
	}
	
	public int minimaxAlphaBetaWithMove (GameState node, int depth, int alpha, int beta, boolean maxPlayer ) {
		if (depth == 0 || node.isOver()) {
			return heuristic(node);
		}
		if (maxPlayer) {
			int val;
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				val = minimaxAlphaBeta(child, depth-1, alpha, beta, false);
				if (val > alpha) {
					alpha = val;
					bestMove = move;
				}
				if (beta <= alpha) {
					break;
				}
			}
			return alpha;
		} else {
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				beta = Math.min(beta, minimaxAlphaBeta(child, depth-1, alpha, beta, true));
				if (beta <= alpha) {
					break;
				}
			}
			return beta;
		}
	}
	
	public int minimaxAlphaBeta(GameState node, int depth, int alpha, int beta, boolean maxPlayer ) {
		if (depth == 0 || node.isOver()) {
			return heuristic(node);
		}
		if (maxPlayer) {
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				alpha = Math.max(alpha, minimaxAlphaBeta(child, depth-1, alpha, beta, false));
				if (beta <= alpha) {
					break;
				}
			}
			return alpha;
		} else {
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				beta = Math.min(beta, minimaxAlphaBeta(child, depth-1, alpha, beta, true));
				if (beta <= alpha) {
					break;
				}
			}
			return beta;
		}
	}
	
	public int heuristic(GameState gs) {
		return gs.shortestPathToRow(gs.player1Square, 0).size()-gs.shortestPathToRow(gs.player2Square, 8).size();
	}
	
	public int negamaxAlphaBeta(GameState node, int depth, int alpha, int beta, int color) {
		if (depth == 0 || node.isOver()) {
			return color*heuristic(node);
		} else {
			for (String move:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(move);
				int val = -negamaxAlphaBeta(child, depth-1, -beta, -alpha, -color);
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
