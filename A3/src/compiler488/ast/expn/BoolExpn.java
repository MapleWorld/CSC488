package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

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

    /** Checks the semantics of both operands and returns Boolean type */
    public Type doSemantics(SymbolTable table, List<String> errorMsg) {
        // do semantic analysis for this node
        Type leftType = left.doSemantics(table, errorMsg);
        Type rightType = right.doSemantics(table, errorMsg);

        // S30
        if (leftType == null || !(leftType instanceof BooleanType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       leftType.getLineNumber(),
                                       leftType.getColumnNumber(),
                                       "S30",
                                       "expected Boolean operand"));
        if (rightType == null || !(rightType instanceof BooleanType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       rightType.getLineNumber(),
                                       rightType.getColumnNumber(),
                                       "S30",
                                       "expected Boolean operand"));

        // S20
        return new BooleanType(this.getLineNumber(), this.getColumnNumber());
    }
}
