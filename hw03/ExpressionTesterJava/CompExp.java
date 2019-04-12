
public class CompExp implements Expression {
	private Bool value;
	
	public CompExp(Comp op, NumExp exp1, NumExp exp2) {
		value = op.compare(exp1.getNumber(), exp2.getNumber());
	}
	
	public boolean getValue() {
		return value.getValue();
	}
	
	public Bool getBool() {
		return value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
