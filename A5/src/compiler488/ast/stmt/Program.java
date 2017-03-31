package compiler488.ast.stmt;

import java.util.*;
import compiler488.runtime.Machine; 
import compiler488.codegen.Instructions;



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

    public void doCodeGeneration(Instructions instructions) {
        // C00
        instructions.add("PUSHMT", Machine.PUSHMT);
        instructions.add("SETD", Machine.SETD);
        instructions.add(null, (short) 0);

        super.doCodeGenChildren(instructions);

        instructions.add("PUSHMT", Machine.PUSHMT);
        instructions.add("ADDR", Machine.ADDR);
        instructions.add(null, (short) 0);
        instructions.add(null, (short) 0);
        instructions.add("SUB", Machine.SUB);
        instructions.add("POPN", Machine.POPN);
        instructions.add("HALT", Machine.HALT);
    }
}
