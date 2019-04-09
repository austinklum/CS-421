
public class ExpresssionTester {

	public static void main(String[] args) {
		IntValue anInt = new IntValue(3);
		DoubleValue aFloat = new DoubleValue(0.5);
		
	}
	
	public static String evaluate(Expression exp) {
		if(exp instanceof IntValue) {
			return ((IntValue) exp).toString();
		} else if (exp instanceof DoubleValue) {
			return ((DoubleValue) exp).toString();
		} else {
			return ((CompExp) exp).toString();
		}
	}
	
}
