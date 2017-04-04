package compiler488.ast.expn;

import compiler488.ast.Readable;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

/**
 * References to an array element variable
 * 
 * Treat array subscript operation as a special form of unary expression.
 * operand must be an integer expression
 */
public class SubsExpn extends UnaryExpn implements Readable {
    public final static String OP_SUBSCRIPT = "[]";

    private String variable; // name of the array variable
    private Expn subscript;

    public SubsExpn(String variable, Expn subscript, int line, int column) {
        super(OP_SUBSCRIPT, subscript, line, column);
        this.variable = variable;
        this.subscript = subscript;
    }

    /** Returns a string that represents the array subscript. */
    @Override
    public String toString() {
        return (variable + "[" + operand + "]");
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    /** Returns the type of this array in table and checks the array is valid */
    public Type doSemantics(SymbolTable table, List<String> errorMsg,
                            SymbolTable.ScopeType scp) {
        // do semantic analysis for this node

        SymbolTableEntry entry = table.getEntry(variable);
        SymbolType entryType;

        // S31
        Type operandType = operand.doSemantics(table, errorMsg, null);
        if (operandType == null || !(operandType instanceof IntegerType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       operand.getLineNumber(),
                                       operand.getColumnNumber(),
                                       "S31",
                                       "expected array index to be an Integer"));

        // S38
        if (entry != null) {
            entryType = entry.getType();
            if (entryType instanceof ArraySymbolType) {
                // S27
                Type arrayType = ((ArraySymbolType)entryType).getType();
                if (arrayType instanceof BooleanType)
                    return new BooleanType(this.getLineNumber(), this.getColumnNumber());
                else if (arrayType instanceof IntegerType)
                    return new IntegerType(this.getLineNumber(), this.getColumnNumber());
            }
        }

        errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                   this.getLineNumber(), this.getColumnNumber(),
                                   "S38",
                                   "arrayname \"" + variable +
                                   "\" has not been declared as an array"));

        return null;
    }

    private void doCodeGenerationGeneric(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scpType) {
        // PUSH i
        subscript.doCodeGeneration(instructions, numVars, table, scpType);
        // PUSH k
        SymbolTableEntry entry = table.getEntry(variable);
        SymbolType entryType = entry.getType();
        ArraySymbolType arrayType = (ArraySymbolType) entryType;
        int k = arrayType.getLowerBound();
        instructions.add("PUSH", Machine.PUSH);
        instructions.add(null, (short) k);
        // compute i - k (SUB)
        instructions.add("SUB", Machine.SUB);
        // ADDR n m
        instructions.add("ADDR", Machine.ADDR);
        instructions.add("n", (short) entry.getLexicalLevel());
        instructions.add("m", (short) entry.getOrderNumber());
        // ADD
        instructions.add("ADD", Machine.ADD);
    }


    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scpType) {
        doCodeGenerationGeneric(instructions, numVars, table, scpType);
        instructions.add("LOAD", Machine.LOAD);
    }

    /** Generates code to push this identifier's memory address to stack */
    public void doCodeGenerationVariable(Instructions instructions, Deque<Integer> numVars,
                                         SymbolTable table, SymbolTable.ScopeType scpType) {
        doCodeGenerationGeneric(instructions, numVars, table, scpType);
     }
}
