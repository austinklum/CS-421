
public class BinExp implements NumExp {
	Number value;
	
	public BinExp(BinOp op, NumExp value1, NumExp value2) {
		value = op.apply(value1.getNumber(), value2.getNumber());
	}
	
	@Override
	public Number getNumber() {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
