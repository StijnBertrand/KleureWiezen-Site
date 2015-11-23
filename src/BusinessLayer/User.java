package BusinessLayer;


import CardGame.HumanPlayer;

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
	
	public void playCard(int index){
		if(player.playCard(index)){
			//notify game
		}
	}
	
	//update variables
	protected final Object lock = new Object();
	private boolean updateAsk = false;
	
	//used by the Servlet to ask if there was an update in the game
	//Blocks and waits until there is an update
	public String Update(){
		return null;
	}
}
