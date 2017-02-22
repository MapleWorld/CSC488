package compiler488.ast.expn;

/**
 * Represents the boolean negation of an expression.
 */
public class NotExpn extends UnaryExpn {
    public NotExpn(Expn operand, int line, int column) {
        super(UnaryExpn.OP_NOT, operand, line, column);
    }
    
    public void doSemantics() {
        // do semantic analysis for this node
    }
}
