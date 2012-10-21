package quoridor;

import java.util.Scanner;

public class HumanPlayer implements Player {

	@Override
	public String getMove(GameState gs) {
        System.out.println(gs);
        System.out.print("Enter next move [(U)ndo | (R)edo | (S)ave]: ");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
}