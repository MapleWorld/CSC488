package compiler488.ast.type;

import compiler488.ast.AST;

/**
 * A placeholder for types.
 */
public class Type extends AST {
    public Type(int line, int column) {
        super(line, column);
    }

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
