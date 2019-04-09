
public class DoubleValue implements Number {
	private double value;
	
	public DoubleValue(int value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "DoubleValue(" + value + ")" ;
	}
}
