package compiler488.ast.expn;

import compiler488.ast.type.BooleanType;
import compiler488.symbol.*;
import compiler488.ast.type.*;                                                                                                                                                                           import java.util.*;
import compiler488.runtime.Machine;
import compiler488.codegen.Instructions;

/**
 * Boolean literal constants.
 */
public class BoolConstExpn extends ConstExpn
{
    private boolean  value ;	/* value of the constant */

    public BoolConstExpn(boolean value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    /** Returns the value of the boolean constant */
    @Override
    public String toString () {
	return ( value ? "(true)" : "(false)" );
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    /** Returns the type of this BoolConstExpn */
    public Type doSemantics(SymbolTable table, List<String> errorMsg, 
                            SymbolTable.ScopeType scp) {
        // S20
        return new BooleanType(this.getLineNumber(), this.getColumnNumber());
    }

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scp) {
        instructions.add("PUSH", Machine.PUSH);
        if (value) {
            instructions.add(null, Machine.MACHINE_TRUE);
        } else {
            instructions.add(null, Machine.MACHINE_FALSE);
        }
    }
}
