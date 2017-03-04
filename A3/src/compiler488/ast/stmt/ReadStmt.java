package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.Readable;
import java.io.*;
import java.util.*;
import compiler488.ast.expn.*;
import compiler488.ast.type.*;
import compiler488.symbol.*;


/**
 * The command to read data into one or more variables.
 */
public class ReadStmt extends Stmt {

    private ASTList<Readable> inputs; // A list of locations to put the values read.
    
    public ReadStmt () {
        inputs = new ASTList<Readable> ();
    }

    public ReadStmt(ASTList<Readable> inputs, int line, int column) {
        super(line, column);
        this.inputs = inputs;
    }

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(inputs);
        return children;
    }

    /** Returns a string describing the <b>read</b> statement. */
    @Override
    public String toString() {
        return "read " + inputs;
    }

    public ASTList<Readable> getInputs() {
        return inputs;
    }

    public void setInputs(ASTList<Readable> inputs) {
        this.inputs = inputs;
    }

    public Type doSemantics(SymbolTable table, List<String> errorMsg, SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
        LinkedList<Readable> elemList = inputs.getList();
        ListIterator<Readable> iterator = elemList.listIterator();
        while (iterator.hasNext()) {
            Readable nextVal = iterator.next();
            // S31
            if (nextVal instanceof IdentExpn || nextVal instanceof SubsExpn) {
                Type result = ((Expn)nextVal).doSemantics(table, errorMsg, scp);
                if (result == null || !(result instanceof IntegerType))
                    errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                               ((Expn)nextVal).getLineNumber(),
                                               ((Expn)nextVal).getColumnNumber(),
                                               "S31",
                                               "expected Integer inputs"));
            }
        }
        return null;
    }
}
