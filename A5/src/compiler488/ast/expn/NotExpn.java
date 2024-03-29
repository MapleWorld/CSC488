package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

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
    public Type doSemantics(SymbolTable table, List<String> errorMsg,
                            SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
        Type operandType = operand.doSemantics(table, errorMsg, null);

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

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scpType) {
        // not expn <=> true iff operand is equal to false
        operand.doCodeGeneration(instructions, numVars, table, null);
        instructions.add("PUSH", Machine.PUSH);
        instructions.add("MACHINE_FALSE", Machine.MACHINE_FALSE);
        instructions.add("EQ", Machine.EQ);
    }
}
