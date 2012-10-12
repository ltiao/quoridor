package quoridor;

public class UserInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Player player1 = new HumanPlayer();
		Player player2 = new AIPlayer();
		String move = new String();
		GameState gs = new GameState();
		while(!gs.isOver()) {
			if (gs.currentPlayer() == 0) {
				move = player1.getMove(gs);
			} else {
				move = player2.getMove(gs);
			}
			if (!gs.move(move)) {
				System.out.println("Invalid move");
			}
		}
		System.out.println("Game Over!");
	}

}
