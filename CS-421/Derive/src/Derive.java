
/**
 * * Derive
 * @author Austin Klum
 * 2019/02/17
 * Derives the following BNF grammar for postfix arthmetic.
 *  Exp     → + Exp Exp | - Exp Exp | * Exp Exp | / Exp Exp | Literal
 *	Literal → Var | Int
 *  Var     → a | b | · · · | z
 *	Int     → 0 | 1 | · · · | 9
 */


public class Derive {
	int realTokensLength;
	StringBuilder history;
	String[] tokens;
	
	public static void main(String[] args) {
		Derive derivation = new Derive(args[0]);
		derivation.doDerivation();
		System.out.println(derivation);
	}
	public Derive(String args) {
		 history = new StringBuilder(args);
		 tokens = args.split(" ");
		 realTokensLength = tokens.length;
	}
	
	/**
	 * Does the work of the program.
	 *  Loops through the tokens and performs contractions and transitions
	 *  whiling keeping track of a history of events.
	 *  @warning Modifies realTokensLength and history
	 */
	public void doDerivation() {
		int numNeighborExp = 0;
		int lastContract = 0;
		for(int i = 0; i < realTokensLength; i++) {
			char tok = tokens[i].charAt(0);
			
			if(!isOp(tok)) {
				toExp(tok,i);
				numNeighborExp++;
				//If there are two consecutive Exp we can contract the Op Exp Exp -> Exp
				if(numNeighborExp >= 2) {
					i = ContractOpExpExp(i); // Contracts the array and sets i -= 2
					lastContract = i;
					numNeighborExp = 1; // We contracted down to a single Exp, thus we have 1 consecutive Exp
				}
			} else { // If we saw an operator that means there are 0 consecutive Exp tokens
				numNeighborExp = 0;
			}
		}
		//Sometimes the last contraction will contract to nothing and it should display Exp
		if (lastContract == 2) {
			history.insert(0,"Exp\n");
		}
	}

	/**
	 * Contracts the derivation from Op Exp Exp -> Exp. 
	 * Subtracts 2 from realTokensLength .
	 * Adds the contraction to history of derivations.
	 * @warning Modifies realTokensLength, tokens, and history
	 * @param index The last instance of Exp that needs to be contracted. index - 2 is where we should start contracting on.
	 * @return index - 2
	 */
	public int ContractOpExpExp (int index) {
		//pull end of array towards front to simulate contraction
		for(int i = index-2; i < realTokensLength - 2; i++) {
			tokens[i] = tokens[i+2];
		}

		//Update index and array search length
		realTokensLength -= 2;
		history.insert(0, deriveLineOtherTokenAtIndex(tokens[index-2],index-2));
		return index - 2;
	}
	
	/**
	 * Takes the int or var and adds to the history the progress of the derivation.
	 * Changes the token at the index to "Exp"
	 * @warning Modifies tokens and history
	 * @param tok token to convert into Exp
	 * @param index where to replace token
	 */
	public void toExp(char tok, int index) {
		String type = "Var";
		if(tok >= 48 && tok <= 57) {
			type = "Int";
		}
		history.insert(0,deriveLineOtherTokenAtIndex(type,index));
		history.insert(0,deriveLineOtherTokenAtIndex("Literal",index));
		history.insert(0,deriveLineOtherTokenAtIndex("Exp",index));
		tokens[index] = "Exp";
	}
	/**
	 * Creates the Derivation Line but uses the other token at the index provided.
	 * @param otherTok  Other token that will be used at index
	 * @param index Index in line that will use the other token
	 * @return
	 */
	public String deriveLineOtherTokenAtIndex(String otherTok, int index) {
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < realTokensLength; i++) {
			if (i == index) {
				//Uses the other token at index
				line.append(otherTok);
			} else {
				//Add what's suppose to be there
				line.append(tokens[i]);
			}
			//At the end of line add newline instead of space
			if(i < realTokensLength - 1) {
				line.append(" ");
			} else {
				line.append("\n");
			}
		}
		return line.toString();
		
	}
	
	@Override
	public String toString() {
		return history.toString();
	}
	
	public boolean isOp(char tok) {
		return tok == '+' || tok == '-' || tok == '*' || tok =='/';
	}
	
}
