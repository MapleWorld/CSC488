package compiler488.ast.expn;

import compiler488.ast.ASTList;
import compiler488.ast.decl.*;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

import java.util.*;

/**
 * Represents a function call with or without arguments.
 */
public class FunctionCallExpn extends Expn {
    private String ident; // The name of the function.

    private ASTList<Expn> arguments; // The arguments passed to the function.

    public FunctionCallExpn(String ident, ASTList<Expn> arguments, int line, int column) {
        super(line, column);
        this.ident = ident;
        this.arguments = arguments;
    }

    /** Returns a string describing the function call. */
    @Override
    public String toString() {
        if (arguments != null) {
            return ident + " (" + arguments + ")";
        } else
            return ident + " ";
    }

    public ASTList<Expn> getArguments() {
        return arguments;
    }

    public void setArguments(ASTList<Expn> args) {
        this.arguments = args;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public Type doSemantics(SymbolTable table, List<String> errMsg,
                            SymbolTable.ScopeType scp) {

        SymbolTableEntry entry = table.getEntry(ident);

        // S40
        if (entry == null) {
            errMsg.add(String.format("%d:%d: error %s: %s %s %s\n",
                                     this.getLineNumber(), this.getColumnNumber(),
                                     "S40",
                                     "referenced function",
                                     this.ident,
                                     "not found."));
            return null;
        }

        if (!(entry.getType() instanceof FunctionSymbolType)) {
            errMsg.add(String.format("%d:%d: error %s: %s %s\n",
                                     this.getLineNumber(), this.getColumnNumber(),
                                     "S40",
                                     this.ident,
                                     "is not a function."));
            return null;
        }

        FunctionSymbolType symbol = (FunctionSymbolType) entry.getType();
        if (symbol.getParamCount() == 0) { //S42
            if (symbol.getParamCount() != this.getArguments().size())
                errMsg.add(String.format("%d:%d: error %s: %s %s\n",
                                         this.getLineNumber(), this.getColumnNumber(),
                                         "S28",
                                         "Invalid number of parameters for function",
                                         this.ident));

            // S28
            if (symbol.getReturnType() instanceof BooleanType)
                return new BooleanType(this.getLineNumber(), this.getColumnNumber());
            else if (symbol.getReturnType() instanceof IntegerType)
                return new IntegerType(this.getLineNumber(), this.getColumnNumber());

        } else { // S43

            // Ensure argument types match, S44, S45, S36
            ASTList<ScalarDecl> neededArgs = symbol.getArguments();
            ASTList<Expn> givenArgs = this.getArguments();

            for (int i = 0; i < Math.min(symbol.getParamCount(), this.getArguments().size()); i++) {

                if (!((neededArgs.getList().get(i).getType() instanceof IntegerType &&
                       givenArgs.getList().get(i).doSemantics(table, errMsg, null) instanceof IntegerType) ||
                      (neededArgs.getList().get(i).getType() instanceof BooleanType &&
                       givenArgs.getList().get(i).doSemantics(table, errMsg, null) instanceof BooleanType))) {
                        errMsg.add(String.format("%d:%d: error %s: %s %s\n",
                                                 neededArgs.getList().get(i).getLineNumber(),
                                                 neededArgs.getList().get(i).getColumnNumber(),
                                                 "S43",
                                                 "Parameter types mismatched for function",
                                                 this.ident));
                }

            }

            if (symbol.getParamCount() != this.getArguments().size())
                errMsg.add(String.format("%d:%d: error %s: %s %s\n",
                                         this.getLineNumber(), this.getColumnNumber(),
                                         "S28",
                                         "Invalid number of parameters for function",
                                         this.ident));

            // S28
            if (symbol.getReturnType() instanceof BooleanType)
                return new BooleanType(this.getLineNumber(), this.getColumnNumber());
            else if (symbol.getReturnType() instanceof IntegerType)
                return new IntegerType(this.getLineNumber(), this.getColumnNumber());
        }

        return null;
    }

    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scpType) {
        instructions.add("PUSH", Machine.PUSH);
        instructions.add("UNDEFINED", Machine.UNDEFINED);

        SymbolTableEntry entry = table.getEntry(ident);
        FunctionSymbolType functionType = (FunctionSymbolType) entry.getType();
        instructions.add("PUSH", Machine.PUSH);
        int indexToFill = instructions.getSize();
        instructions.add("UNDEFINED", Machine.UNDEFINED);
        instructions.add("ADDR", Machine.ADDR);
        // because the function name is at its parent's lexical level, we need to add 1
        // when considering things inside the function's scope
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
        short addr = (short) functionType.getStartAddr();
        instructions.add(null, addr);
        instructions.add("BR", Machine.BR);

        // patch the address of where to execute the next instruction after the function call
        instructions.set(null, (short)instructions.getSize(), indexToFill);
    }
}
