package compiler488.ast.expn;

/**
 * Place holder for all ordered comparisions expression where both operands must
 * be integer expressions. e.g. < , > etc. comparisons
 */
public class CompareExpn extends BinaryExpn {
    public final static String OP_LESS          = "<";
    public final static String OP_LESS_EQUAL    = "<=";
    public final static String OP_GREATER       = ">";
    public final static String OP_GREATER_EQUAL = ">=";

    public CompareExpn(String opSymbol, Expn left, Expn right, int line, int column) {
        super(opSymbol, left, right, line, column);

        assert ((opSymbol == OP_LESS) ||
                (opSymbol == OP_LESS_EQUAL) ||
                (opSymbol == OP_GREATER) ||
                (opSymbol == OP_GREATER_EQUAL));
    }

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
