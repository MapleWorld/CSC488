package compiler488.ast;

import java.util.*;
import compiler488.codegen.Instructions;
import compiler488.symbol.*;

public interface Readable {

	/*
	 * Classes that extend this interface can be used
	 * as arguments to ReadStmt. Don't confuse this
	 * concept with the printing of the
	 * AST itself.
	 */

    public void doCodeGenerationVariable(Instructions instructions, Deque<Integer> numVars, SymbolTable table, SymbolTable.ScopeType scpType);
}
