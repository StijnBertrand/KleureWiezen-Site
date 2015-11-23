package BusinessLayer;

public class AIUser extends User{
	private static int num;
	public AIUser() {
		super("computer#"+ String.valueOf(num));
		num++;
		this.AIUser = true;
	}
}
