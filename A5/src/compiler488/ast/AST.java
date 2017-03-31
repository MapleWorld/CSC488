package compiler488.ast;

import java.util.List;
import compiler488.symbol.*;
import java.util.*;
import compiler488.ast.type.*;

/**
 * This is a placeholder at the top of the Abstract Syntax Tree hierarchy. It is
 * a convenient place to add common behaviour.
 * @author  Ou Ye
 */
public class AST {
	public final static String version = "Winter 2017";

    private AST parent;
    private int lineNumber;
    private int columnNumber;

    public AST() {
        this.parent = null;
        this.lineNumber = 0;
        this.columnNumber = 0;
    }

    /**
     * Construct an AST node with the given lineNumber and columnNumber.
     *
     * @param lineNumber the line number
     * @param columnNumber the column number
     */
    public AST(int lineNumber, int columnNumber) {
        // the cup file causes the lineNumber to be off by 1
        this.lineNumber = lineNumber + 1;
        this.columnNumber = columnNumber;
    }

    public AST getParent() {
        return parent;
    }

    public void setParent(AST parent) {
        this.parent = parent;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Type doSemantics(SymbolTable table, List<String> errorMessages, SymbolTable.ScopeType scp) {
        throw new UnsupportedOperationException("doSemantics not implemented for AST.");
    }

    public void doCodeGeneration() {
        throw new UnsupportedOperationException("doCodeGeneration not implemented for AST.");
    }

}
