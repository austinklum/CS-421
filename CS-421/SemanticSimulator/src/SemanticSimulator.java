import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Semantic Simulator
 * Parses programs from EBNF defined and simulates the needed 
 * states and transitions a complier would need to take.
 * @author Austin Klum
 * 2019/04/20
 */

/* LHS
 * int :: create new var
 * var :: update value
 * 
 * RHS 
 * evaluate expr
 *   find op
 *   find calls
 *   find numbers
 * 
 */

public class SemanticSimulator {

	public static void main(String[] args) {
		String[] parts = Parser.getParts(Parser.readFile("hw04_prog1.txt"),"(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*");
		Parser.print(parts);
		SemanticSimulator sim = new SemanticSimulator(parts);
		sim.simulate();
	}
	
	String[] program;
	boolean globalsExist;
	String[] globals;
	HashMap<String,String> funcMap;
	HashMap<String, Integer> gamma;
	HashMap<Integer, Integer> mu;
	int stackPointer;
	
	public SemanticSimulator(String[] parts) {
		program = parts;
		globalsExist = !parts[0].equals("");
		globals = parts[0].trim().split("\\s*;\\s*");
		gamma = new HashMap<>();
		mu = new HashMap<>();
		funcMap = new HashMap<>();
		stackPointer = 0;
		for(String func : program) {
			funcMap.put(getFuncMapKey(func), func);
		}
	}

	public void simulate() {
//		printState("0");
//		if(globalsExist) {
//			for(String stmt : globals) {
//				processStmt(stmt);
//			}
//		}
//		printState("global");
		processFunc(program[1]);
	}

//	find main
	public int findMain () {
		for(int i = 0; i < program.length; i++) {
			String[] line = program[i].split("(int)(\\s)+(main)\\s*\\(\\)");
			if(line.length > 1) {
				return i;
			}
		}
		return -1;
	}
//	process func
	public int processFunc(String funcLine) {
		Function func = new Function(funcLine);
		
		//printState(func.methodName + "_in");
		
		//System.out.println(func);
		return evaluate("thing");
	}
	
	private void printState(String stateName) {
		System.out.println("sigma_" + stateName + ": \n");
		System.out.print("  gamma: {");
		int count = 0;
		for (String key : gamma.keySet()){
            System.out.print("<" + key + ", " + gamma.get(key) + ">");
            if (count < gamma.size()) {
            	System.out.print(", ");
            	count++;
            }
		} 
		System.out.println("}");
		System.out.print("mu   :");
		count = 0;
		for(Integer key : mu.keySet()) {
			System.out.print("<" + key + ", " + (mu.get(key) == -1 ? "undef" : mu.get(key).toString()) + ">");
            if (count < gamma.size()) {
            	System.out.print(", ");
            	count++;
            }
		}
		System.out.println("}");
		System.out.println("a = " + stackPointer);
	}
	
//	process line
	private void processStmt(String stmt) {
		String[] stmtParts = stmt.trim().split("\\s*=\\s*");
		if(stmtParts[0].length() > 4 && stmtParts[0].substring(0,4).equals("int ")){
			String varName = stmtParts[0].split("\\s*")[1];
			addVar(varName);
			if(stmtParts.length > 1) {
				updateVar(varName,evaluate(stmtParts[1]));
			}
			
		}
	}

	private void addVar(String var) {
		gamma.put(var, stackPointer);
		mu.put(stackPointer, -1);
		stackPointer++;
	}
	private void updateVar(String varName, int value) {
		mu.replace(gamma.get(varName), value);
	}
	
	private void deleteVar(String varName) {
		mu.remove(gamma.remove(varName));
		stackPointer--;
	}
	
	private int getVal(String varName) {
		return mu.get(gamma.get(varName));
	}

	private int getMeaning(String name) {
		if (name.matches("\\d+")) {
			return Integer.parseInt(name);
	    }else if (name.contains("(")) {
			return processFunc(funcMap.get(getFuncMapKey(name)));
		} else {
			return getVal(name);
		}
	}

	private String getFuncMapKey(String name) {
		return getFuncName(name) + getParamCount(name);
	}
	
