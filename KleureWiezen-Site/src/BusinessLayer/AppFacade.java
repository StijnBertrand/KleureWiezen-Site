package BusinessLayer;

import java.util.ArrayList;

import KleurenWiezen.Game;
import KleurenWiezen.KleureWiezenFactory;

public class AppFacade {
	KleureWiezenFactory kf = KleureWiezenFactory.getInstance();
	Users users = new Users();
	ArrayList<Game> games;
	
	public AppFacade(){
		games = new ArrayList<Game>();
	}
	
	//add a new user
	public Integer AddUser(String name) {
		return users.AddPlayer(name);
	}

	//remove a user
	public void removeUser(Integer loginid) {
		users.removePlayer(loginid);
	}
	
	//add a new game
	public void addGame(){
		//check if the owner has a game already
		games.add(kf.getGame());
		// add the owner to the game
	}
	
	//remove a game
	public void removeGame(int index){
		//check is the game has not yet started
		//let all players know that the game has been canceled
		//remove the game
		games.remove(index);
	}
	
	//start a game
	public void startGame(){
		//check if there are enough players
		//can only be done by the owner
	}
	
	//add a player to a game
	public void addPlayer(){
		//check if the player has joined a game already
		// check if the player is not null
		
	}
	
	//remove a player from a game
	public void removePlayer(){
		
	}
	
	//let a player play a card
	public void playCard(){
		
	}
	
	//let a player place a bed
	public void placeBed(){
		
	}

	
	
	
	
	
	
	public String getGamesXml(){
		String XML = "<games>";
		for(Game game: games){
			XML = XML + "<game><name>"+ "kleurewiezen" +"</name></game>";
		}
		XML += "</games>";
		return XML;
	}


	public String getUsersXml() {
		return users.getUsersXml();
	}

}
