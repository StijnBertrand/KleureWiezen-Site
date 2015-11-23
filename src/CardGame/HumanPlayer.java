package CardGame;

public class HumanPlayer extends Player{
	
	protected final Object lock = new Object();
	protected boolean myTurn = false;
	protected int cardToPlay = -1;
	
	public boolean playCard(int index){
		synchronized(lock){
			if(myTurn & canPlay(index)){
				cardToPlay = index;
				myTurn = false;
				lock.notify();
				return true;
			}else{
				return false;
			}
		}		
	}
	
	@Override
	public Card letPlay() {
		try {
			synchronized(lock){
				myTurn = true;
				lock.wait();
			}		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return hand.play(cardToPlay);
	}
	
	public synchronized boolean getMyTurn(){
		return myTurn;
	}
	
	protected boolean canPlay(int card){
		return true;
	}
}
