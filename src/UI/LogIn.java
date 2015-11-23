package UI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BusinessLayer.AppFacade;
import BusinessLayer.Game;
import BusinessLayer.User;

public class LogIn extends Page{

	public LogIn(AppFacade facade,Pages pages) {
		super(facade,pages);
	}

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		//is the user logged in already?
		if((User)request.getSession().getAttribute("user") == null){
			return makePage("<login></login>");
		}else{
			return pages.doGet("room", request, response);
		}
	}

	@Override
	public String doPost(HttpServletRequest request,HttpServletResponse response) {
		String action = (String) request.getParameter("action");
		User user = (User)request.getSession().getAttribute("user");
		//is the user not logged in yet? and does he wants to log in?
		if( user == null && action.equals("login")){
			String name = request.getParameter("name");
			if( name != null && !name.equals("") ){
				//logging in 
				user = facade.AddUser(name);
				request.getSession().setAttribute("user", user);
			}else{
				//invalid name
				return makePage("<login></login>");
			}
			//does the user want to log out?
		}else if(user != null && action.equals("logout")){
			//logging out
			Game game = (Game)request.getSession().getAttribute("game");
			//remove the player from the game he is joined in
			if(game != null){
				//to be completed: part for when the game has started already
				game.removePlayer(user);
				facade.removeGame(game.getId());
				request.getSession().removeAttribute("game");
			}
			facade.removeUser((User)request.getSession().getAttribute("user"));
			request.getSession().removeAttribute("user");
			return makePage("<login></login>");
		}
		return pages.doGet("room", request, response);
	}
}
