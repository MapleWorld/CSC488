package compiler488.ast.decl;

import java.io.PrintStream;

import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.stmt.Scope;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;
import compiler488.codegen.Instructions;

/**
 * Represents the parameters and instructions associated with a
 * function or procedure.
 */
public class RoutineBody extends Indentable {
    private ASTList<ScalarDecl> parameters; // The formal parameters of the routine.
    
    private Scope body; // Execute this scope when routine is called.

    /**
     * Print a description of the formal parameters and the scope for this
     * routine.
     * 
     * @param out
     *            Where to print the description.
     * @param depth
     *            How much indentation to use while printing.
     */
    @Override
    public void printOn(PrintStream out, int depth) {
        if (parameters != null)
            out.println("(" + parameters + ")");
        else
            out.println(" ");
        body.printOn(out, depth);
    }
    
    public Scope getBody() {
        return body;
    }

    public void setBody(Scope body) {
        this.body = body;
    }

    public ASTList<ScalarDecl> getParameters() {
        return parameters;
    }
    
    public void setParameters(ASTList<ScalarDecl> parameters) {
        this.parameters = parameters;
    }

    /** Semantics checks on this routine body */
    public Type doSemantics(SymbolTable table, List<String> errorMessages) {
        // do semantic analysis for this node
        LinkedList<ScalarDecl> paramList = parameters.getList();
        ListIterator<ScalarDecl> iterator = paramList.listIterator();
        while (iterator.hasNext())
            (iterator.next()).doSemantics(table, errorMessages);
        
        body.doSemantics(table, errorMessages);
        return null;
    }

    /** Does code generation on parameters and routine body */
    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars, 
                                 SymbolTable table) {
        LinkedList<ScalarDecl> paramList = parameters.getList();
        ListIterator<ScalarDecl> iterator = paramList.listIterator();
        int numParams = 0;
        while (iterator.hasNext()) {
            (iterator.next()).doCodeGeneration(instructions, numVars, table);
            numParams++;
        }

        // numVars includes the parameters; have to make sure parameters are not counted 
        int size = numVars.removeFirst();
        size -= numParams;
        numVars.addFirst(size);

        body.doCodeGenChildren(instructions, numVars, table);
    }
}
