package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.expn.Expn;
import compiler488.ast.type.*;
import compiler488.symbol.*;


/**
 * Represents the common parts of loops.
 */
public abstract class LoopingStmt extends Stmt
{
    protected ASTList<Stmt> body ;	  		// body of the loop
    protected Expn condition;          	// Loop condition

	public LoopingStmt(Expn condition, ASTList<Stmt> body, int line, int column) {
		super(line, column);
		this.condition = condition;
		this.body = body;
	}

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(condition);
        children.add(body);
        return children;
    }

	public LoopingStmt(ASTList<Stmt> body, int line, int column) {
		this(null, body, line, column);
	}
	public Expn getCondition() {
		return condition;
	}

	public void setCondition(Expn condition) {
		this.condition = condition;
	}

	public ASTList<Stmt> getBody() {
		return body;
	}

	public void setBody(ASTList<Stmt> body) {
		this.body = body;
	}

    public Type doSemantics(SymbolTable table, List<String> errorMessages) {
        // S30: check that expression is boolean.
        Type conditionType = condition.doSemantics(table, errorMessages);
        if (!(conditionType instanceof BooleanType)) {
            errorMessages.add(String.format("%d:%d: error %s: %s\n",
                              this.getLineNumber(), this.getColumnNumber(),
                              "S30",
                              "type of expression is not boolean"));
        }
        return null;
    }
}
