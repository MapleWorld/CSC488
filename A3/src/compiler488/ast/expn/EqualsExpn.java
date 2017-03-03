package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

/**
 * Place holder for all binary expression where both operands could be either
 * integer or boolean expressions. e.g. = and not = comparisons
 */
public class EqualsExpn extends BinaryExpn {
    public final static String OP_EQUAL     = "=";
    public final static String OP_NOT_EQUAL = "not =";

    public EqualsExpn(String opSymbol, Expn left, Expn right, int line, int column) {
        super(opSymbol, left, right, line, column);

        assert ((opSymbol == OP_EQUAL) ||
                (opSymbol == OP_NOT_EQUAL));
    }

    /** Checks the semantics of both operands and returns Boolean type */
    public Type doSemantics(SymbolTable table, List<String> errorMsg) {
        // do semantic analysis for this node
        Type leftType = left.doSemantics(table, errorMsg);
        Type rightType = right.doSemantics(table, errorMsg);

        // S32
        if (leftType == null || rightType == null || 
            !(leftType.getClass().equals(rightType.getClass())))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       this.getLineNumber(), this.getColumnNumber(),
                                       "S32",
                                       "the left and right operands are not the same type"));

        // S20
        return new BooleanType(this.getLineNumber(), this.getColumnNumber());
    }
}
