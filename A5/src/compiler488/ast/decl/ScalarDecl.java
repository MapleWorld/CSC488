package compiler488.ast.decl;

import compiler488.ast.type.Type;
import compiler488.symbol.*;
import java.util.*;

/**
 * Represents the declaration of a simple variable.
 */

public class ScalarDecl extends Declaration {

    public ScalarDecl(String name, Type type, int line, int column) {
        super(name, type, line, column);
    }

    /**
     * Returns a string describing the name and type of the object being
     * declared.
     */
    @Override
    public String toString() {
        return  name + " : " + type ;
    }
    
    public Type doSemantics(SymbolTable table, List<String> errorMsg) {
        // do semantic analysis for this node
        ScalarDeclPart sdp = new ScalarDeclPart(this.name, this.getLineNumber(), this.getColumnNumber());
        return sdp.doSemantics(table, errorMsg, this.type);
    }
}
