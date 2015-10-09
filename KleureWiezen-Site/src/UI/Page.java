package UI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BusinessLayer.AppFacade;


public abstract class Page {
	protected AppFacade facade;
	protected Pages pages;
	
	public Page(AppFacade facade , Pages pages){
		this.facade = facade;
		this.pages = pages;
	}
	
	public abstract String doGet(HttpServletRequest	request,HttpServletResponse	response);
	public abstract String doPost(HttpServletRequest request,HttpServletResponse response);

	protected String makePage(String input) {
		String output = new String();
		output += "<Page>";
		output += input;
		output += "</Page>";
		return output;
	}
}
