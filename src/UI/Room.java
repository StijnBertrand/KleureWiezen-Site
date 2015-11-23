package UI;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BusinessLayer.AppFacade;
import BusinessLayer.Game;
import BusinessLayer.User;

public class Room extends Page{
	
	public Room(AppFacade facade,Pages pages){
		super( facade,pages);
	}
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		if(user != null){
			if(game != null && game.started()){
				//redirect to game page
			}
			//request for an update on the players and the games
			String updatePar = request.getParameter("update");
			if(updatePar != null && updatePar.equals("true")){
				//a request for an update on 	the room page
				return getUpdateXML(user, game);							
			}else{
				return makePage(getRoomXML(user, game));	
			}
		}else{
			//the person was not logged in and thus redirected to the login page
			System.out.println("room do get: redirected to login");
			return pages.doGet("login",request,response);
		}
	}

	@Override
	public String doPost(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("Room: do post");		
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		String action = (String) request.getParameter("action");
		//is the user logged in?
		if(user != null){
			if(action == null)return doGet(request,response);
			//and he must not have created a game already
			if(action.equals("newGame") ){
				if(game == null){
					game = facade.addGame();
					//add the user to the game
					game.addPlayer(user);
					request.getSession().setAttribute("game", game);
					user.setGameOwner(true);
				}
			}else if(action.equals("join")){
				if(game == null){
					String gameId = (String)request.getParameter("gameId");
					try{
						UUID id = UUID.fromString(gameId);
						game = facade.getGame(id);
						//it could be that the game does not exist anymore
						if(game != null){
							//add the user to the game
							//returns true is the player was added successfully 
							if(game.addPlayer(user)){
								request.getSession().setAttribute("game", game);
							};
						}
					}catch(Exception e){

					}
				}	
				
			}else if(action.equals("leave")){
				if(game != null){
					//tries to remove the player from the game, returns true is it succeeded
					if(game.removePlayer(user)){			
						request.getSession().setAttribute("game", null);
						//tries to remove the game, only succeeds if there are no users anymore
						facade.removeGame(game.getId());
						//do get of room page
					}else{
						if(game.started()){
							//the game was started => redirect to game page
						}//else do get from room page
					}
				}//else do get from room page
			}else if(action.equals("addAI")){
				if(game != null && user.isGameOwner()){
					game.addPlayer(facade.getAIUser());
				}
				
			}if(action.equals("removeAI")){
				if(game != null && user.isGameOwner()){
					game.removeAIUser();
				}				
			}else if(action.equals("startGame")){
				
			}
			
			return doGet(request,response);	
		}else{
			return pages.doGet("login",request,response);
		}
	}	
	
	private String getUpdateXML(User user, Game game){
		String XML = "<update>";
		XML += getCurrGameXML(user,game);
		XML += facade.getUsersXml();
		XML += facade.getGamesXml();
		XML += "</update>";	
		return XML;
	}
	
	private String getRoomXML(User user, Game game){
		String XML = "<room_get>";
		XML += getCurrGameXML(user,game);
		XML += facade.getUsersXml();
		XML += facade.getGamesXml();
		XML += "</room_get>";	
		return XML;
	}

	private String getCurrGameXML(User user, Game game){
		String XML ="";
		if(game!=null){
			XML += "<curr-game>" +
						"<game-owner>"+ user.isGameOwner() +"</game-owner>"	+
						game.getGameXML() +
					"</curr-game>";
		}
		return XML;
	}
	

}
