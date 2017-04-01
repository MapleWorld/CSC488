package compiler488.ast.expn;

import compiler488.ast.AST;
import compiler488.ast.Printable;
import compiler488.symbol.*;
import java.util.*;
import compiler488.ast.type.*;
import compiler488.runtime.Machine;
import compiler488.codegen.Instructions;

/**
 * A placeholder for all expressions.
 */
public class Expn extends AST implements Printable {
    public Expn(int line, int column) {
        super(line, column);
    }

    @Override
    public void print(String str) {
        // TODO Auto-generated method stub

    }

    @Override
    public void newline() {
        // TODO Auto-generated method stub

    }

    @Override
    public void println(String str) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enterBlock() {
        // TODO Auto-generated method stub

    }

    @Override
    public void exitBlock() {
        // TODO Auto-generated method stub

    }

    @Override
    public Type doSemantics(SymbolTable table, List<String> errorMsg, 
                            SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
		return null;
    }

    @Override
    public void doCodeGenerationForWrite(Instructions instructions, Deque<Integer> numVars, SymbolTable table) {
    }
}
