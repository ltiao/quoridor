package quoridor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
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
				player1 = new HumanPlayer();
				player2 = new HumanPlayer();
			}
			else if (command.equals("2")) {
				validInput = true;
				player1 = new HumanPlayer();
				player2 = new AIPlayer();
			}
			else if (command.equals("3")) {
				System.out.println("Enter file name:");
				String FileName = null;
				Scanner fileIn = new Scanner(System.in);
				FileName = fileIn.nextLine();
				BufferedReader br = new BufferedReader(new FileReader(FileName));
				try {
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					
					while (line != null) {
						sb.append(line);
						sb.append("\n");
						line = br.readLine();
					}
					String gameString = sb.toString();
				} finally {
					br.close();
				}
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
