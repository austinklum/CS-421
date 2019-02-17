
/**
 * * Derive
 * @author Austin Klum
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
		Derive derivation = new Derive("+ - 1 2 3");
		derivation.DoDerivation();
		System.out.println(derivation);
	}
	public Derive(String args) {
		 history = new StringBuilder(args);
		 tokens = args.split(" ");
		 realTokensLength = tokens.length;
	}
	
	public void DoDerivation() {
		int numNeighborExp = 0;
		for(int i = 0; i < realTokensLength; i++) {
			char tok = tokens[i].charAt(0);
			if(!IsOp(tok)) {
				ToExp(tok,i);
				numNeighborExp++;
				if(numNeighborExp >= 2) {
					i = ContractOpExpExp(i);
					numNeighborExp = 1;
				}
			} else {
				numNeighborExp = 0;
			}
		}
	}

	public int ContractOpExpExp (int index) {
		int i = 0;
		//pull end of array towards front to simulate contraction
		for(i = index-2; i < realTokensLength; i++) {
			//tokensToString();
			if(i < realTokensLength -1) {
			tokens[i] = tokens[i+1];
			} else {
				tokens[i] = tokens[i];
			}
		}
		//tokensToString();
		//tokens[i] = tokens
		//Update index and array search length
		realTokensLength -= 2;
		history.insert(0, DeriveLineOtherTokAtIndex(tokens[index-2],index-2));
		//tokensToString();
		return index - 2;
	}
	
	/**
	 * Takes the int or var and adds to the history the progress of the derivation.
	 * Changes the token at the index to "Exp"
	 * @param tok token to convert into Exp
	 * @param index where to replace token
	 */
	public void ToExp(char tok, int index) {
		String type = "Var";
		if(tok >= 48 && tok <= 57) {
			type = "Int";
		}
		history.insert(0,DeriveLineOtherTokAtIndex(type,index));
		history.insert(0,DeriveLineOtherTokAtIndex("Literal",index));
		history.insert(0,DeriveLineOtherTokAtIndex("Exp",index));
		tokens[index] = "Exp";
	}
	/**
	 * Creates the Derivation Line but uses the other token at the index provided.
	 * @param otherTok  Other token that will be used at index
	 * @param index Index in line that will use the other token
	 * @return
	 */
	public String DeriveLineOtherTokAtIndex(String otherTok, int index) {
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
	
	public boolean IsOp(char tok) {
		return tok == '+' || tok == '-' || tok == '*' || tok =='/';
	}
	
	public void tokensToString() {
		System.out.println("RTL = " + realTokensLength);
		for(String tok : tokens) {
			System.out.print(tok + " ");
		}
		System.out.println();
	}
}
