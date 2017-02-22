package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.Readable;

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

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
