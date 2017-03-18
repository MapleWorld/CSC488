package compiler488.ast.stmt;

import java.util.List;

import java.io.PrintStream;

import compiler488.ast.Indentable;
import compiler488.ast.expn.Expn;
import compiler488.ast.type.*;
import compiler488.symbol.*;

/**
 * The command to return from a function or procedure.
 */
public class ReturnStmt extends Stmt {
    // The value to be returned by a function.
    private Expn value = null;
    
    public ReturnStmt(Expn value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    /**
     * Construct a procedure <code>return</code> statement (with no return
     * value)
     */
    public ReturnStmt(int line, int column) {
        this(null, line, column);
    }

    /**
     * Print <b>return</b> or <b>return with </b> expression on a line, by itself.
     *
     * @param out
     *            Where to print.
     * @param depth
     *            How much indentation to use while printing.
     */
    @Override
    public void printOn(PrintStream out, int depth) {
        Indentable.printIndentOn(out, depth);
        if (value == null)
            out.println("return ");
        else
            out.println("return with " + value );
    }

    public Expn getValue() {
        return value;
    }
    
    public void setValue(Expn value) {
        this.value = value;
    }

    public Type doSemantics(SymbolTable table, List<String> errorMessages, 
                            SymbolTable.ScopeType scp) {
        // TODO: Check S51.
        // S51 & S52: Return only when in a function or a procedure.
        SymbolTable.ScopeType currentScope = table.getScopeType();
        if (value == null) {
            if (currentScope != SymbolTable.ScopeType.PROCEDURE) {
                errorMessages.add(String.format("%d:%d: error %s: %s\n",
                                  this.getLineNumber(), this.getColumnNumber(),
                                  "S52",
                                  "Return statement is outside a procedure"));
            }
        } else {
            if (currentScope != SymbolTable.ScopeType.FUNCTION) {
                errorMessages.add(String.format("%d:%d: error %s: %s\n",
                                  this.getLineNumber(), this.getColumnNumber(),
                                  "S51",
                                  "Return statement is outside a function"));
            }
            else {
                // S35: Check that the expression type matches the return type of enclosing function.
                Type result = value.doSemantics(table, errorMessages, null);
                if (result == null || !(table.getReturnType().getClass().equals(result.getClass())))
                    errorMessages.add(String.format("%d:%d: error %s: %s\n", 
                                                    this.getLineNumber(), this.getColumnNumber(),
                                                    "S35",
                                                    "expression type does not match the return type of enclosing function"));
            }
        }
        return null;
    }
}
