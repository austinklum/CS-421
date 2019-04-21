import java.util.HashMap;

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
		String[] parts = Parser.getParts(Parser.readFile("hw04_prog14.txt"),"(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*");
		Parser.print(parts);
		SemanticSimulator sim = new SemanticSimulator(parts);
		sim.simulate();
	}
	
	String[] program;
	boolean globalsExist;
	HashMap<String,Integer> gamma;
	HashMap<Integer,Integer> mu;
	int stackPointer;
	
	public SemanticSimulator(String[] parts) {
		program = parts;
		globalsExist = !parts[0].equals("");
		gamma = new HashMap<>();
		mu = new HashMap<>();
		stackPointer = 0;
	}

	public void simulate() {
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
		printState(func.methodName + "_in");
		
		System.out.println(func);
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
			System.out.print("<" + key + ", " + mu.get(key) + ">");
            if (count < gamma.size()) {
            	System.out.print(", ");
            	count++;
            }
		}
		System.out.println("}");
		System.out.println("a = " + stackPointer);
	}
//	process line
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


