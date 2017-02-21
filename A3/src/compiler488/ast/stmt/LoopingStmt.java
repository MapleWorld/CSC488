package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.expn.Expn;

/**
 * Represents the common parts of loops.
 */
public abstract class LoopingStmt extends Stmt
{
    protected ASTList<Stmt> body ;	  		// body of the loop
    protected Expn expn;          	// Loop condition

	public LoopingStmt(Expn expn, ASTList<Stmt> body, int line, int column) {
		super(line, column);
		this.expn = expn;
		this.body = body;
	}

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(expn);
        children.add(body);
        return children;
    }

	public LoopingStmt(ASTList<Stmt> body, int line, int column) {
		this(null, body, line, column);
	}
	public Expn getExpn() {
		return expn;
	}

	public void setExpn(Expn expn) {
		this.expn = expn;
	}

	public ASTList<Stmt> getBody() {
		return body;
	}

	public void setBody(ASTList<Stmt> body) {
		this.body = body;
	}

}
