package compiler488.ast.stmt;

import java.util.*;
import compiler488.ast.*;
import compiler488.ast.expn.*;
import compiler488.symbol.*;
import compiler488.ast.type.*;

/**
 * The command to write data on the output device.
 */
public class WriteStmt extends Stmt {
    private ASTList<Printable> outputs; // The objects to be printed.
    
    public WriteStmt(ASTList<Printable> outputs, int line, int column) {
        super(line, column);
        this.outputs = outputs;
    }
    
    public WriteStmt () {
        outputs = new ASTList<Printable> ();
    }
    
    /** Returns a description of the <b>write</b> statement. */
    @Override
    public String toString() {
        return "write " + outputs;
    }
    
    public ASTList<Printable> getOutputs() {
        return outputs;
    }
    
    public void setOutputs(ASTList<Printable> outputs) {
        this.outputs = outputs;
    }

    /** Checks the semantics of outputs */ 
    public Type doSemantics(SymbolTable table, List<String> errorMsg, SymbolTable.ScopeType scp) {
        LinkedList<Printable> elemList = outputs.getList();
        ListIterator<Printable> iterator = elemList.listIterator();
        while (iterator.hasNext()) {
            Expn nextVal = (Expn)iterator.next();
            // S31
            Type result = nextVal.doSemantics(table, errorMsg, null);
            if (!(nextVal instanceof SkipConstExpn) && 
                !(nextVal instanceof TextConstExpn) &&
                (result == null || !(result instanceof IntegerType)))
                errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                           nextVal.getLineNumber(),
                                           nextVal.getColumnNumber(),
                                           "S31",
                                           "expected Integer outputs"));
            
        }
        return null;
    }
}
