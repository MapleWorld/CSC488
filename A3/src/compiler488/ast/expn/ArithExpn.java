package compiler488.ast.expn;

/**
 * Place holder for all binary expression where both operands must be integer
 * expressions.
 */
public class ArithExpn extends BinaryExpn {
    public final static String OP_PLUS      = "+";
    public final static String OP_MINUS     = "-";
    public final static String OP_TIMES     = "*";
    public final static String OP_DIVIDE    = "/";

    public ArithExpn(String opSymbol, Expn left, Expn right, int line, int column) {
        super(opSymbol, left, right, line, column);

        assert ((opSymbol == OP_PLUS) ||
                (opSymbol == OP_MINUS) ||
                (opSymbol == OP_TIMES) ||
                (opSymbol == OP_DIVIDE));
    }

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
