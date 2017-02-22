package compiler488.ast.expn;

/**
 * Represents negation of an integer expression
 */
public class UnaryMinusExpn extends UnaryExpn {
    public UnaryMinusExpn(Expn operand, int line, int column) {
        super(UnaryExpn.OP_MINUS, operand, line, column);
    }

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
