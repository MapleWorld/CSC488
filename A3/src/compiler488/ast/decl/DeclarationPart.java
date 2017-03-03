package compiler488.ast.decl;

import compiler488.ast.AST;
import compiler488.symbol.*;
import java.util.*;

/**
 * The common features of declarations' parts.
 */
public class DeclarationPart extends AST {

    /** The name of the thing being declared. */
    protected String name;

    public DeclarationPart(String name, int line, int column) {
        super(line, column);
        this.name = name;
    }   
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type doSemantics(SymbolTable table, LinkedList<String> errorMsg, Type type) {
        // do semantic analysis for this node
        return null;
    }

}
