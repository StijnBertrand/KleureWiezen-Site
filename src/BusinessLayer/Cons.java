package BusinessLayer;

public class Cons<T> {

	private T value;
	private Cons<T> next;
	public Cons(T value,Cons<T> next){
		this.value = value;
		this.next = next;
	}
	
	public T car(){
		return value;
	}
	
	public Cons<T> cdr(){
		return next;
	}

	public void setCar(T i) {
		value = i;
	}
}
