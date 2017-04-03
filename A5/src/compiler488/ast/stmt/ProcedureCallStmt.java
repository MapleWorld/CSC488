package compiler488.ast.stmt;

import java.util.*;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.decl.*;
import compiler488.ast.expn.Expn;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

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
            // Check that the argument types match.
            ASTList<Expn> givenArgs = this.getArguments();
            ASTList<ScalarDecl> neededArgs = procedureType.getArguments();
            for (int i = 0; i < Math.min(this.getArguments().size(), procedureType.getParamCount()); i++) {
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
                 }
            }
            
            // S43: Check that the number of arguments is equal to the number of
            // formal parameters.
            if (procedureType.getParamCount() != arguments.size()) {
                errorMessages.add(String.format("%d:%d: error %s: %s\n",
                                                this.getLineNumber(), this.getColumnNumber(),
                                                "S43",
                                                "Procedure is called with incorrect number of parameters."));
            }
            
        }

        return null;
    }

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars, 
                                 SymbolTable table, SymbolTable.ScopeType scpType) {
        SymbolTableEntry entry = table.getEntry(name);
        ProcedureSymbolType procedureType = (ProcedureSymbolType) entry.getType();
        instructions.add("PUSH", Machine.PUSH);
        int indexToFill = instructions.getSize();
        instructions.add("UNDEFINED", Machine.UNDEFINED);
        instructions.add("ADDR", Machine.ADDR);
        // because the procedure name is at its parent's lexical level, we need to add 1
        // when considering things inside the procedure's scope
        short lexlev = (short) (entry.getLexicalLevel() + 1);
        instructions.add(null, lexlev);
        instructions.add(null, (short) 0);
       
        for (int i = 0; i < arguments.size(); i++) {
            arguments.getList().get(i).doCodeGeneration(instructions, numVars, table, null);
        }            
        
        instructions.add("PUSHMT", Machine.PUSHMT);
        instructions.add("PUSH", Machine.PUSH);
        instructions.add(null, (short) arguments.size());
        instructions.add("SUB", Machine.SUB);
        instructions.add("SETD", Machine.SETD);
        instructions.add(null, lexlev);
        instructions.add("PUSH", Machine.PUSH);
        short addr = (short) procedureType.getStartAddr();
        instructions.add(null, addr);
        instructions.add("BR", Machine.BR);

        // patch the address of where to execute the next instruction after the procedure call
        instructions.set(null, (short)instructions.getSize(), indexToFill);
    }    
}
