package compiler488.ast.expn;

import compiler488.symbol.*;
import compiler488.ast.*;
import compiler488.ast.expn.*;
import compiler488.ast.type.*;
import java.util.*;

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

    /** Returns the type of this IdentExpn based on table (null if this
     *  IdentExpn is not in the table).
     */
    public Type doSemantics(SymbolTable table, List<String> errMsg, 
                            SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
        SymbolTableEntry entry = table.getEntry(ident);
        SymbolType entryType;

        // S37
        if (entry != null) {
            entryType = entry.getType();
            if (entryType instanceof ScalarSymbolType) {
                // S26
                Type identType = ((ScalarSymbolType)entryType).getType();
                if (identType instanceof BooleanType)
                    return new BooleanType(this.getLineNumber(), this.getColumnNumber());
                else if (identType instanceof IntegerType)
                    return new IntegerType(this.getLineNumber(), this.getColumnNumber());
            }
            else if (entryType instanceof FunctionSymbolType) {
                FunctionCallExpn func = new FunctionCallExpn(ident, new ASTList<Expn> (),
                                                             this.getLineNumber(),
                                                             this.getColumnNumber());
                return func.doSemantics(table, errMsg, scp);
            }
        }

        // either there is no ident in table or ident is not a scalar
        // var in table
        errMsg.add(String.format("%d:%d: error %s: %s\n",
                                   this.getLineNumber(), this.getColumnNumber(),
                                   "S37",
                                   "identifier \"" + ident + "\" has not been declared as a scalar variable"));


        return null;
    }
}
