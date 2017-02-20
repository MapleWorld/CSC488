package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.expn.Expn;

/**
 * Holds the assignment of an expression to a variable.
 */
public class AssignStmt extends Stmt {
	/*
	 * lval is the location being assigned to, and rval is the value being
	 * assigned.
	 */
	private Expn lval, rval;

	public AssignStmt(Expn lval, Expn rval, int line, int column) {
		super(line, column);
		this.lval = lval;
		this.rval = rval;
	}

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(lval);
        children.add(rval);
        return children;
    }

	/** Returns a string that describes the assignment statement. */
	@Override
	public String toString() {
		return "Assignment: " + lval + " := " + rval;
	}

	public Expn getLval() {
		return lval;
	}

	public void setLval(Expn lval) {
		this.lval = lval;
	}

	public Expn getRval() {
		return rval;
	}

	public void setRval(Expn rval) {
		this.rval = rval;
	}
}
