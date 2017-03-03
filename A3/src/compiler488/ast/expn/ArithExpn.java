package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

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

    /** Checks the semantics of both operands and returns Integer type */
    public Type doSemantics(SymbolTable table, LinkedList<String> errorMsg) {
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

        // S21
        return new IntegerType(lineNumber, columnNumber);
    }
}
