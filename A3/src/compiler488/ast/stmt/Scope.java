package compiler488.ast.stmt;

import java.io.PrintStream;
import java.util.*;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.decl.Declaration;
import compiler488.ast.type.*;
import compiler488.symbol.*;

/**
 * Represents the declarations and instructions of a scope construct.
 */
public class Scope extends Stmt {
    private ASTList<Declaration> declarations; // The declarations at the top.
    
    private ASTList<Stmt> statements; // The statements to execute.
    private SymbolTable.ScopeType currScpType; // this scope's type


    public Scope() {
        declarations = new ASTList<Declaration>();
        statements = new ASTList<Stmt>();
    }

    public Scope(Scope scope) {
        if (scope.getDeclarations() == null) {
            declarations = new ASTList<Declaration>();
        } else {
            declarations = scope.getDeclarations();
        }

        if (scope.getStatements() == null) {
            statements = new ASTList<Stmt>();
        } else {
            statements = scope.getStatements();
        }
    }

    public Scope(int lineNumber, int columnNumber) {
        declarations = new ASTList<Declaration>();
        statements = new ASTList<Stmt>();
        this.setLineNumber(lineNumber);
        this.setColumnNumber(columnNumber);
    }

    public Scope(ASTList<Stmt> stats, int lineNumber, int columnNumber) {
        this(lineNumber, columnNumber);
        this.statements = stats;
    }

    public Scope(ASTList<Declaration> decls, ASTList<Stmt> stmts, int lineNumber, int columnNumber) {

        if (decls == null) {
            declarations = new ASTList<Declaration>();
        } else {
            declarations = decls;
        }

        if (stmts == null) {
            statements = new ASTList<Stmt>();
        } else {
            statements = stmts;
        }

        this.setLineNumber(lineNumber);
        this.setColumnNumber(columnNumber);

    }

    public List<AST> getChildren() {
        LinkedList<AST> children = new LinkedList<AST>();
        children.add(declarations);
        children.add(statements);
        return children;
    }

    /**
     * Print a description of the <b>scope</b> construct.
     *
     * @param out
     *            Where to print the description.
     * @param depth
     *            How much indentation to use while printing.
     */
    @Override
    public void printOn(PrintStream out, int depth) {
        Indentable.printIndentOnLn(out, depth, "Scope");
        Indentable.printIndentOnLn(out, depth, "declarations");
        
        declarations.printOnSeperateLines(out, depth + 1);
        
        out.print('\n');
        
        Indentable.printIndentOnLn(out, depth, "statements");
        
        statements.printOnSeperateLines(out, depth + 1);

        Indentable.printIndentOnLn(out, depth, "End Scope");
    }

    public ASTList<Declaration> getDeclarations() {
        return declarations;
    }

    public ASTList<Stmt> getStatements() {
        return statements;
    }

    public void setDeclarations(ASTList<Declaration> declarations) {
        this.declarations = declarations;
    }

    public void setStatements(ASTList<Stmt> statements) {
        this.statements = statements;
    }

    public void setScpType(SymbolTable.ScopeType scpType) {
        this.currScpType = scpType;
    }

    /** Checks semantics of this Scope */
    public Type doSemantics(SymbolTable table, List<String> errorMessages, SymbolTable.ScopeType scpType) {
        if (scpType == SymbolTable.ScopeType.LOOP)
            table.startLoopScope();
        else
            table.startScope(scpType);
        this.currScpType = scpType;
        this.doSemantics(table, errorMessages);
        table.endScope();
        return null;
    }

     /** Checks semantics on this Scope without adding new scope to table*/
    public Type doSemantics(SymbolTable table, List<String> errorMessages) {
        LinkedList<Declaration> declList = declarations.getList();
        ListIterator<Declaration> declIterator = declList.listIterator();
        while (declIterator.hasNext())
            (declIterator.next()).doSemantics(table, errorMessages, null);
        LinkedList<Stmt> stmtList = statements.getList();
        ListIterator<Stmt> stmtIterator = stmtList.listIterator();
        while (stmtIterator.hasNext()) 
            (stmtIterator.next()).doSemantics(table, errorMessages, this.currScpType);
        return null;
    }
}
