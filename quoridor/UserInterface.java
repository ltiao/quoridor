	package quoridor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
	
	static List <String> moves = new LinkedList <String>();
	
	/**
	 * Prints out a menu before allowing the user to select what game type 
	 * they would like to play and other options regarding the functionality
	 * of the game such as loading a saved game. 
	 * @param args 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String menu = "\t********MENU********\n\t\t***Options***\n1:Human vs Human\t2:Human vs AI\t3:Load Saved Game";
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
				int ai = 2;
				boolean validLevel = false;
				while (!validLevel) {
					System.out.println("Enter the level of AI expertise: (1-4)");
					ai = in.nextInt();
					if (ai == 1) {
						validLevel = true;
						player2 = new ForrestGump();
					} else if (ai <= 4) {
						validLevel = true;
						player2 = new Miranda(ai);
					}
					else {
						System.out.println("Invalid level number");
					}
				}
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
					System.out.println(gameString);
				} catch(FileNotFoundException e) {
					System.err.println(e.getMessage());
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
			if (move.equalsIgnoreCase("s")) {	
				try {
					String content = "["+player1.getClass().getSimpleName()+"] ["+player2.getClass().getSimpleName()+"] "+moves.toString();
					Scanner sc = new Scanner(System.in);
					File file;
					do {
						System.out.print("Enter filename: ");
						file = new File(sc.nextLine()+".qr");
					} while (file.exists());
					
					file.createNewFile();
		 
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(content);
					bw.close();
		 
					System.out.println("Game saved successfully.");
		 
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (move.equalsIgnoreCase("u")) {
				//System.out.println(moves.subList(0, gs.turn-2));
				if (gs.turn() >= 2) {
					gs = new GameState(moves.subList(0, gs.turn()-2));	
				} else {
					System.out.println("Nothing to undo!");
				}
			} else if (move.equalsIgnoreCase("r")) {
				if (gs.turn()+2 <= moves.size()) {
					gs = new GameState(moves.subList(0, gs.turn()+2));
				} else {
					System.out.println("Nothing to redo!");
				}
			} else if (!gs.move(move)) {
				System.out.println("Invalid move");
			} else {
				moves.add(move);
			}
		}
		System.out.println("Game Over! The winner is player "+gs.winnerIcon()+"!");
	}

}
