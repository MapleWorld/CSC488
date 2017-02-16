package compiler488.ast;

public interface Printable {

	/*
	 * Classes that extend this interface can be used
	 * as arguments to WriteStmt. Don't confuse this
	 * concept with the printing of the
	 * AST itself.
	 */
	
	public void print(String str);

	public void newline();

	public void println(String str);

	public void enterBlock();

	public void exitBlock();
}
