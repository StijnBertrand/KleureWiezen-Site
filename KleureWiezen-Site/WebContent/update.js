function startUpdating(){
    var request = new XMLHttpRequest()
    request.onreadystatechange = function(){
        if(this.readyState == 4){
         if(this.status == 200){      
        	 var dom = (new DOMParser()).parseFromString(this.responseText,"text/xml");
        	 var users = document.getElementById("users");
        	 var games = document.getElementById("games");
        	 
        	 users.innerHTML = dom.getElementById("users").innerHTML;
        	 games.innerHTML = dom.getElementById("games").innerHTML;

         }else{console.log("state = " + this.status)}
        }
    }
    request.open("GET","Site?Page=room&update=true",true);
    request.send();
    setTimeout(startUpdating, 5000);
}

startUpdating();