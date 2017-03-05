package compiler488.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.decl.*;
import compiler488.ast.expn.Expn;
import compiler488.ast.type.*;
import compiler488.symbol.*;

/**
 * Represents calling a procedure.
 */
public class ProcedureCallStmt extends Stmt {

    private String name; // The name of the procedure being called.
    private ASTList<Expn> arguments; // The arguments passed to the procedure.
    
    public ProcedureCallStmt(String name, ASTList<Expn> arguments, int line, int column) {
        super(line, column);
        this.name = name;
        this.arguments = arguments;
    }
    
    public ProcedureCallStmt(String name, int line, int column) {
        this(name, new ASTList<Expn>(),line, column);
    }
    
    /** Returns a string describing the procedure call. */
    @Override
    public String toString() {
        if (arguments.size() != 0)
            return "Procedure call: " + name + " (" + arguments + ")";
        else
            return "Procedure call: " + name + " ";
    }

    public ASTList<Expn> getArguments() {
        return arguments;
    }
    
    public void setArguments(ASTList<Expn> args) {
        this.arguments = args;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /** Semantics Checks for this procedure call */
    public Type doSemantics(SymbolTable table, List<String> errorMessages, SymbolTable.ScopeType scp) {
        SymbolTableEntry entry = table.getEntry(name);
        if (entry == null) {
            errorMessages.add(String.format("%d:%d: error %s: %s %s %s\n",
                                            this.getLineNumber(), 
                                            this.getColumnNumber(),
                                            "S41",
                                            "referenced procedure", name, 
                                            "not found"));
            return null;
        }

        if (!(entry.getType() instanceof ProcedureSymbolType)) {
            errorMessages.add(String.format("%d:%d: error %s: %s %s\n",
                                            this.getLineNumber(), 
                                            this.getColumnNumber(),
                                            "S41",
                                            name, "is not a procedure"));
            return null;
        }

        ProcedureSymbolType procedureType = (ProcedureSymbolType) entry.getType();

        if (arguments.size() == 0) {
            // S42: Check that the function or procedure has no parameters.
            if (procedureType.getParamCount() != 0) {
                errorMessages.add(String.format("%d:%d: error %s: %s\n",
                              this.getLineNumber(), this.getColumnNumber(),
                              "S42",
                              "Nullary procedure called with parameters."));
            }
        } else {
            // S43: Check that the number of arguments is equal to the number of
            // formal parameters.
            if (procedureType.getParamCount() != arguments.size()) {
                errorMessages.add(String.format("%d:%d: error %s: %s\n",
                              this.getLineNumber(), this.getColumnNumber(),
                              "S43",
                              "Procedure is called with incorrect number of parameters."));
                return null;
            }

            // Check that the argument types match.
            ASTList<Expn> givenArgs = this.getArguments();
            ASTList<ScalarDecl> neededArgs = procedureType.getArguments();
            for (int i = 0; i < procedureType.getParamCount(); i++) {
                if (!((neededArgs.getList().get(i).getType() instanceof IntegerType && 
                       givenArgs.getList().get(i).doSemantics(table, errorMessages, null) instanceof IntegerType) ||
                      (neededArgs.getList().get(i).getType() instanceof BooleanType && 
                       givenArgs.getList().get(i).doSemantics(table, errorMessages, null) instanceof BooleanType))) {
                    errorMessages.add(String.format("%d:%d: error %s: %s %s\n",
                                                    neededArgs.getList().get(i).getLineNumber(), 
                                                    neededArgs.getList().get(i).getColumnNumber(),
                                                    "S36",
                                                    "Parameter types mismatched for procedure",
                                                    name));
                    return null;
                }
            }
        }

        return null;
    }
}
