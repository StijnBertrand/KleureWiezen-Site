package UI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BusinessLayer.AppFacade;

public class Room extends Page{
	
	public Room(AppFacade facade,Pages pages){
		super( facade,pages);
	}
	
	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("update =" + request.getParameter("update"));
		Integer loginid = (Integer) request.getSession().getAttribute("login_id");
		if(loginid != null){
			//request for an update on the players and the games
			if(request.getParameter("update")==null){
				return makePage("<room_get>"+ 
									facade.getUsersXml() + 
									facade.getGamesXml() +
								"</room_get>");
				
			}else{
				//a request for the room page
				return "<update>" + facade.getUsersXml() + facade.getGamesXml() + "</update>";
			}
		}else{
			//the person was not logged in and thus redirected to the login page
			System.out.println("redirected to login");
			return pages.doGet("login",request,response);
		}
	}

	@Override
	public String doPost(HttpServletRequest request,HttpServletResponse response) {
		Integer loginid = (Integer) request.getSession().getAttribute("login_id");
		String game = (String) request.getSession().getAttribute("game");
		//to create a new game a person has to be logged in 
		if(loginid != null){
			//and he must not have created a game already
			if(game == null){
				request.getSession().setAttribute("game", "kleurewiezen");
				facade.addGame();
				return doGet(request,response);
			}else{
				return doGet(request,response);
			}
		}else{
			return pages.doGet("login",request,response);
		}
	}
	
	

}
