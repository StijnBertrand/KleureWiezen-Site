<?xml version="1.0" encoding="ISO-8859-1" ?>


<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html"
            doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
            doctype-system="http://www.w3.org/TR/html401/loose.dtd" />


<xsl:template match="Page">
  <html>
    <head>
      <link type="text/css" href="style.css" rel="stylesheet" />
      
      </head>
    <body>
      		
            <xsl:apply-templates select="room_get" />
            <xsl:apply-templates select="login" />

    </body>
  </html>
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

<!-- Template for the login page-->
<xsl:template match="login">
  	<form method="Post" id="login">
  		<input type="text" name="name"></input>
  		<input type="hidden" name="Page" value="login"></input>
  		<input type="hidden" name="action" value="login"></input>
  	</form>
  	<button type="submit" form="login" value="Submit">Log in</button>  	
</xsl:template>


<!-- Template for the room page-->
<xsl:template match="room_get">
	<!-- form to remove a computer -->
	<form method="Post" id="removeAI">
		<input type="hidden" name="Page" value="room"></input>
		<input type="hidden" name="action" value="removeAI"></input>
	</form>
	
	<!-- form to add a computer -->
	<form method="Post" id="addAI">
		<input type="hidden" name="Page" value="room"></input>
		<input type="hidden" name="action" value="addAI"></input>
	</form>
	
	<!-- form to leave a game -->
	<form method="Post" id="leave">
		<input type="hidden" name="Page" value="room"></input>
		<input type="hidden" name="action" value="leave"></input>
	</form>
	
	<!-- form to join a game -->
	<form method="Post" id="join">
		<input type="hidden" name="Page" value="room"></input>
		<input type="hidden" name="action" value="join"></input>
		<input type="hidden" id="form-game-id" name="gameId" value=""></input>			
	</form>
	
	<!-- form to create a new game -->
	<form method="Post" id="new_game">
  		<input type="hidden" name="Page" value="room"></input>
  		<input type="hidden" name="action" value="newGame"></input>
  	</form>
	
  	<!-- form to log out -->
  	<form method="Post" id="log_out">
  		<input type="hidden" name="Page" value="login"></input>
  		<input type="hidden" name="action" value="logout"></input>
  	</form>	
  	<button type="submit" form="log_out" value="Submit">log out</button>
  	
	<div style="border:1px solid black;">
  		<button type="submit" form="new_game" value="Submit">Create Game</button>	
	</div>
		
	<div id="curr-game" style="border:1px solid black;">
		<xsl:apply-templates select="curr-game" />  
	</div>
  	<xsl:apply-templates select="users" />
  	<xsl:apply-templates select="games" />
  
  
  <!-- Content:template java script file that updates the players-->
  <script src="update.js"></script>
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


</xsl:stylesheet>