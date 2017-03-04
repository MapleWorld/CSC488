package compiler488.ast.decl;

import java.io.PrintStream;

import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.stmt.Scope;
import compiler488.ast.type.Type;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

/**
 * Represents the declaration of a function or procedure.
 */
public class RoutineDecl extends Declaration {
	/*
	 * The formal parameters of the function/procedure and the statements to
	 * execute when the procedure is called.
	 */
	private RoutineBody routineBody = new RoutineBody();

	/**
	 * Construct a function with parameters, and a definition of the body.
	 * @param name Name of the routine
	 * @param type Type returned by the function
	 * @param parameters List of parameters to the routine
	 * @param body Body scope for the routine
	 */
	public RoutineDecl(String name, Type type, ASTList<ScalarDecl> parameters, Scope body, int line, int column) {
		super(name, type, line, column);
		this.routineBody.setParameters(parameters);
		this.routineBody.setBody(body);
	}

	/**
	 * Construct a function with no parameters, and a definition of the body.
	 * @param name Name of the routine
	 * @param type Type returned by the function
	 * @param body Body scope for the routine
	 */
	public RoutineDecl(String name, Type type, Scope body, int line, int column) {
		this(name, type, new ASTList<ScalarDecl>(), body, line, column);
	}

	/**
	 * Construct a procedure with parameters, and a definition of the body.
	 *
	 * @param name Name of the routine
	 * @param parameters List of parameters to the routine
	 * @param body Body scope for the routine
	 */
	public RoutineDecl(String name, ASTList<ScalarDecl> parameters, Scope body, int line, int column) {
		this(name, null, parameters, body, line, column);
		this.routineBody.setParameters(parameters);
		this.routineBody.setBody(body);
	}

	/**
	 * Construct a procedure with no parameters, and a definition of the body.
	 * @param name Name of the routine
	 * @param body Body scope for the routine
	 */
	public RoutineDecl(String name, Scope body, int line, int column) {
		this(name, null, new ASTList<ScalarDecl>(), body, line, column);
	}
	
	/**
	 * Returns a string indicating that this is a function with return type or a
	 * procedure, name, Type parameters, if any, are listed later by routineBody
	 */
	@Override
	public String toString() {
		if (type == null) {
			return " procedure " + name;
		} else {
			return " function " + name + " : " + type;
		}
	}

	/**
	 * Prints a description of the function/procedure.
	 * 
	 * @param out Where to print the description.
	 * @param depth How much indentation to use while printing.
	 */
	@Override
	public void printOn(PrintStream out, int depth) {
		Indentable.printIndentOn(out, depth, this + " ");
		routineBody.printOn(out, depth);
	}

	public RoutineBody getRoutineBody() {
		return routineBody;
	}

	public void setRoutineBody(RoutineBody routineBody) {
		this.routineBody = routineBody;
	}

    public Type doSemantics(SymbolTable table, List<String> errorMsg) {

		if (this.type == null) { // Procedure

			if (this.routineBody.getParameters().size() == 0) { // Procedure without parameters

				// S17
				table.addSymbol(this.name, new SymbolTableEntry(new ProcedureSymbolType(null)));

				// S08
				table.startFunctionScope(this.type);

				// S17, S13
				table.addSymbol(this.name, new SymbolTableEntry(new ProcedureSymbolType()));

				// S09
				table.endScope();

			} else { // Procedure with parameters

				// S08
				table.startFunctionScope(this.type);

				// S18, S13
				table.addSymbol(this.name, new SymbolTableEntry(new ProcedureSymbolType(this.routineBody.getParameters())));

<<<<<<< HEAD
				// S13, isn't this in this.routineBody?

=======
				// S09
				table.endScope();
				
>>>>>>> 28771d0b8a1458cb50115f5e457b9307c2c9c359
			}

		} else { // Function

			if (this.routineBody.getParameters().size() == 0) { // Function without parameters

				// S04
				table.startFunctionScope(this.type);

				// S11, S13
				table.addSymbol(this.name, new SymbolTableEntry(new FunctionSymbolType(this.type, null)));

				// S05
				table.endScope();

			} else { // Function with parameters

				// S04
				table.startFunctionScope(this.type);

				// S12, S13
				table.addSymbol(this.name, new SymbolTableEntry(new FunctionSymbolType(this.type, this.routineBody.getParameters())));

				// S05
				table.endScope();

			}

		}


    }
}
