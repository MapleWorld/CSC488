package compiler488.ast.stmt;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.Readable;
import java.io.*;
import java.util.*;
import compiler488.ast.expn.*;
import compiler488.ast.type.*;
import compiler488.symbol.*;


import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

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

    /** Checks the semantics on inputs */
    public Type doSemantics(SymbolTable table, List<String> errorMsg, SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
        LinkedList<Readable> elemList = inputs.getList();
        ListIterator<Readable> iterator = elemList.listIterator();
        while (iterator.hasNext()) {
            Expn nextVal = (Expn)iterator.next();
            Type result = nextVal.doSemantics(table, errorMsg, null);
            // S31
            if (result == null || !(result instanceof IntegerType))
                errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                           nextVal.getLineNumber(),
                                           nextVal.getColumnNumber(),
                                           "S31",
                                           "expected Integer inputs"));

        }
        return null;
    }


    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars, SymbolTable table, SymbolTable.ScopeType scp) {

        // loop through inputs
        // for each input
        // do codegeneration
        //
        LinkedList<Readable> elemList = inputs.getList();
        ListIterator<Readable> iterator = elemList.listIterator();
        while (iterator.hasNext()) {
            iterator.next().doCodeGenerationVariable(instructions, numVars, table, null);
            instructions.add("READI", Machine.READI);
            instructions.add("STORE", Machine.STORE);
        }


    }
}
