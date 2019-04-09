
public class ExpresssionTester {

	public static void main(String[] args) {
		IntValue aInt = new IntValue(3);
		DoubleValue aFloat = new DoubleValue(0.5);
		
		DoubleValue zeroFloat = new DoubleValue(0.0);
		IntValue zero = new IntValue(0);
		IntValue one = new IntValue(1);
		
		Negate neg = new Negate();
		Equal eq = new Equal();
		NotEqual ne = new NotEqual();
		
		Add add = new Add();
		Multiply times = new Multiply();
		Subtract minus = new Subtract();
		Divide divide = new Divide();
		
		UnExp exp1 = new UnExp(neg,new BinExp(add,aInt, aFloat));
		BinExp exp2 = new BinExp(times, aInt, aFloat);
		CompExp exp3 = new CompExp(eq,exp1,exp2);
		CompExp exp4 = new CompExp(eq,exp1,exp1);
		CompExp exp5 = new CompExp(ne,exp1,exp2);
		BinExp exp6 = new BinExp(divide,new BinExp(minus,one,one),one);
		CompExp exp7 = new CompExp(eq,new BinExp(divide,new BinExp(minus,one,one),one),zero);
		CompExp exp8 = new CompExp(eq,new BinExp(divide,new BinExp(minus,one,one),one),zeroFloat);
		
		System.out.println(evaluate(exp1));
		System.out.println(evaluate(exp2));
		System.out.println(evaluate(exp3));
		System.out.println(evaluate(exp4));
		System.out.println(evaluate(exp5));
		System.out.println(evaluate(exp6));
		System.out.println(evaluate(exp7));
		System.out.println(evaluate(exp8));
	}
	
	public static String evaluate(Expression exp) {
		return exp.toString();
	}
	
}
