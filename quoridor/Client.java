package quoridor;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		String menu = "********MENU********\n***Options***\n1:Human vs Human\t2:Human vs AI";
		System.out.print(menu);
		Boolean validInput = false;
		while (!validInput) { 
			String command = null;
			Scanner in = new Scanner(System.in);
			command = in.nextLine();
			if (command.equals("1")) {
				validInput = true;
				
				Player yamum = new HumanPlayer();
				while (!gs.isOver()) {
					String move = yamum.getMove(gs);
					gs.move(move);
				}
			}
			else if (command.equals("2")) {
				validInput = true;
				//run human vs ai
			}
			else {
				System.out.print("Invalid selection\nInput selection:");
			}
		}
		
	}
	
	/*
	public static void main(String[] args) {
		Display board = new Board();
		StringBuilder sb = new StringBuilder();
		LinkedList <String> moves = new LinkedList<String>();
		Scanner sc = new Scanner(System.in);
		board.display("");
		int i = 0;
		while (sc.hasNextLine()) {
			Validator val = new Validator();
			String temp = sc.nextLine();
			moves.add(temp);
			sb.append(temp+" ");
			System.out.println("Turn: " + i + " | Player: " + i%2 );
			if (val.check(moves)) {
				board.display(sb.toString());
			} else {
				sb.delete(sb.length()-(temp.length()+2), sb.length());
				System.out.println("Invalid Move by Player " + i%2 + ": "+temp);
			}
			i++;
		}
	}*/

}
