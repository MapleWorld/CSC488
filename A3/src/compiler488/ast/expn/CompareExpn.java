package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

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

    /** Checks the semantics of both operands and returns Boolean type */
    public Type doSemantics(SymbolTable table, List<String> errorMsg, SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
        Type leftType = left.doSemantics(table, errorMsg);
        Type rightType = right.doSemantics(table, errorMsg);

        // S31
        if (leftType == null || !(leftType instanceof IntegerType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       leftType.getLineNumber(),
                                       leftType.getColumnNumber(),
                                       "S31",
                                       "expected Integer operand"));
        if (rightType == null || !(rightType instanceof IntegerType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       rightType.getLineNumber(),
                                       rightType.getColumnNumber(),
                                       "S31",
                                       "expected Integer operand"));

        // S20
        return new BooleanType(this.getLineNumber(), this.getColumnNumber());
    }

}
