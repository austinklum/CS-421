
public class Subtract implements BinOp {

	@Override
	public Number apply(Number value1, Number value2) {
		if (value1 instanceof IntValue && value2 instanceof IntValue) { 
			return new IntValue((int) value1.getValue()-(int) value2.getValue());
		} else {
			return new DoubleValue(value1.getValue()-value2.getValue());
		}
	}
	
}
