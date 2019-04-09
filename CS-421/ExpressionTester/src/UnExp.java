
public class UnExp implements NumExp {
	private Number value;
	
	public UnExp(UnOp op, Number exp) {
		value = op.apply(exp.getNumber());
	}
	
	@Override
	public Number getNumber() {
		return value;
	}
}
