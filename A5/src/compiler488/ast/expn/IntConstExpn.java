package compiler488.ast.expn;

import compiler488.symbol.*;
import java.util.*;
import compiler488.ast.type.*;
import compiler488.runtime.Machine;
import compiler488.codegen.Instructions;

/**
 * Represents a literal integer constant.
 */
public class IntConstExpn extends ConstExpn
{
    private Integer value;	// The value of this literal.

    public IntConstExpn(Integer value, int line, int column) {
        super(line, column);
        this.value = value;
    }
    
    /** Returns a string representing the value of the literal. */
    @Override
    public String toString () { return value.toString (); }
        
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    /** Returns the type of this IntConstExpn */
    public Type doSemantics(SymbolTable table, List<String> errorMsg,
                            SymbolTable.ScopeType scp) {
        // S21
        return new IntegerType(this.getLineNumber(), this.getColumnNumber());
    }

    /** Adds code to push the constant value to stack */
    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scp) {
        instructions.add("PUSH", Machine.PUSH);
        instructions.add(null, value.shortValue());
    }
}
