package compiler488.ast.stmt;

import java.util.*;
import compiler488.runtime.Machine; 
import compiler488.codegen.Instructions;
import compiler488.symbol.*;


/**
 * Placeholder for the scope that is the entire program
 */
public class Program extends Scope {
    public Program(Scope scope, int lineNumber, int columnNumber) {
        super(scope.getDeclarations(), scope.getStatements(), lineNumber, columnNumber);
    }

    public Program(Scope scope) {
        super(scope);
    }

    /** Emits code to prepare and end program execution */
    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars, 
                                 SymbolTable table, SymbolTable.ScopeType scpType) {
        // C00
        instructions.add("PUSHMT", Machine.PUSHMT);
        instructions.add("SETD", Machine.SETD);
        instructions.add(null, (short) 0);
        
        table.startScope(scpType);
        super.doCodeGenChildren(instructions, numVars, table);
        table.endScope();

        // C01
        instructions.add("HALT", Machine.HALT);
    }
}
