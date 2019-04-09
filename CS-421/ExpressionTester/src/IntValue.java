
public class IntValue implements Number {
	private int value;
	
	public IntValue(int value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "IntValue(" + (int)value + ")" ;
	}

	@Override
	public Number getNumber() {
		return new IntValue(value);
	}
	
}
