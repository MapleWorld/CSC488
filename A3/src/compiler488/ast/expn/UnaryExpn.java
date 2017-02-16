package compiler488.ast.expn;

/**
 * The common features of unary expressions.
 */
public class UnaryExpn extends Expn {

    public final static String OP_NOT = "not";
    public final static String OP_MINUS = "-";

    /** Operand of the unary operator. */
    Expn operand; /* operand of the unary operator. */
    String opSymbol; /* Name of the operator. */

    public UnaryExpn(String opSymbol, Expn operand, int line, int column) {
        super(line, column);

        this.opSymbol = opSymbol;
        this.operand = operand;

        assert ((opSymbol == OP_NOT) || (opSymbol == OP_MINUS));
    }

    /** Returns a string that represents the unary expression. */
    @Override
    public String toString() {
        return (opSymbol + "(" + operand + ")");
    }

    public Expn getOperand() {
        return operand;
    }

    public void setOperand(Expn operand) {
        this.operand = operand;
    }

    public String getOpSymbol() {
        return opSymbol;
    }

    public void setOpSymbol(String opSymbol) {
        this.opSymbol = opSymbol;
    }
}
