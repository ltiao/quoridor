	package quoridor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

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
		Scanner sc = new Scanner(System.in);
		Player player1 = new HumanPlayer();
		Player player2 = new HumanPlayer();
		GameState gs = new GameState();
		String s = new String();
		String move = new String();
		int lvl = 0;
		boolean valid;
		do {
			System.out.print("Quoridor - (N)ew Game | (L)oad Game: ");
			s = sc.nextLine();
			if (s.equalsIgnoreCase("n")) {
				valid = true;
				do {
					System.out.print("Human vs. (H)uman | Human vs. (A)I: ");
					s = sc.nextLine();
					if (s.equalsIgnoreCase("h")) {
						valid = true;
					} else if (s.equalsIgnoreCase("a")) {
						valid = true;
						do {
							System.out.print("Difficulty [1-4]: ");
							lvl = sc.nextInt();
							if (lvl > 0 && lvl < 5) {
								valid = true;
								if (lvl == 1) {
									player2 = new ForrestGump();
								} else {
									player2 = new Miranda(lvl);
								}
							} else {
								valid = false;
							}
						} while (!valid);
					} else {
						valid = false;
					}
				} while (!valid);
			} else if (s.equalsIgnoreCase("l")) {
				valid = true;
				File file;
				do {
					System.out.print("Enter filename: ");
					file = new File(sc.nextLine());
				} while (!file.exists());
		        try {
		            Scanner scanner = new Scanner(file);
		            lvl = Integer.valueOf(scanner.nextLine());
		            player1 = scanner.nextLine().equalsIgnoreCase("HumanPlayer") ? new HumanPlayer() : (lvl == 1 ? new ForrestGump(): new Miranda(lvl));
		            player2 = scanner.nextLine().equalsIgnoreCase("HumanPlayer") ? new HumanPlayer() : (lvl == 1 ? new ForrestGump(): new Miranda(lvl));
		            String movesString = scanner.nextLine();
		            movesString = movesString.substring(1, movesString.length()-1);
		            StringTokenizer st = new StringTokenizer(movesString, ", "); 
		            while (st.hasMoreTokens()) {
		            	moves.add(st.nextToken());
		            }
		            gs = new GameState(moves);
		            scanner.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        }
			} else {
				valid = false;
			}
		} while (!valid);
		while(!gs.isOver()) {
			if (gs.currentPlayer() == 0) {
				move = player1.getMove(gs);
			} else {
				move = player2.getMove(gs);
			}
			if (move.equalsIgnoreCase("s")) {	
				try {
					String content = lvl + "\n"+player1.getClass().getSimpleName()+"\n"+player2.getClass().getSimpleName()+"\n"+moves.toString();
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
