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

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(arguments);
        return children;
    }

	/** Returns a string describing the procedure call. */
	@Override
	public String toString() {
		if (arguments!=null)
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

    public Type doSemantics(SymbolTable table, List<String> errorMessages) {
        SymbolTableEntry entry = table.getEntry(name);
        if (entry == null) {
            errorMessages.add(String.format("%d:%d: error: %s %s %s\n",
                              this.getLineNumber(), this.getColumnNumber(),
                              "referenced procedure", name, "not found."));
            return null;
        }

        if (!(entry.getType() instanceof ProcedureSymbolType)) {
            errorMessages.add(String.format("%d:%d: error: %s %s\n",
                                    this.getLineNumber(), this.getColumnNumber(),
                                    name, "is not a function."));
            return null;
        }

        ProcedureSymbolType procedureType = (ProcedureSymbolType) entry.getType();

        // TODO: Check S42 and S43.
        if (arguments == null) {
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
                if (!((neededArgs.getList().get(i).doSemantics(table, errorMessages) instanceof IntegerType && givenArgs.getList().get(i).doSemantics(table, errorMessages) instanceof IntegerType) ||
                      (neededArgs.getList().get(i).doSemantics(table, errorMessages) instanceof BooleanType && givenArgs.getList().get(i).doSemantics(table, errorMessages) instanceof BooleanType))) {
                        errorMessages.add(String.format("%d:%d: error: %s %s\n",
                                neededArgs.getList().get(i).getLineNumber(), neededArgs.getList().get(i).getColumnNumber(),
                                "Parameter types mismatched for procedure",
                                name));
                        return null;
                    }
            }
        }

        return null;
    }
}
