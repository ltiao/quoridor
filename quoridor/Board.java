package quoridor;

import java.util.StringTokenizer;

public class Board implements Display {

	static final int BOARD_SIZE = 9;
	GameState gs = new GameState();
	
	@Override
	public void display(String moves) {
		StringTokenizer st = new StringTokenizer(moves);
		while (st.hasMoreTokens()) {
			gs.move(st.nextToken());
		}
		System.out.println(gs);
	}
	
}