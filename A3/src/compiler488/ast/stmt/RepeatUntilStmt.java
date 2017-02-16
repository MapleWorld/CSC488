package compiler488.ast.stmt;

import java.io.PrintStream;
import java.util.ListIterator;

import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.expn.Expn;

/**
 * Represents a loop in which the exit condition is evaluated after each pass.
 */
public class RepeatUntilStmt extends LoopingStmt {
	
	public RepeatUntilStmt(Expn expn, ASTList<Stmt> body, int line, int column) {
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
		ListIterator<Stmt> iterator = body.getList().listIterator();
		if (iterator.hasNext())
			while (iterator.hasNext())
				iterator.next().printOn(out, depth + 1);
		Indentable.printIndentOnLn(out, depth, " until "  + expn );

	}
}
