package quoridor;

import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("(N)ew Game | (L)oad Game:");
		if (sc.nextLine().equalsIgnoreCase("N")) {
			System.out.println("New game");
		} else if (sc.nextLine().equalsIgnoreCase("L")) {
			System.out.println("Load game");
		} else {
			
		}
	}

}
