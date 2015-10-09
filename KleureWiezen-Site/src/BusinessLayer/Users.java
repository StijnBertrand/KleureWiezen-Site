package BusinessLayer;

import java.util.ArrayList;

public class Users {
	ArrayList<String> users;
	Cons<Integer> list;
	
	public Users(){
		users = new	ArrayList<>();
		list = new Cons<Integer>(0,null);
		
	}
	
	public synchronized Integer AddPlayer(String name){
		int p = (int)list.car();
		
		if(list.cdr() == null){			
			list.setCar(p+1);
			users.add(name);
		}else{			
			list = list.cdr();
			users.set(p,name);
		}
		return p;
	}
	
	public synchronized void removePlayer(int loginid) {		
		list = new Cons<Integer>(loginid,list);
		users.set(loginid,null);
	}
	
	public String getUsersXml(){
		String XML = "<users>";
		for(String player: users){
			if(player != null){
				XML = XML + "<user><name>"+ player +"</name></user>";
			}
		}
		XML += "</users>";
		return XML;
	}

}
