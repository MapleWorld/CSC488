package compiler488.ast.expn;

import compiler488.ast.AST;
import compiler488.ast.Printable;
import compiler488.symbol.*;
import java.util.*;
import compiler488.ast.type.*;


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

    public Type doSemantics(SymbolTable table, List<String> errorMsg) {
        // do semantic analysis for this node
		return null;
    }
}
