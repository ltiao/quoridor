package quoridor;

import java.util.HashMap;

public class AIPlayer implements Player {

	static final int DEPTH = 3;
	String bestMove = new String();
	HashMap <GameState, Integer> value = new HashMap<GameState, Integer>();
	
	@Override
	public String getMove(GameState gs) {
		//List<String> validMoves = gs.validMoves();
		//System.out.println(validMoves);
		System.out.println(minimaxAlphaBetaWithMove(gs, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true));
		//System.out.println(negamaxAlphaBeta(gs, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		//System.out.println(negamax(gs, 3));
		//System.out.println(bestMove);
		//return validMoves.get((int)(Math.random() * validMoves.size()));
		return bestMove;
	}
	
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
		return gs.player2Square.getRow()-8;
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
