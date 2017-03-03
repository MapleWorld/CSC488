package compiler488.ast.decl;

import java.io.PrintStream;
import java.util.List;

import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.type.Type;
import compiler488.ast.type.*;
import compiler488.symbol.*;

/**
 * Holds the declaration of multiple elements.
 */
public class MultiDeclarations extends Declaration {
	/* The elements being declared */
	private ASTList<DeclarationPart> elements;

	public MultiDeclarations(Type type, ASTList<DeclarationPart> elements, int line, int column) {
		super(null, type, line, column);
		this.elements = elements;
	}

	/**
	 * Returns a string that describes the array.
	 */
	@Override
	public String toString() {
		return  " : " + type ;
	}

	/**
	 * Print the multiple declarations of the same type.
	 *
	 * @param out
	 *            Where to print the description.
	 * @param depth
	 *            How much indentation to use while printing.
	 */
	@Override
	public void printOn(PrintStream out, int depth) {
		out.println(elements);
		Indentable.printIndentOn (out, depth, this + " ");
	}


	public ASTList<DeclarationPart> getElements() {
		return elements;
	}

	public void setElements(ASTList<DeclarationPart> elements) {
		this.elements = elements;
	}

    public Type doSemantics(SymbolTable table, List<String> errorMessages) {
        // TODO: S47: Associate type with variables
        // elements.doSemantics();
        return null;
    }
}
