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
		String[] parts = Parser.getParts(Parser.readFile("hw04_prog3.txt"),"(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*");
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
		int firstParen = funcLine.indexOf('(');
		int secondParen = funcLine.indexOf(')');
		
		String methodName = funcLine.substring(0,firstParen);
		String parameterLine = funcLine.substring(firstParen+1,secondParen);
		String[] parameters = Parser.getParts(parameterLine,"(int)\\s+[a-z]*");
	}
//	process line
//	add var
//	update var
//	delete var
//	display gamma
//	display mu
//	display sigma
}
