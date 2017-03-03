package compiler488.ast.expn;

import compiler488.ast.type.IntegerType;
import compiler488.symbol.*;
import java.util.*;

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
    public Type doSemantics(SymbolTable table, LinkedList<String> errorMsg) {
        // S21
        return new IntegerType(lineNumber, columnNumber);
    }
}
