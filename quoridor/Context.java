package quoridor;

public class Context {
	private Player player;
	
	public Context(Player player) {
		this.player = player;
	}
	
	public String executePlayerGetMove (GameState gs) {
		return player.getMove(gs);
	}
}
