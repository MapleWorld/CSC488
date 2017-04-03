package compiler488.ast.stmt;

import compiler488.ast.Indentable;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;
import compiler488.codegen.Instructions;


/**
 * A placeholder for statements.
 */
public class Stmt extends Indentable {
    public Stmt() {
        super();
    }

    public Stmt(int line, int column) {
        super(line, column);
    }

    public Type doSemantics(SymbolTable table, List<String> errorMsg, SymbolTable.ScopeType scp) {
        return null;
    }
}
