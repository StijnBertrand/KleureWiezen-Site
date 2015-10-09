package BusinessLayer;

import CardGame.HumanPlayer;

public class User {
	private String name;
	private HumanPlayer player;
	
	
	private String Game;
	private boolean owner;
	
	public User(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	
	
}
