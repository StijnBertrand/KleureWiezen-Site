package UI;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BusinessLayer.AppFacade;

public class LogIn extends Page{

	public LogIn(AppFacade facade,Pages pages) {
		super(facade,pages);
	}

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		if(!loggedIn(request)){
			return makePage("<login></login>");
		}else{
			return pages.doGet("room", request, response);
		}
	}

	@Override
	public String doPost(HttpServletRequest request,HttpServletResponse response) {
		String action = (String) request.getParameter("action");

		if(!loggedIn(request) && action.equals("login")){
			String name = request.getParameter("name");
			if(!name.equals("") && name != null){
				//logging in 
				Integer loginid = facade.AddUser(name);
				request.getSession().setAttribute("login_id", loginid);
			}else{
				//invalid name
				return makePage("<login></login>");
			}
		}else if(action.equals("logout")){
			//logging out
			facade.removeUser((Integer)request.getSession().getAttribute("login_id"));
			request.getSession().removeAttribute("login_id");
			return makePage("<login></login>");
		}
		return pages.doGet("room", request, response);
	}
	
	private boolean loggedIn(HttpServletRequest request){
		//loged in when loginid != null
		//and the player can be found in the database
		Integer loginid = (Integer) request.getSession().getAttribute("login_id");
		if(loginid == null ){
			return false;
		}else{
			return true;
		}
	}
}
