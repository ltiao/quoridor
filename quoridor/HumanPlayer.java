package quoridor;

import java.io.*;
import java.util.Scanner;

public class HumanPlayer implements Player {

	@Override
	public String getMove(GameState gs) {
        System.out.print(gs);
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
}