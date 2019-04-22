import java.util.HashMap;
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
	HashMap<String, Integer> gamma;
	HashMap<Integer, Integer> mu;
	int stackPointer;
	
	public SemanticSimulator(String[] parts) {
		program = parts;
		globalsExist = !parts[0].equals("");
		globals = parts[0].trim().split("\\s*;\\s*");
		gamma = new HashMap<>();
		mu = new HashMap<>();
		stackPointer = 0;
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
	public void processFunc(String funcLine) {
		Function func = new Function(funcLine);
		
		//printState(func.methodName + "_in");
		
		//System.out.println(func);
		evaluate("thing");
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
	
	private void updateVar(String varName, int value) {
		mu.replace(gamma.get(varName), value);
	}
	
	private void deleteVar(String varName) {
		mu.remove(gamma.remove(varName));
		stackPointer--;
	}

	private int evaluate(String expr) {
		//String[] exprParts = parseTokens(expr);
		//Ops :  \s*(\+|-|\*|\/)*\s*
		//funcs: \s*[a-z]*\(.*\)\s*
		//vars: \s*[a-z]*\s*
		expr = "+2*z function()";
		 Pattern programPattern = Pattern.compile("(\\s*[a-z]*\\(.*\\)\\s*)|(\\s*[a-z]*\\s*)|(\\s*(\\+|-|\\*|\\/)*\\s*)");
		 Matcher match = programPattern.matcher(expr);
	        while (match.find()) {
	        	String str0 = match.group(0);
	        	String str1 = match.group(1);
	        	String str2 = match.group(2);
	        	String str3 = match.group(3);
	        	String str4 = match.group(4);
	        	//match = programPattern.matcher(expr);
	        }
		 //Number
		//Variable Name
		//Binary Operation
		//Function Call
		return -1;
	}

	private void addVar(String var) {
		gamma.put(var, stackPointer);
		mu.put(stackPointer, -1);
		stackPointer++;
	}
//	add var
//	update var
//	delete var
//	display gamma
//	display mu
//	display sigma
	private class Function {
		
		boolean isVoid;
		String methodName;
		boolean areParameters;
		String[] parameters;
		String[] stmts;
		
		public Function(String funcLine) {
			int firstParen = funcLine.indexOf('(');
			int secondParen = funcLine.indexOf(')');
			
			methodName = funcLine.substring(funcLine.indexOf(' ')+1,firstParen);
			isVoid = funcLine.charAt(0) != 'i';
			parameters = funcLine.substring(firstParen+1,secondParen).trim().split("\\s*,\\s*");
			stmts = funcLine.substring(funcLine.indexOf('{')+1,funcLine.indexOf('}')).trim().split("\\s*;\\s*");
		}
		
		@Override
		public String toString() {
			StringBuilder str = new StringBuilder();
			str.append("isVoid : " + isVoid + "\n");
			str.append("Name   : " + methodName + "\n");
			
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


