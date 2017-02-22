package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.Printable;

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

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(outputs);
        return children;
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

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
