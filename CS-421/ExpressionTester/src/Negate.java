
public class Negate implements UnOp {

	@Override
	public Number apply(Number value) {
		if(value instanceof IntValue) {
			return new IntValue((int)value.getValue() * -1);
		} else {
			return new DoubleValue(value.getValue() * -1);
		}
	}

}
