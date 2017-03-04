package compiler488.ast.decl;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

/**
 * Represents the declaration of a simple variable.
 */
public class ScalarDeclPart extends DeclarationPart {

    public ScalarDeclPart(String name, int line, int column) {
        super(name, line, column);
    }
    
    /**
     * Returns a string describing the name of the object being
     * declared.
     */
    @Override
    public String toString() {
        return name;
    }

    public Type doSemantics(SymbolTable table, List<String> errorMsg, Type idType) {
        // do semantic analysis for this node
        // S10, S47
        boolean success = table.addSymbol(name, 
                                          new SymbolTableEntry(new ScalarSymbolType(idType, 
                                                                                    false)));

        // S37
        if (!success)
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       this.getLineNumber(),
                                       this.getColumnNumber(),
                                       "S37",
                                       "identifier \"" + name + "\" has already been declared in current scope"));
        return null;
    }
}
