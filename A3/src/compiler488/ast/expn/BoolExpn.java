package compiler488.ast.expn;

/**
 * Place holder for all binary expression where both operands must be boolean
 * expressions.
 */
public class BoolExpn extends BinaryExpn {
    public final static String OP_OR    = "or";
    public final static String OP_AND   = "and";

    public BoolExpn(String opSymbol, Expn left, Expn right, int line, int column) {
        super(opSymbol, left, right, line, column);

        assert ((opSymbol == OP_OR) ||
                (opSymbol == OP_AND));
    }

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
