
public class UnExp implements NumExp {
	private Number value;
	
	public UnExp(UnOp op, NumExp exp) {
		value = op.apply(exp.getNumber());
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
