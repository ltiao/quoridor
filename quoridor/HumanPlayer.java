package quoridor;

import java.io.*;

public class HumanPlayer implements Player {

	@Override
	public String getMove(GameState gs) {
		String str = "";
		try {
		    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		    while (str != null) {
		        System.out.print(gs);
		        str = in.readLine();
		    }
		} catch (IOException e) {
			str = "Invalid input!";
			System.out.println(str);
		}
		return str;
	}
}