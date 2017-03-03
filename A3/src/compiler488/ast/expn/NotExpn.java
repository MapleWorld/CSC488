package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

/**
 * Represents the boolean negation of an expression.
 */
public class NotExpn extends UnaryExpn {
    public NotExpn(Expn operand, int line, int column) {
        super(UnaryExpn.OP_NOT, operand, line, column);
    }
    
    /**
     * Checks the type of the operand and returns with Boolean type
     */
    public Type doSemantics(SymbolTable table, List<String> errorMsg) {
        // do semantic analysis for this node
        Type operandType = operand.doSemantics(table, errorMsg);

        // S30
        if (operandType == null || !(operandType instanceof BooleanType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       operand.getLineNumber(),
                                       operand.getColumnNumber(),
                                       "S30",
                                       "expected Boolean operand"));
        // S20
        return new BooleanType(this.getLineNumber(), this.getColumnNumber());
    }
}
