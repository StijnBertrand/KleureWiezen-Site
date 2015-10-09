package KleurenWiezen;

import CardGame.Player;

public class KleureWiezenFactory {

	private static KleureWiezenFactory pf = new KleureWiezenFactory();
	
	public static KleureWiezenFactory getInstance(){
		return pf;
	}
	
	private KleureWiezenFactory(){};
	
	public KHumanPlayer getKHumanPlayer(){
		return new KHumanPlayer();
	}
	
	public AIPlayer getAIPlayer(){
		return new AIPlayer();
	}

	public Game getGame(){
		return new Game();
	}
}
