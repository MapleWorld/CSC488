package compiler488.ast.stmt;

import java.io.PrintStream;
import java.util.*;

import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.expn.Expn;
import compiler488.symbol.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

/**
 * Represents a loop in which the exit condition is evaluated before each pass.
 */
public class WhileDoStmt extends LoopingStmt {

    public WhileDoStmt(Expn expn, Stmt body, int line, int column) {
        super(expn, body, line, column);
    }

    /**
     * Print a description of the <b>while-do</b> construct.
     *
     * @param out
     *            Where to print the description.
     * @param depth
     *            How much indentation to use while printing.
     */
    @Override
    public void printOn(PrintStream out, int depth) {
        Indentable.printIndentOnLn(out, depth, "while " + condition + " do");
        body.printOn(out, depth + 1);
        Indentable.printIndentOnLn(out, depth, "End while-do");
    }

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scp) {
        int LOOP = instructions.getSize();
        condition.doCodeGeneration(instructions, numVars, table, scp);
        instructions.add("PUSH", Machine.PUSH);
        int indexToFill = instructions.getSize();
        // END segment (we don't know the address yet, need to patch it in)
        instructions.add("UNDEFINED", Machine.UNDEFINED);
        instructions.add("BF", Machine.BF);
        // add code to execute statement
        if (body instanceof Scope)
            body.doCodeGeneration(instructions, numVars, table, SymbolTable.ScopeType.LOOP);
        else {
            table.startLoopScope();
            body.doCodeGeneration(instructions, numVars, table, null);
            table.endScope();
        }
        instructions.add("PUSH", Machine.PUSH);
        instructions.add("LOOP", (short) LOOP);
        instructions.add("BR", Machine.BR);

        // patching in all addresses that need to be patched
        int END = instructions.getSize();
        // change the undefined to the current address
        instructions.set("END", (short) END, indexToFill);

        LinkedList<Integer> addressesToBePatched = table.getLoopAddr(table.getNumLoop());
        for (int i = 0; i < addressesToBePatched.size(); i++) {
            instructions.set("END", (short) END, addressesToBePatched.get(i));
        }
    }
}
