package compiler488.ast.stmt;

import java.util.*;
import compiler488.runtime.Machine; 

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

    public void doCodeGeneration(List<Short> instructions) {
        // C00
        instructions.add(Machine.PUSHMT);
        instructions.add(Machine.SETD);
        instructions.add((short) 0);

        

    }
}
