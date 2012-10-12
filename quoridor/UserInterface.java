package quoridor;

import java.util.Scanner;

public class UserInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String menu = "********MENU********\n***Options***\n1:Human vs Human\t2:Human vs AI";
		System.out.print(menu);
		Boolean validInput = false;
		Player player1 = new HumanPlayer();
		Player player2 = new HumanPlayer();
		while (!validInput) { 
			String command = null;
			Scanner in = new Scanner(System.in);
			command = in.nextLine();
			if (command.equals("1")) {
				validInput = true;
			}
			else if (command.equals("2")) {
				validInput = true;
				player2 = new AIPlayer();
			}
			else {
				System.out.print("Invalid selection\nInput selection:");
			}
		}
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
