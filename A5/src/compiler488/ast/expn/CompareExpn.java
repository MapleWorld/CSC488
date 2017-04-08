package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

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
        Type leftType = left.doSemantics(table, errorMsg, null);
        Type rightType = right.doSemantics(table, errorMsg, null);

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

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
            SymbolTable table, SymbolTable.ScopeType scp) {
        if (opSymbol == OP_LESS) {
            left.doCodeGeneration(instructions, numVars, table, scp);
            right.doCodeGeneration(instructions, numVars, table, scp);
            instructions.add("LT", Machine.LT);
        } else if (opSymbol == OP_LESS_EQUAL) {
            // not (b > a) <=> a <= b
            left.doCodeGeneration(instructions, numVars, table, scp);
            right.doCodeGeneration(instructions, numVars, table, scp);
            instructions.add("SWAP", Machine.SWAP);
            instructions.add("LT", Machine.LT);
            instructions.add("PUSH", Machine.PUSH);
            instructions.add("MACHINE_FALSE", Machine.MACHINE_FALSE);
            instructions.add("EQ", Machine.EQ);
        } else if (opSymbol == OP_GREATER) {
            // b < a <=> a > b
            left.doCodeGeneration(instructions, numVars, table, scp);
            right.doCodeGeneration(instructions, numVars, table, scp);
            instructions.add("SWAP", Machine.SWAP);
            instructions.add("LT", Machine.LT);
        } else if (opSymbol == OP_GREATER_EQUAL) {
            left.doCodeGeneration(instructions, numVars, table, scp);
            right.doCodeGeneration(instructions, numVars, table, scp);
            instructions.add("LT", Machine.LT);
            instructions.add("PUSH", Machine.PUSH);
            instructions.add("MACHINE_FALSE", Machine.MACHINE_FALSE);
            instructions.add("EQ", Machine.EQ);
        }
    }
}
