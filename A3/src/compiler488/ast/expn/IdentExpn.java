package compiler488.ast.expn;

import compiler488.ast.Readable;

/**
 * References to a scalar variable.
 */
public class IdentExpn extends Expn implements Readable {
    private String ident; // name of the identifier

    public IdentExpn(String ident, int line, int column) {
        super(line, column);
        this.ident = ident;
    }

    /**
     * Returns the name of the variable or function.
     */
    @Override
    public String toString() {
        return ident;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public Type doSemantics(SymbolTable table, LinkedList<String> errorMsg) {
        // do semantic analysis for this node

        // .cup checks S37
        return new ScalarType(this.getLineNumber(), this.getColumnNumber());
    }
}
