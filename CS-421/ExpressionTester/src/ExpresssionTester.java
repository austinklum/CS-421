
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
		
//		UnExp exp1 = new UnExp(neg,new BinExp(add,aInt, aFloat));
//		BinExp exp2 = new BinExp(times, aInt, aFloat);
//		CompExp exp3 = new CompExp(eq,exp1,exp2);
//		CompExp exp4 = new CompExp(eq,exp1,exp1);
//		CompExp exp5 = new CompExp(ne,exp1,exp2);
//		BinExp exp6 = new BinExp(divide,new BinExp(minus,one,one),one);
		BinExp exp1 = new BinExp(times,new BinExp(add,new IntValue(4),new IntValue(3)),new BinExp(minus, new IntValue(2),new BinExp(divide,new IntValue(6),new IntValue(2))));
		BinExp exp2 = new BinExp(times,new DoubleValue(2.0),new BinExp(minus,new IntValue(3),new BinExp(divide,new IntValue(4),new DoubleValue(2.0))));
		BinExp exp3 = new BinExp(minus,new IntValue(9),new BinExp(minus,new IntValue(6),new BinExp(times,new DoubleValue(5.0),new UnExp(neg,new IntValue(4)))));
		BinExp exp4 = new BinExp(times,new UnExp(neg,new IntValue(7)),new BinExp(times, new IntValue(4), new BinExp(minus, new IntValue(3),new BinExp(times, new DoubleValue(4.0),new DoubleValue(2.0)))));
		CompExp exp5 = new CompExp(ne,new BinExp(divide,new IntValue(3),new IntValue(2)),new BinExp(divide,new IntValue(3),new DoubleValue(2.0)));
		CompExp exp6 = new CompExp(eq,new BinExp(divide,new DoubleValue(3.0),new IntValue(2)),new BinExp(divide,new IntValue(3),new DoubleValue(2.0)));
		CompExp exp7 = new CompExp(eq,new BinExp(divide,new BinExp(minus,one,one),one),zero);
		CompExp exp8 = new CompExp(eq,new BinExp(divide,new BinExp(minus,one,one),one),zeroFloat);
		
		System.out.println("* + 4 3 - 2 / 6 2 => " + evaluate(exp1));
		System.out.println("* 2.0 - 3 / 4 2.0 => " + evaluate(exp2));
		System.out.println("- 9 - 6 - * 5.0 4 => " + evaluate(exp3));
		System.out.println("- * 7 * 4 - 3 * 4.0 2.0 => " + evaluate(exp4));
		System.out.println("/ 3 2 != / 3 2.0 => " + evaluate(exp5));
		System.out.println("/ 3.0 2 == / 3 2.0 => " + evaluate(exp6));
		System.out.println("/ - 1 1 1 == 0 => " + evaluate(exp7));
		System.out.println("/ - 1 1 1 == 0.0 => " + evaluate(exp8));
	}
	
	public static String evaluate(Expression exp) {
		return exp.toString();
	}
	
}
