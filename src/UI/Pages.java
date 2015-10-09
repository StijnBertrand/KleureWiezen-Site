package UI;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BusinessLayer.AppFacade;

public class Pages {
	private Map<String, Page> 		pages		= new Hashtable<String, Page>();
	String defaultPage;
	
	public Pages(String defaultPage){
		this.defaultPage = defaultPage;
		AppFacade facade = new AppFacade();
		
		pages.put("room", new Room(facade, this));
		pages.put("login", new LogIn(facade , this));
	}
	
	public String doGet(String page, HttpServletRequest	request,HttpServletResponse	response){
		Page p = getPage(page);
		return p.doGet(request, response);
	}
	
	public String doPost(String page, HttpServletRequest	request,HttpServletResponse	response){
		Page p = getPage(page);
		return p.doPost(request, response);
	}
	
	private Page getPage(String page){
		if (page != null && pages.containsKey(page)){
			System.out.println("page found: " + page);
			return (Page) pages.get(page);
		}else if(defaultPage != null && pages.containsKey(defaultPage)) {
			System.out.println("page not found: " + page);
			return (Page) pages.get(defaultPage);
		}else{
			return null;
		}
	}

}
