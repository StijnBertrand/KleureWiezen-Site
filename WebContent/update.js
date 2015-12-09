document.addEventListener("DOMContentLoaded", function(event) { 
	initJoinButtons();
	
	drawHand();
	drawCurrSlag()
	//startUpdating();
});

	


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
    request.open("GET","Site?action=update",true);
    request.send();
    setTimeout(startUpdating, 5000);
};

//adds an event listener to the join button (needed for the game id)
function initJoinButtons(){
	document.getElementById("games-div").addEventListener('click',function(e){
		if(e.target && e.target.nodeName == "BUTTON"){		
			var form = document.getElementById("join");	
			form.querySelector('[id=form-game-id]').value = e.target.parentNode.querySelector('[name=game-id]').value;	
			form.submit();
		}	 
	})
}



var space = 20;
var height = 96;
var width = 72;

var numCards = 6;
var hand = new Array(13);
hand[0]= 1;
hand[1]= 2;
hand[2]= 3;
hand[3]= 4;
hand[4]= 5;
hand[5]= 6;
hand[6]= 7;
hand[7]= 8;
hand[8]= 9;
hand[9]= 10;
hand[10]= 11;
hand[11]= 12;
hand[12]= 13;


//load the cards
var cards = new Array(52);
for(var i = 0; i< 52 ;i++){
	cards[i] = document.createElement("img");
	cards[i].src = 'img/card'+ i + '.png';
	cards[i].id = i;
}

function drawHand(){
	var container = document.getElementById("gameContainer");
	var cHeight = container.offsetHeight;
	var cWidth = container.offsetWidth;

	var div = document.getElementById("south-player");
	var handWidth = ((numCards-1)*space+width )
	//height is the same as the card height
	
	
	div.setAttribute("style","position: absolute;"+
							 "top: 500px; " +
							 "left: "+ (cWidth-handWidth)/2 +"px;" +
							 "width:"+ handWidth +"px; " + 
					 		 "height:"+ height +"px"                   );

	
	var j =0;
	for(var i=0; i<13;i++){
		if(hand[i] != null){
			cards[hand[i]].setAttribute("style","position: absolute;"+
					 "top: 0px; left: "+ (space*j) +"px; z-index: "+ i +";");
			div.appendChild(cards[hand[i]]);
			j++;
		}
	}
	
}


var first = 3;
var playerSeat=1;
var slag = new Array(4);
var slagWidth = width*2 + width/3;
var slagHeight = height*2 + height/3;


function drawCurrSlag(){
	var container = document.getElementById("gameContainer");
	var cSlag = document.createElement("div");
	cSlag.setAttribute("style","position: absolute;"+
			 "width:"+ slagWidth +"px; " + 
	 		 "height:"+ slagHeight +"px"                   );
	var x,y;
	for(var i =0;i<4;i++){
		if(slag[(first+i)%4]==null)break;		
		switch((4+first+i-playerSeat)%4){
		//the card to be drawn is this players card	
		case(0):{
			console.log("hier0");
			x=2*width/3;
			y=height + height/3;
			break;
		}
		//the card to be drawn is the player to the west his card
		case(1):{
			console.log("hier1");

			x=0;
			y= 2*height/3;
			break;
		}
		//the card to be drawn is the player to the north his card
		case(2):{
			console.log("hier2");

			x=2*width/3;
			y= 0;
			break;
		}
		//the card to be drawn is the player to the east his card
		case(3):{
			console.log("hier3");

			x=width + width/3;
			y= 2*height/3;
			break;
		}
		}
		cards[slag[(first+i)%4]].setAttribute("style","position: absolute;"+
				 "top: " + y + "px; left: "+ x +"px; z-index: "+ i +";");
		cSlag.appendChild(cards[slag[(first+i)%4]]);	
	}
	container.appendChild(cSlag);
}

