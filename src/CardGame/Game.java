package CardGame;

public abstract class Game implements Runnable{
	public abstract boolean addPlayer( Player player ,int seat);
	public abstract boolean removePlayer(int seat);
}
