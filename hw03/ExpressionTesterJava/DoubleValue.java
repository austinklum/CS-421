
public class DoubleValue implements Number {
	private double value;
	
	public DoubleValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "DoubleValue(" + value + ")" ;
	}

	@Override
	public Number getNumber() {
		return new DoubleValue(value);
	}
}
