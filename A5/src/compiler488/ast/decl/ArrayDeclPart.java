package compiler488.ast.decl;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

/**
 * Holds the declaration part of an array.
 */
public class ArrayDeclPart extends DeclarationPart {

    /* The lower and upper boundaries of the array. */
    private Integer lb, ub;

    /* The number of objects the array holds. */
    private Integer size;

    /* true iff only upper bound is specified */
    private boolean hasUpperBoundOnly;

    public ArrayDeclPart(String name, Integer i, int line, int column) {
        super(name, line, column);
        this.lb = 1;
        this.ub = i;
        this.hasUpperBoundOnly = true;
    }

    public ArrayDeclPart(String name, Integer lb, Integer ub, int line, int column) {
        super(name, line, column);
        this.lb = lb;
        this.ub = ub;
        this.hasUpperBoundOnly = false;
    }

    /**
     * Returns a string that describes the array.
     */
    @Override
    public String toString() {
        return name + "[" + lb + ".." + ub + "]";
    }

    public Integer getSize() {
        return size;
    }

    public Integer getLowerBoundary() {
        return lb;
    }

    public Integer getUpperBoundary() {
        return ub;
    }

    public void setLowerBoundary(Integer lb) {
        this.lb = lb;
    }

    public void setUpperBoundary(Integer ub) {
        this.ub = ub;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
    /** Adds name to symbol table and checks if name is already declared */
    public Type doSemantics(SymbolTable table, List<String> errorMsg, Type idType) {
        // do semantic analysis for this node
        boolean success;

        // S47
        if (hasUpperBoundOnly)
            // S48
            success = table.addSymbol(name, 
                                      new SymbolTableEntry(new ArraySymbolType(idType, 
                                                                              ub)));
        else {
            // S46
            if (lb > ub)
                errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                           this.getLineNumber(),
                                           this.getColumnNumber(),
                                           "S46",
                                           "expected lower bound <= upper bound"));
            success = table.addSymbol(name,
                                      new SymbolTableEntry(new ArraySymbolType(idType,
                                                                              ub, lb)));
        }            

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
