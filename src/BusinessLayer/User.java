package BusinessLayer;


import CardGame.HumanPlayer;
import CardGame.Player;

public class User {
	private String name;
	private HumanPlayer player;
	private boolean gameOwner = false;
	protected boolean AIUser =false;
	
	
	
	public boolean isAIUser() {
		return AIUser;
	}

	public boolean isGameOwner() {
		return gameOwner;
	}

	public void setGameOwner(boolean gameOwner) {
		this.gameOwner = gameOwner;
	}

	public User(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setPlayer(HumanPlayer player){
		this.player = player;
	}

	public Player getPlayer() {
		return player;		
	}
}
