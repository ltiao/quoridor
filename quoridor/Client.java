package quoridor;

import java.util.Scanner;

public class Client {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display board = new Board();
		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		board.display("");
		int i = 0;
		while (sc.hasNextLine()) {
			Validator val = new Validator();
			String temp = sc.nextLine();
			sb.append(temp+" ");
			System.out.println("Turn: " + i + " | Player: " + i%2 );
			if (val.check(sb.toString())) {
				board.display(sb.toString());
			} else {
				sb.delete(sb.length()-(temp.length()+2), sb.length());
				System.out.println("Invalid Move by Player " + i%2 + ": "+temp);
			}
			i++;
		}
	}

}
