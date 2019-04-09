
public class BinExp implements NumExp {
	Number value;
	
	public BinExp(BinOp op, Number value1, Number value2) {
		value = op.apply(value1, value2);
	}
	
	@Override
	public Number getNumber() {
		return value;
	}

}
