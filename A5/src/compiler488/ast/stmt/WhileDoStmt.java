package compiler488.ast.stmt;

import java.io.PrintStream;
import java.util.ListIterator;

import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.expn.Expn;

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
}
