package KleurenWiezen;

import CardGame.*;



public class KleurewiezenGame extends CardGame.Game{

	private KTable table;
	private int bStarter = 0;
	private int sStarter = 0;
	private int troef = 3;
	int[] game;
	
	public KleurewiezenGame(){
		table = new KTable();
	}

	public boolean addPlayer( Player player ,int seat){
		return table.setPlayer(player, seat);
	}
	
	public boolean removePlayer(int seat){
		return table.removePlayer(seat);
	}
	
	public void run(){
		boolean playing = true;
		while(playing){
			//distribute cards
			table.distributeCards();
			//troel?
			//let the players bed
			bidding();
			//set game variable, troef,...
			game = table.getGame();
			setTroef();
			//let the players play
			playing();
			//give points
			//reset table
			table.reset();
			bStarter ++;
		}
	}
	private void playing(){
		int aantal = 13;
		//discard a card if it is small misery
		if(game[0]==7){
			for(int j = 0;j<4;j++){
				table.letDiscardCard((sStarter + j)%4);
			}
			aantal = 12;
		}
		//naked misery
		if(game[0]==10){
			for(int j = 0;j<4;j++){
				table.letPlayerPlay((sStarter + j)%4);
			}
			sStarter = getWinner(table.getCurrSlag());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			table.appointSlag(sStarter);
			table.showCards();
			aantal = 12;
		}
		for(int i = 0; i != aantal; i++ ){
			for(int j = 0;j<4;j++){
				table.letPlayerPlay((sStarter + j)%4);
			}
			sStarter = getWinner(table.getCurrSlag());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			table.appointSlag(sStarter);
		}
	}

	private void bidding(){
		table.startBidding(bStarter);
		for(int bidder = bStarter;!table.finishedBidding(); bidder = (bidder+1)%4){
			table.letNextPlayerBed();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		table.stopBidding();
	}
	
	private void setTroef(){
		troef = -1;
		sStarter = bStarter;
		switch(game[0]){
		//two or one player go alone
		case(6)://hearts
		case(5)://diamonds
		case(4)://clubs
		case(3):{//spades
			troef = -(game[0]-7);
			break;
		}
		case(8)://abundance
		case(11):{//solo slim
			if(game[2]!=-1){
				troef = game[2];
				sStarter = game[game.length-1]; 
				break;
			}
		}
		case(1):{//troel
			firstSlagTroef = true;
			sStarter = game[game.length-1]; 
			break;
		}
		//no troef with the other games
		}
	}
	
	private boolean firstSlagTroef = false;
	private int getWinner(Slag s){
		int winner = s.getStarter();
		Card highest = s.getCard(s.getStarter()); 
		if(firstSlagTroef){
			troef = highest.getColour();
			firstSlagTroef = false;
		}
		for(int i = 1; i<4;i++){
			int currPlayer = (s.getStarter() + i)%4;
			Card curr = s.getCard(currPlayer);
			if(curr.getColour() == highest.getColour()){
				if(highest.getNumber()==0)continue;
				if(curr.getNumber()>highest.getNumber() | curr.getNumber() == 0){
					highest = curr;
					winner = currPlayer;
				}
			}else if(curr.getColour() == troef){
				winner = currPlayer;
				highest = curr;
			}
		}	
		return winner;
	}

}
