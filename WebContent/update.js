function startUpdating(){
    var request = new XMLHttpRequest()
    request.onreadystatechange = function(){
        if(this.readyState == 4){
         if(this.status == 200){  
        	 var dom = (new DOMParser()).parseFromString(this.responseText,"text/xml");
        	 var users = document.getElementById("users");
        	 var games = document.getElementById("games"); 
        	 var currGame = document.getElementById("curr-game");
        	 
        	 
        	 users.innerHTML = dom.getElementById("users").innerHTML;
        	 games.innerHTML = dom.getElementById("games").innerHTML; 
        	 currGame.innerHTML = dom.getElementById("curr-game").innerHTML;
        	 

         }else{console.log("state = " + this.status)}
        }
    }
    request.open("GET","Site?Page=room&update=true",true);
    request.send();
    setTimeout(startUpdating, 5000);
}

(function(){
	document.getElementById("games-div").addEventListener('click',function(e){
		if(e.target && e.target.nodeName == "BUTTON"){		
			var form = document.getElementById("join");	
			form.querySelector('[id=form-game-id]').value = e.target.parentNode.querySelector('[name=game-id]').value;	
			form.submit();
		}	 
	})
})();

startUpdating();