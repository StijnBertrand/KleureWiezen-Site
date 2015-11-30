<?xml version="1.0" encoding="ISO-8859-1" ?>


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html"
            doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
            doctype-system="http://www.w3.org/TR/html401/loose.dtd" />


<xsl:template match="Page">
  <html>
    <head>
      	<link type="text/css" href="style.css" rel="stylesheet" />
      	<meta name="viewport" content="width=device-width, initial-scale=1"/>
 		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
      
      </head>
    
    <body>
    	<ul class="nav nav-tabs">
		  <li id="hometab" class="active"><a data-toggle="tab" href="#home">Home</a></li>
		  <li><a data-toggle="tab" href="#rules">Game rules</a></li>
		  
		  <!-- the log in tab can only be seen when not logged in-->
		  <xsl:if test="not(/Page/loggedIn)">
		  	<li><a data-toggle="tab" href="#login">Log in</a></li>
		  </xsl:if>
		  
		  <!-- room and game tab can only be seen when logged in-->
		  <xsl:if test="/Page/loggedIn">
		  	<li id="roomtab"><a data-toggle="tab" href="#room">room</a></li>
		  	<li id="gametab"><a data-toggle="tab" href="#game">Game</a></li>
		  </xsl:if>
		  
		</ul>
		
		<div class="container">
			<h2>Dynamic Tabs</h2>
			<div class="tab-content">	
				<xsl:call-template name="home" />
				<xsl:call-template name="rules" />
				
				<xsl:if test="not(/Page/loggedIn)">
					<xsl:call-template name="login" />  
				</xsl:if>
				
				<xsl:if test="/Page/loggedIn">
					<xsl:apply-templates select="room" />
				  	<xsl:call-template name="game" />
				</xsl:if>
				
			</div>	
		</div>
				
		<xsl:if test="/Page/roomActive">
			<script>
				document.getElementById("hometab").className = "";
				document.getElementById("home").className = "tab-pane fade";
				<!-- document.getElementById("gametab").className = "";-->
				<!-- document.getElementById("game").className = "tab-pane fade";  -->
				document.getElementById("roomtab").className = "active";
				document.getElementById("room").className = "tab-pane fade in active";
			</script>
		</xsl:if>
		
		<xsl:if test="/Page/gameActive">
			<script>
				document.getElementById("hometab").className = "";
				document.getElementById("home").className = "tab-pane fade";
				<!--document.getElementById("gametab").className = "active";-->
				<!--document.getElementById("game").className = "tab-pane fade in active";-->
				document.getElementById("roomtab").className = "";
				document.getElementById("room").className = "tab-pane fade";
			</script>
		</xsl:if>

    </body>
  </html>
</xsl:template>

<xsl:template name="home">
	<div id="home" class="tab-pane fade in active">
	    <h3>HOME</h3>
	    <p>no content Yet.</p>
	  </div>
</xsl:template>

<xsl:template name="rules">
	<div id="rules" class="tab-pane fade">
	    <h3>Rules</h3>
	    <p>no content Yet.</p>
	  </div>
</xsl:template>

<!-- Template for the login tab-->
<xsl:template name="login">
	<div id="login" class="tab-pane fade">
	  	<form method="Post" id="loginForm">
	  		<input type="text" name="name"></input>
	  		<input type="hidden" name="action" value="login"></input>
	  	</form>
	  	<button type="submit" form="loginForm" value="Submit">Log in</button> 
	 </div> 	
</xsl:template>

<!-- Template for the room tab-->
<xsl:template match="room">
	<div id="room" class="tab-pane fade">
		<!-- form to remove a computer -->
		<form method="Post" id="removeAI">
			<input type="hidden" name="action" value="removeAI"></input>
		</form>
		
		<!-- form to add a computer -->
		<form method="Post" id="addAI">
			<input type="hidden" name="action" value="addAI"></input>
		</form>
		
		<!-- form to leave a game -->
		<form method="Post" id="leave">
			<input type="hidden" name="action" value="leave"></input>
		</form>
		
		<!-- form to join a game -->
		<form method="Post" id="join">
			<input type="hidden" name="action" value="join"></input>
			<input type="hidden" id="form-game-id" name="gameId" value=""></input>			
		</form>
		
		<!-- form to create a new game -->
		<form method="Post" id="new_game">
	  		<input type="hidden" name="action" value="newGame"></input>
	  	</form>
		
	  	<!-- form to log out -->
	  	<form method="Post" id="log_out">
	  		<input type="hidden" name="Page" value="login"></input>
	  		<input type="hidden" name="action" value="logout"></input>
	  	</form>	
	  	<button type="submit" form="log_out" value="Submit">log out</button>
	  	
		
		
		<xsl:if test="not(/Page/room/curr-game)">
			<div style="border:1px solid black;">
		  		<button type="submit" form="new_game" value="Submit">Create Game</button>	
			</div>
		</xsl:if>
		
		<xsl:if test="/Page/room/curr-game">
			<div id="curr-game" style="border:1px solid black;">
				<xsl:apply-templates select="curr-game" />  
			</div>
		</xsl:if>
			
		
	  	<xsl:apply-templates select="users" />
	  	<xsl:apply-templates select="games" />
	  
	  
	  <!-- Content:template java script file that updates the players-->
	  <script src="update.js"></script>
	</div>
</xsl:template>

<xsl:template match="curr-game"> 
		<xsl:value-of select="game/game-name"/>
		<ul id="players">
			<xsl:for-each select="game/players/player">
					<li><xsl:value-of select="player-name"/></li>  	
			</xsl:for-each>
		</ul>
		<button type="submit" form="leave" value="Submit">leave game</button>
		<xsl:if test="game-owner= 'true'">
			<button type="submit" form="addAI" value="Submit">add Computer</button>
			<button type="submit" form="removeAI" value="Submit">remove Computer</button>
		</xsl:if>
</xsl:template>

<xsl:template match="users">
	<div style="border:1px solid black;">
		<ul id="users">
			<xsl:for-each select="user">
				<li><xsl:value-of select="name"/></li>  	
	  		</xsl:for-each>
	    </ul>
	</div>	    
</xsl:template>

<xsl:template match="games">
	<div id="games-div" style="border:1px solid black;">
		<ul id="games" >
			<xsl:for-each select="game">
				<li>
					<xsl:value-of select="game-name"/>
					<ul id="players">
						<xsl:for-each select="players/player">
							<li><xsl:value-of select="player-name"/></li>  	
	  					</xsl:for-each>
					</ul>
					
					<input type="hidden" value="{game-id}" name="game-id"><xsl:text> </xsl:text></input>
  					<button>Join</button>
				</li>  	
	  		</xsl:for-each>
	    </ul>
    </div>
</xsl:template>

<xsl:template name="game">
	<div id="game" class="tab-pane fade">
	    <h3>HOME</h3>
	    <div class="bgimg">
	    </div>
	</div>
</xsl:template>


<xsl:template match="update">
	<update>	
		<div id="curr-game" style="border:1px solid black;">
		<xsl:apply-templates select="curr-game" />  
		</div> 	  	
  		<xsl:apply-templates select="users" /> 
  		<xsl:apply-templates select="games" />
	</update>
</xsl:template>


</xsl:stylesheet>