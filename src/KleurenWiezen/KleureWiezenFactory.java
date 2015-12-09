package KleurenWiezen;

public class KleureWiezenFactory {

	private static KleureWiezenFactory pf = new KleureWiezenFactory();
	
	public static KleureWiezenFactory getInstance(){
		return pf;
	}
	
	private KleureWiezenFactory(){};
	
	public KHumanPlayer getHumanPlayer(){
		return new KHumanPlayer();
	}
	
	public AIPlayer getAIPlayer(){
		return new AIPlayer();
	}

	public KleurewiezenGame getGame(){
		return new KleurewiezenGame();
	}
}
