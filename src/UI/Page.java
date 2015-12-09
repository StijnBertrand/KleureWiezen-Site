package UI;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BusinessLayer.AppFacade;
import BusinessLayer.Game;
import BusinessLayer.User;
import CardGame.Player;


public class Page {
	protected AppFacade facade;
	private String activTab = "roomActive";	
	
	public Page(AppFacade facade){
		this.facade = facade;
	}
	
	public String doGet(HttpServletRequest	request,HttpServletResponse	response){
		User user = (User)request.getSession().getAttribute("user");
		String action = (String) request.getParameter("action");
		Game game = (Game)request.getSession().getAttribute("game");
		if(user == null){
			//not logged in page	
			return makePage("");
		}else if(action != null && action.equals("update")){
			return getUpdateXML(user, game);
		}else{ 
			//logged in page
			activTab = "gameActive";
			return makePage(
					 // this makes it such that the logged in version will be returned as a response
					 "<loggedIn></loggedIn>"
					 //used to see what tab should be active
					+"<"+activTab+"></"+activTab+">"
					 // this xml will be transformed to the layout of the room tab
					+getRoomXML(user, game)
					 // this xml will be transformed into the layout for the game tab
					//+getGameXML(user, game)
					+"<game>"+"<bgimg>"+ "/context/gameBackground.png" +"</bgimg>"+"</game>"
					);
		}
	}

	public String doPost(HttpServletRequest request,HttpServletResponse response){
		String action = (String) request.getParameter("action");
		
		if(action == null)return doGet(request,response);
		if(action.equals("login")){	
			return logIn(request, response);
		}else if(action.equals("logout")){
			return logOut(request, response);
		}if(action.equals("newGame")){
			return newGame(request, response);
		}if(action.equals("join")){
			return join(request, response);
		}if(action.equals("leave")){
			return leave(request, response);
		}if(action.equals("addAI")){
			return addAI(request, response);
		}if(action.equals("removeAI")){
			return removeAI(request, response);
		}
		
		
		return null;
	}
	
	private String removeAI(HttpServletRequest request,HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		if(game != null && user.isGameOwner()){
			game.removeAIUser();
		}
		activTab = "roomActive";
		return doGet(request, response);
	}

	private String addAI(HttpServletRequest request,HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		if(game != null && user.isGameOwner()){
			game.addPlayer(facade.getAIUser());
		}
		activTab = "roomActive";
		return doGet(request, response);
	}

	private String leave(HttpServletRequest request,HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		if(user != null && game != null){
			//tries to remove the player from the game, returns true is it succeeded
			if(game.removePlayer(user)){			
				request.getSession().setAttribute("game", null);
				//tries to remove the game, only succeeds if there are no users anymore
				facade.removeGame(game.getId());
				//active tab should be the room tab ( the game has not started yet
				activTab = "roomActive";
			}else{
				if(game.started()){
					//the game was started => active tab should be the game tab
					activTab = "gameActive";
				}
			}
		}
		return doGet(request, response);
	}

	private String join(HttpServletRequest request, HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		//you can only join a game if you are logged in and if you have not joined a game already
		if(user != null && game == null){
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
				//don't know what to do here
			}
		}
		activTab = "roomActive";
		return doGet(request, response);
	}

	private String newGame(HttpServletRequest request,HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		if(user != null){
			if(game == null){
				game = facade.addGame();
				//add the user to the game
				game.addPlayer(user);
				request.getSession().setAttribute("game", game);
				user.setGameOwner(true);
			}
		}
		activTab = "roomActive";
		return doGet(request, response);
	}

	private String logOut(HttpServletRequest request, HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		Game game = (Game)request.getSession().getAttribute("game");
		if(user != null){
			//remove the player from the game he is joined in
			if(game != null){
				//to be completed: part for when the game has started already
				game.removePlayer(user);
				//the game can only be removed when there are no human players left
				facade.removeGame(game.getId());
				request.getSession().removeAttribute("game");
			}
			facade.removeUser(user);
			request.getSession().removeAttribute("user");
		}
		return doGet(request, response);
	}

	
	private String logIn(HttpServletRequest request, HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		//is the user not logged in yet?
		if( user == null){
			String name = request.getParameter("name");
			if( name != null && !name.equals("") ){
				//logging in 
				user = facade.AddUser(name);
				request.getSession().setAttribute("user", user);
			}
		}
		activTab = "roomActive";
		//Simply do a get when already logged in, invalid name or logging in was successful
		return doGet(request, response);
	}

	private String makePage(String input) {
		String output = "<Page>";
		output += input;
		output += "</Page>";
		return output;
	}
	
	private String getRoomXML(User user, Game game){
		String XML = "<room>";
		XML += getCurrGameXML(user,game);
		XML += facade.getUsersXml();
		XML += facade.getGamesXml();
		XML += "</room>";
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
	
	private String getUpdateXML(User user, Game game){
		String XML = "<update>";
		XML += getCurrGameXML(user,game);
		XML += facade.getUsersXml();
		XML += facade.getGamesXml();
		XML += "</update>";	
		return XML;
	}
	
	private String getGameXML(User user, Game game) {
		Player player = user.getPlayer();
		int seat = player.getSeat();
		String XML = "";
		if(game==null || !game.started())return XML;
		XML += "<Game>";
		//this player (player to the south)
		XML += "<south-player>";
		XML += "<#cards>" + player.getAmountOfCards() + "</#cards>";
			XML += "<hand>";
			for(int i = 0;i<player.getHand().size();i++){
				XML += "<card>"; 
					XML += player.getHand().getCard(i).getID();
					XML += "<card>";
			}
			XML += "</hand>";					
		XML += "</south-player>";
		//next player (player to the west)
		XML += "<west-player>";
			XML += "<#cards>" + player.getAmountOfCards(seat+1%4) + "</#cards>";		
		XML += "</west-player>";
		//next player (player to the north)
		XML += "<north-player>";
			XML += "<#cards>" + player.getAmountOfCards(seat+2%4) + "</#cards>";		
		XML += "</north-player>";
		//next player (player to the east)
		XML += "<east-player>";
			XML += "<#cards>" + player.getAmountOfCards(seat+3%4) + "</#cards>";		
		XML += "</east-player>";

		XML += "</Game>";
		return XML;
	}
}
