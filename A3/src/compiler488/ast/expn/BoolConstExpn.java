package compiler488.ast.expn;

import compiler488.ast.type.BooleanType;
import compiler488.symbol.*;                                                                                                                                                                               import java.util.*;
 
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
    public Type doSemantics(SymbolTable table, LinkedList<String> errorMsg) {
        // S20
        return new BooleanType(lineNumber, columnNumber);
    }
}
