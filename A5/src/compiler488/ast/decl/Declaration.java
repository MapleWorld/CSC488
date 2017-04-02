package compiler488.ast.decl;

import compiler488.ast.Indentable;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;
import compiler488.codegen.Instructions;

/**
 * The common features of declarations.
 */
public class Declaration extends Indentable {
    /** The type of thing being declared. */
    protected Type type = null;
    
    public Declaration(String name, Type type, int line, int column) {
        super(line, column);
        this.name = name;
        this.type = type;
    }
    
    /** The name of the thing being declared. */
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type doSemantics(SymbolTable table, List<String> errorMessages, 
                            SymbolTable.ScopeType scp) {
        return null;
    }

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars, 
                                 SymbolTable table, SymbolTable.ScopeType scp) {}
}
