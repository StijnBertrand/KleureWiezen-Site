package KleurenWiezen;

import java.util.ArrayList;

import CardGame.Card;
import CardGame.Hand;
import CardGame.HumanPlayer;

public class KHumanPlayer extends HumanPlayer implements KPlayer{
	private boolean first = true;
	private int bed = 0;
	private boolean myTurnToBed = false;
	private boolean myTurnToDiscard = false;
	
	//checks if the player can play the card in his hand at position index
	@Override
	protected boolean canPlay(int index){
		// hier moet nog iets komen voor als het troel is
		int starter = table.getCurrSlag().getStarter();
		if(-1 == starter)return true;
		int colour = table.getCurrSlag().getCard(starter).getColour();	
		if(hasCards(colour)){
			if( colour != hand.getCard(index).getColour()){
				return false;
			}
		}	
		return true;
	}
	
	//checks whether this player has troel
	private int troel(){
		int aces = 0;
		boolean[] b = new boolean[9];
		for(Card c :hand){
			if(c.getNumber() == 0)aces++;
			if(c.getColour() == 0){
				try{
					b[12-c.getColour()] = true;
				}catch(Exception e){
					continue;
				}
			}
		}	
		if(aces >= 3 ){
			if(aces == 4){
				for(int i = 0; i <b.length;i++){
					if(!b[i])return 55+i;
				}
			}else{
				return 54;
			}
		}
		return 1;
	}

	//lets this player place a bed
	@Override
	public int letBid() {
		try{
			if(((KTable)table).getRound() <= 1 & ((KTable)table).getBeds().get(0)[seat]==0){
				return troel();
			}
			if(((KTable)table).getGame()[0]==1 & !((KTable)table).isTroelFound()){
				int number = ((KTable)table).getGame()[1];
				if(number == 0){
					//fourth ace joins
					for(Card c: hand){
						if(c.getNumber()==0)return 64;
					}
				}else{
					for(Card c: hand){
						if(c.getColour()==0 & c.getNumber() == (13 - number))return 64;
					}			
					System.out.println("hier3");
				}
				return 0;
			}
			synchronized(lock){
				myTurnToBed = true;
				lock.wait();
			}
			return bed;
		}catch(Exception e){
			return bed;	
		}
	}
	
	public ArrayList<int[]> getBeds(){
		return ((KTable)table).getBeds();
	}
	
	public boolean[] getOptions(){
		return ((KTable)table).getOptions( seat);		
	}
	
	//this method makes a player place a bed
	public boolean bed(int bed){
		synchronized(lock){
			if(myTurnToBed & canBed(bed)){
				this.bed  = bed;
				myTurnToBed = false;
				lock.notify();
				return true;
			}
			else{
				return false;
			}
		}	
	}

	//checks if a player can place this bed
	private boolean canBed(int bed) {
		return getOptions()[bed];
	}

	public boolean getMyTurnToBed(){
		return myTurnToBed;
	}
	
	//returns if the bedding phase is still going on
	public boolean isBidding() {
		return ((KTable)table).getBidding();
	}

	@Override
	public boolean hasCard(int color, int number) {
		for(Card c:hand){
			if(color== c.getColour()&number == c.getNumber())return true;
		}		
		return false;
	}

	//allows a player to discard a card
	@Override
	public Card letDiscard() {
		try {
			synchronized(lock){
				myTurnToDiscard = true;
				lock.wait();
			}		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return hand.play(cardToPlay);
	}
	
	public boolean discardCard(int index){
		synchronized(lock){
			if(myTurnToDiscard & canPlay(index)){
				cardToPlay = index;
				myTurnToDiscard = false;
				lock.notify();
				return true;
			}else{
				return false;
			}
		}		
	}
	
	public Hand getHand(int player){
		return ((KTable) table).getHand(player); 
	}
	
	public boolean isMyTurnToDiscard(){
		return myTurnToDiscard;
	}
}
