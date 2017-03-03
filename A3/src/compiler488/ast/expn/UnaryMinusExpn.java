package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

/**
 * Represents negation of an integer expression
 */
public class UnaryMinusExpn extends UnaryExpn {
    public UnaryMinusExpn(Expn operand, int line, int column) {
        super(UnaryExpn.OP_MINUS, operand, line, column);
    }

    /** 
     * Checks the type of the operand and returns Integer type 
     */
    public Type doSemantics(SymbolTable table, LinkedList<String> errorMsg) {
        // do semantic analysis for this node
        Type operandType = operand.doSemantics(table, errorMsg);
        // S31
        if (operandType == null || !(operandType instanceof IntegerType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       operand.getLineNumber(),
                                       operand.getColumnNumber(),
                                       "S31",
                                       "expected Integer operand"));
        // S21
        return new IntegerType(lineNumber, columnNumber);
    }
}
