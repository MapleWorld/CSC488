package compiler488.ast.expn;

import compiler488.ast.ASTList;
import compiler488.ast.type.*;
import compiler488.symbol.*;
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

    public Type doSemantics(SymbolTable table, LinkedList<String> errMsg) {
        //   S44,

        SymbolTableEntry entry = table.getEntry(ident);

        if (entry == null) {
            errMsg.add(String.format("%d:%d: error: %s %s %s\n",
                                    this.getLineNumber(), this.getColumnNumber(),
                                    "referenced function",
                                    this.ident,
                                    "not found."));
            return null;
        }

        if (!(entry.getType() instanceof FunctionSymbolType)) {
            errMsg.add(String.format("%d:%d: error: %s %s\n",
                                    this.getLineNumber(), this.getColumnNumber(),
                                    this.ident,
                                    "is not a function."));
            return null;
        }

        FunctionSymbolType symbol = (FunctionSymbolType) entry.getType();
        if (symbol.getParamCount() == 0) { //S42
            

            // S28
            if (symbol.getReturnType() instanceof BooleanType)
                return new BooleanType(this.getLineNumber(), this.getColumnNumber());
            else if (symbol.getReturnType() instanceof IntegerType)
                return new IntegerType(this.getLineNumber(), this.getColumnNumber());

        } else if (symbol.getParamCount() == this.getArguments().size()) { // S43

            // Ensure argument types match
            ASTList<Expn> givenArgs = symbol.getArguments();
            ASTList<Expn> neededArgs = this.getArguments();
            for (int i = 0; i < symbol.getParamCount(); i++) {

                if (!((neededArgs.getList().get(i).doSemantics(table, errMsg) instanceof IntegerType && givenArgs.getList().get(i).doSemantics(table, errMsg) instanceof IntegerType) ||
                    (neededArgs.getList().get(i).doSemantics(table, errMsg) instanceof BooleanType && givenArgs.getList().get(i).doSemantics(table, errMsg) instanceof BooleanType))) {
                        errMsg.add(String.format("%d:%d: error: %s %s\n",
                                neededArgs.getList().get(i).getLineNumber(), neededArgs.getList().get(i).getColumnNumber(),
                                "Parameter types mismatched for function",
                                this.ident));
                        return null;
                    }

            }

            // S28
            if (symbol.getReturnType() instanceof BooleanType)
                return new BooleanType(this.getLineNumber(), this.getColumnNumber());
            else if (symbol.getReturnType() instanceof IntegerType)
                return new IntegerType(this.getLineNumber(), this.getColumnNumber());
        } else {
            errMsg.add(String.format("%d:%d: error: %s %s\n",
                                    this.getLineNumber(), this.getColumnNumber(),
                                    "Invalid number of parameters for function",
                                    this.ident));
            return null;
        }

        return null;

    }

}
