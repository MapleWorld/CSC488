package compiler488.ast.decl;

import compiler488.ast.AST;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;
import compiler488.codegen.Instructions;

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

    public Type doSemantics(SymbolTable table, List<String> errorMsg, Type type) {
        // do semantic analysis for this node
        return null;
    }

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars, SymbolTable table,
                                 Type type) {}
}
