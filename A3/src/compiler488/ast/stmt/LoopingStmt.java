package compiler488.ast.stmt;

import java.util.*;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.expn.Expn;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import compiler488.ast.stmt.Scope;


/**
 * Represents the common parts of loops.
 */
public abstract class LoopingStmt extends Stmt
{
    protected Stmt body;                // body of the loop
    protected Expn condition;          	// Loop condition

    public LoopingStmt(Expn condition, Stmt body, int line, int column) {
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

    public LoopingStmt(Stmt body, int line, int column) {
        this(null, body, line, column);
    }

    public Expn getCondition() {
        return condition;
    }
    
    public void setCondition(Expn condition) {
        this.condition = condition;
    }

    public Stmt getBody() {
        return body;
    }

    public void setBody(Stmt body) {
        this.body = body;
    }

    /** checks semantics of the loop body and loop condition */
    public Type doSemantics(SymbolTable table, List<String> errorMessages, 
                            SymbolTable.ScopeType scp) {
        // S30: check that expression is boolean.
        Type conditionType = condition.doSemantics(table, errorMessages, null);
        if (conditionType == null || !(conditionType instanceof BooleanType)) {
            errorMessages.add(String.format("%d:%d: error %s: %s\n",
                              this.getLineNumber(), this.getColumnNumber(),
                              "S30",
                              "type of expression is not boolean"));
        }

        if (body instanceof Scope)
            body.doSemantics(table, errorMessages, SymbolTable.ScopeType.LOOP);
        else {
            table.startLoopScope();
            body.doSemantics(table, errorMessages, null);
            table.endScope();
        }
        return null;
    }
}
