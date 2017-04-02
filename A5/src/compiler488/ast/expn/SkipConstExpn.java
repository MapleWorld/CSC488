package compiler488.ast.expn;

import compiler488.ast.Printable;
import java.util.*;
import compiler488.runtime.Machine;
import compiler488.codegen.Instructions;
import compiler488.symbol.*;

/**
 * Represents the special literal constant associated with writing a new-line
 * character on the output device.
 */
public class SkipConstExpn extends ConstExpn implements Printable {
    
    public SkipConstExpn(int line, int column) {
        super(line, column);
    }
    
    /** Returns the string <b>"skip"</b>. */
    @Override
    public String toString() {
        return " newline ";
    }

    /** Adds instruction to print newline */
    public void doCodeGenerationForWrite(Instructions instructions, Deque<Integer> numVars, 
                                         SymbolTable table, SymbolTable.ScopeType scpType) {
        instructions.add("PUSH", Machine.PUSH);
        instructions.add(null, (short) '\n');
        instructions.add("PRINTC", Machine.PRINTC);
    }
}
