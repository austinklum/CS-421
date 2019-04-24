import java.util.HashMap;
import java.util.LinkedList;
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
		String[] parts = Parser.getParts(Parser.readFile("hw04_prog3.txt"),"(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*");
		//Parser.print(parts);
		SemanticSimulator sim = new SemanticSimulator(parts);
		sim.simulate();
	}
	
	String[] program;
	boolean globalsExist;
	String[] globals;
	HashMap<String,String> funcMap;
	LinkedList<KeyValuePair> gamma;
	LinkedList<KeyValuePair> mu;
	int stackPointer;
	
	public SemanticSimulator(String[] parts) {
		program = parts;
		globalsExist = !parts[0].equals("");
		globals = parts[0].trim().split("\\s*;\\s*");
		gamma = new LinkedList<>();
		mu = new LinkedList<>();
		funcMap = new HashMap<>();
		stackPointer = 0;
		for(int i = 1; i < program.length; i++) {
			funcMap.put(getFuncMapKey(program[i]), program[i]);
		}
	}

	public void simulate() {
		printState("0");
		Function global = new Function();
		global.funcName ="global";
		global.stmts = globals;
		global.varsAdded = new LinkedList<>();
	
		for(String stmt : globals) {
			processStmt(stmt, global);
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
			addVar(func.parameters[i], func);
			updateVar(func.parameters[i], getMeaning(param));
			i++;
		}
		
		printState(func.funcName + "_in");
		
		for(String stmt : func.stmts) {
			processStmt(stmt, func);
		}
		
		printState(func.funcName + "_out");
		
		if(func.funcName.equals("main")) {
			return 0;
		}
		
		for(String varName : func.varsAdded) {
			if(!varName.equals(func.funcName)) {
				deleteVar(varName);
			}
		}
		
		
		return func.isVoid ? 0 : deleteVarFuncResult(func.funcName, func.funcResultIndex);
	}
	

	
//	process line
	private void processStmt(String stmt, Function func) {
		String[] stmtParts = stmt.trim().split("\\s*=\\s*");
		
		//Declaration
		String lhs = stmtParts[0];
		
		String varName = lhs;
		if (lhs.contains(" ")) {
			varName = lhs.split("\\s+")[1];
		}
		if(lhs.length() >= 4 && lhs.substring(0,4).equals("int ")){
			addVar(varName, func);
		} 
		
		//Assignment
		if(stmtParts.length > 1) {
			updateVar(varName, evaluate(stmtParts[1]));
		}
		
		//Function
		if(lhs.contains("(")) {
			processFunc(stmt);
		}
		
		//Return stmt
		if(lhs.length() >= 7 && lhs.substring(0,7).equals("return ")) {
			addVar(func.funcName, func);
			updateVar(func.funcName, evaluate(varName));
		}
	}

	private void printState(String stateName) {
		System.out.println("sigma_" + stateName + ":");
		System.out.print("  gamma: {");
		int count = 1;
		for (KeyValuePair item : gamma){
            System.out.print(item);
            if (count < gamma.size()) {
            	System.out.print(", ");
            	count++;
            }
		} 
		System.out.println("}");
		System.out.print("  mu   : {");
		count = 1;
		for(KeyValuePair item : mu) {
			System.out.print(item);
            if (count < mu.size()) {
            	System.out.print(", ");
            	count++;
            }
		}
		System.out.println("}");
		System.out.println("a = " + stackPointer);
		System.out.println();
	}
	
	private int findPairIndex(String key, LinkedList<KeyValuePair> list) {
		int i = 0;
		int biggestAddrSpace = 0;
		int retVal = -1;
		boolean isGamma = list.equals(gamma);
		
		for(KeyValuePair item : list) {
			if(item.key.equals(key)) {
				if(isGamma && biggestAddrSpace <= item.value) {
					biggestAddrSpace = item.value;
					retVal = i;
				} else if (!isGamma) {
					retVal = i;
				}
			}
			i++;
		}
		return retVal;
	}
	
	private void addVar(String var, Function func) {

		func.varsAdded.add(var);
		gamma.add(new KeyValuePair(var, stackPointer));
		mu.add(new KeyValuePair(String.valueOf(stackPointer), null));
		stackPointer++;
		
		if(var.equals(func.funcName)) {
			func.funcResultIndex = stackPointer - 1;
		}
	}
	private void updateVar(String varName, int value) {
		//int i = findPairIndex(varName,gamma);
		//int j = findPairIndex(String.valueOf(gamma.get(findPairIndex(varName,gamma)).value),mu);
		mu.get(findPairIndex(String.valueOf(gamma.get(findPairIndex(varName,gamma)).value),mu)).value = value;
	}
	
	private int deleteVar(String varName) {
		stackPointer--;
		return mu.remove(findPairIndex(String.valueOf(gamma.remove(findPairIndex(varName,gamma)).value), mu)).value;
	}
	
	private int deleteVarFuncResult(String varName, int funcResultIndex) {
		stackPointer--;
		gamma.remove(findPairIndex(varName,gamma));
		return mu.remove(findPairIndex(String.valueOf(funcResultIndex), mu)).value;
	}
	
	private int getVal(String varName) {
		return mu.get(findPairIndex(String.valueOf(gamma.get(findPairIndex(varName,gamma)).value),mu)).value;
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
		LinkedList<String> varsAdded;
		int funcResultIndex;
		
		public Function(String funcLine) {
		    funcName = getFuncName(funcLine);
			isVoid = funcLine.charAt(0) != 'i';
			parameters = getParamsFromFuncSig(funcLine);
			stmts = funcLine.substring(funcLine.indexOf('{')+1,funcLine.indexOf('}')).trim().split("\\s*;\\s*");
			varsAdded = new LinkedList<>();
			funcResultIndex = 0;
		}
		
		public Function() {
			
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
	
	private class KeyValuePair {
		String key;
		Integer value;
		
		KeyValuePair(String k, Integer v) {
			key = k;
			value = v;
		}
		
		@Override
		public String toString() {
			return "<" + (key) + ", " + (value == null ? "undef" : value) + ">";
		}
		
	}
	
}


