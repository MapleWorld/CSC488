package compiler488.ast.stmt;

import compiler488.ast.Indentable;

/**
 * A placeholder for statements.
 */
public class Stmt extends Indentable {
    public Stmt() {
        super();
    }
    
    public Stmt(int line, int column) {
        super(line, column);
    }

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
