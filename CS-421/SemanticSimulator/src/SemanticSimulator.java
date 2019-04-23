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
		String[] parts = Parser.getParts(Parser.readFile("hw04_prog2.txt"),"(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*");
		//Parser.print(parts);
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
		for(int i = 1; i < program.length; i++) {
			funcMap.put(getFuncMapKey(program[i]), program[i]);
		}
	}

	public void simulate() {
		printState("0");
		for(String stmt : globals) {
			processStmt(stmt,"global");
		}
		printState("global");
		processFunc("main()");
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
	
//	int myFunc(int x, int y) {
//		return x + y;
//	}
//	
//	myFunc(2,2);
	
//	process func
	public int processFunc(String funcSig) {
		String str0 = getFuncMapKey(funcSig);
		String str = funcMap.get(str0);
		Function func = new Function(str);
		int i = 0;
		
		String[] paramsPassed = getParamsFromFuncSig(funcSig);
		
		// Loop on parameters passed in
		for(String param : paramsPassed) {
			addVar(func.parameters[i]);
			updateVar(func.parameters[i], getMeaning(param));
			i++;
		}
		
		printState(func.funcName + "_in");
		
		for(String stmt : func.stmts) {
			processStmt(stmt, func.funcName);
		}
		
		printState(func.funcName + "_out");
		
		for(String param : paramsPassed) {
			deleteVar(param);
		}
		return func.isVoid ? 0 : deleteVar(func.funcName);
	}
	

	
//	process line
	private void processStmt(String stmt, String funcName) {
		String[] stmtParts = stmt.trim().split("\\s*=\\s*");
		
		//Declaration
		String lhs = stmtParts[0];
		
		String varName = lhs;
		if (lhs.contains(" ")) {
			varName = lhs.split("\\s+")[1];
		}
		if(lhs.length() >= 4 && lhs.substring(0,4).equals("int ")){
			addVar(varName);
		} 
		
		//Assignment
		if(stmtParts.length > 1) {
			updateVar(varName, evaluate(stmtParts[1]));
		}
		
		//Function
		if(lhs.contains("(")) {
			processFunc(getFuncName(stmt));
		}
		
		//Return stmt
		if(lhs.length() >= 7 && lhs.substring(0,7).equals("return ")) {
			addVar(funcName);
			updateVar(funcName, evaluate(varName));
		}
	}

	private void printState(String stateName) {
		System.out.println("sigma_" + stateName + ":");
		System.out.print("  gamma: {");
		int count = 1;
		for (String key : gamma.keySet()){
            System.out.print("<" + key + ", " + gamma.get(key) + ">");
            if (count < gamma.size()) {
            	System.out.print(", ");
            	count++;
            }
		} 
		System.out.println("}");
		System.out.print("  mu   : {");
		count = 1;
		for(Integer key : mu.keySet()) {
			System.out.print("<" + key + ", " + (mu.get(key) == -1 ? "undef" : mu.get(key).toString()) + ">");
            if (count < gamma.size()) {
            	System.out.print(", ");
            	count++;
            }
		}
		System.out.println("}");
		System.out.println("a = " + stackPointer);
		System.out.println();
	}
	
	private void addVar(String var) {
		gamma.put(var, stackPointer);
		mu.put(stackPointer, -1);
		stackPointer++;
	}
	private void updateVar(String varName, int value) {
		mu.replace(gamma.get(varName), value);
	}
	
	private int deleteVar(String varName) {
		stackPointer--;
		return mu.remove(gamma.remove(varName));
	}
	
	private int getVal(String varName) {
		return mu.get(gamma.get(varName));
	}

	private int getMeaning(String name) {
		if (name.matches("\\d+")) {
			return Integer.parseInt(name);
	    }else if (name.contains("(")) {
			return processFunc(name);
		} else {
			return getVal(name);
		}
	}

	private String getFuncMapKey(String name) {
		String str =  getFuncName(name) + getParamCount(name);
		return str;
	}
	
//	private int evaluate(String expr) {
//		//String[] exprParts = parseTokens(expr);
//		//Ops :  \s*(\+|-|\*|\/)*\s*
//		//funcs: \s*[a-z]*\(.*\)\s*
//		//vars: \s*[a-z]*\s*
//		expr = "+2*z function(x ,y   )anothaOne(  q  ,  r  )(p) 53";
//		
//		
//		
//		 //Number
//		//Variable Name
//		//Binary Operation
//		//Function Call
//		return -1;
//	}

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
	   
	private int evaluate(String expr) { 
		String[] rhs = removeSpacesFromFuncs(expr).split("\\s+");
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
			String str = funcLine.substring(tmp,funcLine.indexOf('(')).trim();
			return str;
		}
	
	private int getParamCount(String funcLine) {
		return funcLine.substring(funcLine.indexOf('('),funcLine.indexOf(')')+1).split(",").length;
	}
	
	private String[] getParamsFromFuncSig(String funcLine) {
		String[] params = funcLine.substring(funcLine.indexOf('(')+1,funcLine.indexOf(')')).replaceAll("int", "").trim().split("\\s*,\\s*");
		if(params.length == 1 && params[0].equals("")) {
			return new String[0];
		}
		return params;
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
		    funcName = getFuncName(funcLine);
			isVoid = funcLine.charAt(0) != 'i';
			parameters = getParamsFromFuncSig(funcLine);
			stmts = funcLine.substring(funcLine.indexOf('{')+1,funcLine.indexOf('}')).trim().split("\\s*;\\s*");
		}

		@Override
		public String toString() {
			StringBuilder str = new StringBuilder();
			str.append("isVoid : " + isVoid + "\n");
			str.append("Name   : " + funcName + "\n");
			
			str.append("Param  : \n");
			for(String param : parameters) {
				str.append("  " + param + "\n");
			}
			
			str.append("Stmts  : \n");
			for(String stmt : stmts) {
				str.append("  " + stmt + "\n");
			}
			
			return str.toString();
		}
	}
}


