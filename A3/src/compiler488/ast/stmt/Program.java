package compiler488.ast.stmt;

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

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
