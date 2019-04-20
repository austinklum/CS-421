/**
 * Semantic Simulator
 * Parses programs from EBNF defined and simulates the needed 
 * states and transitions a complier would need to take.
 * @author Austin Klum
 * 2019/04/20
 */


public class SemanticSimulator {

	public static void main(String[] args) {
		String[] parts = Parser.getParts(Parser.readFile(args[0]));
		Parser.print(parts);
		simulate(parts);
	}

	public static void simulate(String[] parts) {
		
	}
}
