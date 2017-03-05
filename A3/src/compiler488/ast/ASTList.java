package compiler488.ast;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.List;

import compiler488.symbol.*;
import compiler488.ast.type.*;

/**
 * For nodes with an arbitrary number of children.
 */
public class ASTList<E> extends AST {
	/*
	 * Keep the list here. We delegate rather than subclass LinkedList
	 * because Java won't let us override the return type for addLast.
	 */
	private LinkedList<E> ll;

	/**
	 * Create an empty list.
	 */
	public ASTList() {
		this.ll = new LinkedList<E>();
	}

	/**
	 * Create a list with one element.
	 */
	public ASTList(E ast) {
		this.ll = new LinkedList<E>();
		this.ll.addLast(ast);
	}

	/**
	 * The number of elements in the list.
	 */
	public int size() {
		return ll.size();
	}

	public LinkedList<E> getList() {
		return this.ll;
	}

    public List<AST> getChildren() {
        return (List<AST>) getList();
    }

	/**
	 * Append an element to the list, then return the list. This is a
	 * pure-side-effect method, so it doesn't need to return anything.
	 * However, we like the conciseness gained when such methods return the
	 * target object.
	 */
	public ASTList<E> addLast(E ast) {
		ll.addLast(ast);
		return this;
	}

	/**
	 * Ask each element of the list to print itself using
	 * <b>printOn(out,depth)</b>.  This should only be used when the
	 * elements are typically printed on seperate lines, otherwise they may
	 * not implement <b>printOn</b>. If the list is empty, print
	 * <b>&gt;&gt;empty&lt;&lt;</b> follwed by a new-line.
	 *
	 * @param out
	 *            Where to print the list.
	 * @param depth
	 *            How much indentation to use while printing.
	 */
	public void printOnSeperateLines(PrintStream out, int depth) {
		ListIterator<E> iterator = ll.listIterator();
		if (iterator.hasNext()){
			while (iterator.hasNext()){
				((Indentable) iterator.next()).printOn(out, depth);
				out.print('\n');
			}
		} else{
			Indentable.printIndentOn(out, depth, ">>empty<<\n");
		}
	}

	/**
	 * Return the concatenation of the strings obtained by sending
	 * <b>toString</b> to each element.
	 */
	@Override
	public String toString() {
		if (0 == ll.size())
			return "";
		else {

			ListIterator<E> iterator = ll.listIterator();
			E temp = iterator.next();
			StringBuffer result = new StringBuffer(temp.toString());
			while (iterator.hasNext())
				result.append(", " + iterator.next());

			return result.toString();
		}
	}

        public Type doSemantics(SymbolTable table, List<String> errorMessages, SymbolTable.ScopeType scp) {
        List<AST> children = getChildren();
        for (int i = 0; i < children.size(); i++) {
            System.out.println(children.get(i).getClass());
            children.get(i).doSemantics(table, errorMessages, null);
        }
        return null;
    }
}
