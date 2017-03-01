package compiler488.ast;

import java.util.List;

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
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public AST getParent() {
        return parent;
    }

    public void setParent(AST parent) {
        this.parent = parent;
    }

    public List<AST> getChildren() {
        throw new UnsupportedOperationException("getChildren() not implemented for AST.");
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

    public void doSemantics() {
        throw new UnsupportedOperationException("doSemantics() not implemented for AST.");
    }
}
