package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.expn.*;
import compiler488.symbol.*;
import compiler488.ast.type.*;

/**
 * Represents the command to exit from a loop.
 */

public class ExitStmt extends Stmt {

	// condition for 'exit when'
	private Expn condition = null;
	private Integer level = -1;

	public ExitStmt(Expn cond, Integer level, int line, int column) {
		super(line, column);
		condition = cond;
		this.level = level;
	}

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(condition);
        return children;
    }

	/**
	 * Returns the string <b>"exit"</b> or <b>"exit when e"</b>" or
	 * <b>"exit"</b> level or <b>"exit"</b> level when e
	 */
	@Override
	public String toString() {
		String stmt = "exit ";
		if (level >= 0)
			stmt = stmt + level + " ";
		if (condition != null)
			stmt = stmt + "when " + condition + " ";
		return stmt;
	}

	public Expn getCondition() {
		return condition;
	}

	public void setCondition(Expn condition) {
		this.condition = condition;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

    public Type doSemantics(SymbolTable table, List<String> errorMessages, SymbolTable.ScopeType scpType) {
        // TODO: Check S50.
        // S50: Exit only when in a loop.
        SymbolTable.ScopeType currentScope = table.getScopeType();
        if (currentScope != SymbolTable.ScopeType.LOOP) {
            errorMessages.add(String.format("%d:%d: error %s: %s\n",
                              this.getLineNumber(), this.getColumnNumber(),
                              "S50",
                              "Exit statement is outside a loop"));
        }

        // TODO:
        // S53: Check that integer is greater than 0 and <= number of containing loops.

        // TODO: Check S30.
        // S30: Check expression is boolean.
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
