package quoridor;

import java.util.List;
import static java.util.Arrays.asList;

public class AIPlayer implements Player {

	List <String> shiller = asList("e8", "e2", "e7", "e3", "e6", "e4", "c8v");
	List <String> ala = asList("e8", "e2", "e7", "e3", "e6", "e4", "d6h", "f6h", "c5v", "g5v");
	
	@Override
	public String getMove(GameState gs) {
		List<String> validMoves = gs.validMoves();
		//System.out.println(validMoves);
		//if (gs.turn() < 7) {
		//	return shiller.get(gs.turn());
		//} else {
			System.out.println(negamax(gs, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));	
			return validMoves.get((int)(Math.random() * validMoves.size()));
		//}
	}
	
	public int negamax(GameState node, int depth, int alpha, int beta, int color) {
		System.out.println(node);
		if (node.isOver() || depth == 0) {
			int temp = color * node.heuristic();
			System.out.println(temp);
			return temp;
		} else {
			for (String e:node.validMoves()) {
				GameState child = new GameState(node);
				child.move(e);
				int val = -negamax( child, depth-1, -beta, -alpha, -color);
				if (val >= beta) {
					System.out.println(val);
					return val;
				}
				if (val >= alpha) {
					alpha = val;
				}
			}
			System.out.println(alpha);
			return alpha;
		}
	}
	
}
