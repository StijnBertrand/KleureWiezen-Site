package BusinessLayer;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

public class AppFacade {
	
	ArrayList<User> users;
	HashMap<UUID, Game> games;
	
	public AppFacade(){
		games = new HashMap<UUID, Game>();
		users = new ArrayList<User>();
	}
	
	//add a new user
	public User AddUser(String name) {
		User user = new User(name);
		users.add(user);
		return user;
	}

	//remove a user
	public void removeUser(User user) {
		users.remove(user);
	}
	
	//add a new game
	public Game addGame(){
		Game game = new Game();
		games.put(game.getId(),game);
		return game;
	}
	
	public Game getGame(UUID id){
		return games.get(id);
	}
	
	//remove a game
	public synchronized boolean removeGame(UUID id){
		if(games.get(id).empty()){
			games.remove(id);
			return true;
		}
		return false;
	}
	
	public User getAIUser() {
		return new AIUser();
	}
	
	public String getGamesXml(){
		String XML = "<games>";    
		for(Game game: games.values()){
			XML += game.getGameXML();
		}
		XML += "</games>";
		return XML;
	}


	public String getUsersXml() {
		String XML = "<users>";
		for(User user: users){
			XML = XML + "<user><name>"+ user.getName() +"</name></user>";
		}
		XML += "</users>";
		return XML;
	}

	

}
