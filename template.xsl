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
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
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
  	<form method="Post" id="new_game">
  		<input type="hidden" name="Page" value="room"></input>
  	</form>
  	<button type="submit" form="new_game" value="Submit">Create Game</button>
  	
  	<form method="Post" id="log_out">
  		<input type="hidden" name="Page" value="login"></input>
  		<input type="hidden" name="action" value="logout"></input>
  	</form>
  	<button type="submit" form="log_out" value="Submit">log out</button>
  	
  	<xsl:apply-templates select="users" />
  	<xsl:apply-templates select="games" />
  
  
  <!-- Content:template java script file that updates the players-->
  <script src="update.js"></script>
</xsl:template>

<xsl:template match="users">
	<ul id="users">
		<xsl:for-each select="user">
			<li><xsl:value-of select="name"/></li>  	
  		</xsl:for-each>
    </ul>
</xsl:template>

<xsl:template match="games">
	<ul id="games">
		<xsl:for-each select="game">
			<li><xsl:value-of select="name"/></li>  	
  		</xsl:for-each>
    </ul>
</xsl:template>


</xsl:stylesheet>