package BusinessLayer;

import java.util.UUID;

public class Game {

	private User[] players;
	private UUID id;
	private boolean started = false;



	public Game(){
		players = new User[4];
		id = UUID.randomUUID();
	}
	
	
	public boolean addPlayer(User user) {
		for(int i = 0;i<4;i++){
			if(players[i] == null){
				players[i]= user;
				return true;
			}
		}
		//there was no place in the game
		return false;
	}

	public synchronized boolean removePlayer(User user) {		
		//a player can not leave the game once it is started
		if(started)return false;
		for(int i =0;i<4;i++){
			if(players[i] == user){
				players[i] = null;
				//give the game a new game owner
				//if there are no players left the game will be removed (room.post action.equals("leave")
				if(user.isGameOwner()){
					user.setGameOwner(false);
					for(int j =0;j<4;j++){
						if(players[j] != null && !players[j].isAIUser()){
							players[j].setGameOwner(true);
							break;
						}
					}		
				}
				return true;
			}
		}
		//the player was not found in the game (something went wrong)
		return false;
	}
	
	public void removeAIUser(){
		for(int j =0;j<4;j++){
			if(players[j] != null && players[j].isAIUser()){
				players[j]=null;
				break;
			}
		}	
	}
	
	public boolean started() {
		return started;
	}


	public boolean empty() {
		for(int i = 0;i<4;i++){
			if(players[i]!=null && !players[i].isAIUser()){
				return false;
			}
		}
		return true;
	}

	public synchronized void notifyUsers(){
		for(User user: players){
			user.notify();
		}
	}
	
	public UUID getId() {
		return id;
	}

	public String getGameXML() {
		String XML = "<game>";
			XML = XML +  "<game-name>" + "kleurewiezen" + "</game-name>";
			XML = XML +  "<game-id>" + id.toString() + "</game-id>";
			
			XML += "<players>";
				for(User player: players){
					if(player != null){
						XML = XML + "<player><player-name>"+ player.getName() +"</player-name></player>";
					}
				}
			XML += "</players>";
		XML += "</game>";
		return XML;
	}
}
