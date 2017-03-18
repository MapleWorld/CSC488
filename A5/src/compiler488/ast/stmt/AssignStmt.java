package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.expn.Expn;
import compiler488.ast.type.*;
import compiler488.symbol.*;

/**
 * Holds the assignment of an expression to a variable.
 */
public class AssignStmt extends Stmt {
    /*
     * lval is the location being assigned to, and rval is the value being
     * assigned.
     */
    private Expn lval, rval;

    public AssignStmt(Expn lval, Expn rval, int line, int column) {
        super(line, column);
        this.lval = lval;
        this.rval = rval;
    }

    /** Returns a string that describes the assignment statement. */
    @Override
    public String toString() {
        return "Assignment: " + lval + " := " + rval;
    }
    
    public Expn getLval() {
        return lval;
    }

    public void setLval(Expn lval) {
        this.lval = lval;
    }

    public Expn getRval() {
        return rval;
    }

    public void setRval(Expn rval) {
        this.rval = rval;
    }

    /** Checks the left hand side and the right hand side of assignments 
     * and makes sure they are the same type. */
    public Type doSemantics(SymbolTable table, List<String> errorMessages, SymbolTable.ScopeType scpType) {
        // S34: Check that variable and expression in assignment are the same type.
        Type lvalType = lval.doSemantics(table, errorMessages, null);
        Type rvalType = rval.doSemantics(table, errorMessages, null);
        if (lvalType == null || rvalType == null || 
            !(lvalType.getClass().equals(rvalType.getClass()))) {
            errorMessages.add(String.format("%d:%d: error %s: %s\n",
                                            this.getLineNumber(), this.getColumnNumber(),
                                            "S34",
                                            "variable and expression in assignment are not the same type"));
        }
        return null;
    }
}
