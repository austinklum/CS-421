
public class Bool {
	private boolean bool;
	
	public Bool(boolean bool) {
		this.bool = bool;
	}
	
	public boolean getValue() {
		return bool;
	}
	
	@Override
	public String toString() {
		return "Bool(" + bool + ")";
	}
	
}
