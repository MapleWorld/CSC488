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
 * Represents a loop in which the exit condition is evaluated after each pass.
 */
public class RepeatUntilStmt extends LoopingStmt {

    public RepeatUntilStmt(Expn expn, Stmt body, int line, int column) {
        super(expn, body, line, column);
    }

    /**
     * Print a description of the <b>repeat-until</b> construct.
     *
     * @param out
     *            Where to print the description.
     * @param depth
     *            How much indentation to use while printing.
     */
    @Override
    public void printOn(PrintStream out, int depth) {
        Indentable.printIndentOnLn(out, depth, "repeat");
        body.printOn(out, depth + 1);
        Indentable.printIndentOnLn(out, depth, " until "  + condition );
    }

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scp) {
        int LOOP = instructions.getSize();

        // add code to execute statement
        if (body instanceof Scope)
            body.doCodeGeneration(instructions, numVars, table, SymbolTable.ScopeType.LOOP);
        else {
            table.startLoopScope();
            body.doCodeGeneration(instructions, numVars, table, null);
            table.endScope();
        }

        condition.doCodeGeneration(instructions, numVars, table, scp);
        instructions.add("PUSH", Machine.PUSH);
        instructions.add("LOOP", (short) LOOP);
        instructions.add("BF", Machine.BF);

        // patching in all addresses that need to be patched
        int END = instructions.getSize();
        LinkedList<Integer> addressesToBePatched = table.getLoopAddr(table.getNumLoop());
        // if there's no addresses to be patched (i.e., no 'exits' in this loop),
        // then just return.
        if (addressesToBePatched == null) {
            return;
        }
        for (int i = 0; i < addressesToBePatched.size(); i++) {
            instructions.set("END", (short) END, addressesToBePatched.get(i));
        }
    }
}