	private int evaluate(String expr) {
		//String[] exprParts = parseTokens(expr);
		//Ops :  \s*(\+|-|\*|\/)*\s*
		//funcs: \s*[a-z]*\(.*\)\s*
		//vars: \s*[a-z]*\s*
		expr = "+2*z function(x ,y   )anothaOne(  q  ,  r  )(p) 53";
		String[] rhsParts = removeSpacesFromFuncs(expr).split("\\s+");
		
		
		 //Number
		//Variable Name
		//Binary Operation
		//Function Call
		return -1;
	}

	static Boolean isOp(String str) 
	{ 
	    // If the character is a digit  
	    // then it must be an operand 
		switch(str.charAt(0)) {
			case '+':
			case '-':
			case '*':
			case '/':
				return true;
			default: 
				return false;
		}
	} 
	   
	private int evaluatePrefix(String[] rhs) { 
	    Stack<Integer> Stack = new Stack<Integer>(); 
	   
	    for (int j = rhs.length - 1; j >= 0; j--) { 
	   
	        // Push operand to Stack 
	        // To convert exprsn[j] to digit subtract 
	        // '0' from exprsn[j]. 
	        if (!isOp(rhs[j]))  
	            Stack.push(getMeaning(rhs[j])); 
	           
	        else { 
	   
	            // Operator encountered 
	            // Pop two elements from Stack 
	            int o1 = Stack.peek(); 
	            Stack.pop(); 
	            int o2 = Stack.peek(); 
	            Stack.pop(); 
	   
	            // Use switch case to operate on o1  
	            // and o2 and perform o1 O o2. 
	            switch (rhs[j].charAt(0)) { 
	            case '+': 
	                Stack.push(o1 + o2); 
	                break; 
	            case '-': 
	                Stack.push(o1 - o2); 
	                break; 
	            case '*': 
	                Stack.push(o1 * o2); 
	                break; 
	            case '/': 
	                Stack.push(o1 / o2); 
	                break; 
	            } 
	        } 
	    } 
	   
	    return Stack.peek(); 
	} 
	
	private String removeSpacesFromFuncs(String expr) {
		int start = expr.indexOf('(', 0);
		int end = expr.indexOf(')', 0);
		while(end < expr.length() && end > 0) {
			String substr = expr.substring(start,++end);
			String substrNoSpace = substr.replaceAll("\\s+","");
			expr = expr.replace(substr, substrNoSpace);
			
			start = expr.indexOf('(', end);
			end = expr.indexOf(')', end);
		}
		return expr;
	}
	private	String getFuncName(String funcLine) {
			Pattern pattern = Pattern.compile("\\s+");
		    Matcher matcher = pattern.matcher(funcLine);
			int tmp = 0;
			if(matcher.find()) {
					tmp = matcher.start();
			}
			return funcLine.substring(tmp,funcLine.indexOf('('));
		}
	
	private int getParamCount(String funcLine) {
		return funcLine.substring(funcLine.indexOf('('),funcLine.indexOf(')')+1).split(",").length;
	}
//	add var
//	update var
//	delete var
//	display gamma
//	display mu
//	display sigma
	private class Function {
		
		boolean isVoid;
		String funcName;
		boolean areParameters;
		String[] parameters;
		String[] stmts;
		
		public Function(String funcLine) {
			int firstParen = funcLine.indexOf('(');
			int secondParen = funcLine.indexOf(')');
			
		    funcName = getFuncName(funcLine);
			isVoid = funcLine.charAt(0) != 'i';
			parameters = funcLine.substring(firstParen+1,secondParen).trim().split("\\s*,\\s*");
			stmts = funcLine.substring(funcLine.indexOf('{')+1,funcLine.indexOf('}')).trim().split("\\s*;\\s*");
		}

		@Override
		public String toString() {
			StringBuilder str = new StringBuilder();
			str.append("isVoid : " + isVoid + "\n");
			str.append("Name   : " + funcName + "\n");
			
			str.append("Para   : \n");
			for(String para : parameters) {
				str.append("  " + para + "\n");
			}
			
			str.append("Stmts  : \n");
			for(String stmt : stmts) {
				str.append("  " + stmt + "\n");
			}
			
			return str.toString();
		}
	}
}


