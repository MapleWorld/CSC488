package compiler488.ast.stmt;

import java.io.PrintStream;

import compiler488.ast.Indentable;
import compiler488.ast.expn.Expn;

/**
 * The command to return from a function or procedure.
 */
public class ReturnStmt extends Stmt {
	// The value to be returned by a function.
	private Expn value = null;

	public ReturnStmt(Expn value, int line, int column) {
		super(line, column);
		this.value = value;
	}

	/**
	 * Construct a procedure <code>return</code> statement (with no return
	 * value)
	 */
	public ReturnStmt(int line, int column) {
		this(null, line, column);
	}

	/**
	 * Print <b>return</b> or <b>return with </b> expression on a line, by itself.
	 *
	 * @param out
	 *            Where to print.
	 * @param depth
	 *            How much indentation to use while printing.
	 */
	@Override
	public void printOn(PrintStream out, int depth) {
		Indentable.printIndentOn(out, depth);
		if (value == null)
			out.println("return ");
		else
			out.println("return with " + value );
	}

	public Expn getValue() {
		return value;
	}

	public void setValue(Expn value) {
		this.value = value;
	}
}
