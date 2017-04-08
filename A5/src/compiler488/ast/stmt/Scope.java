package compiler488.ast.stmt;

import java.io.PrintStream;
import java.util.*;

import compiler488.ast.AST;
import compiler488.ast.ASTList;
import compiler488.ast.Indentable;
import compiler488.ast.decl.Declaration;
import compiler488.ast.type.*;
import compiler488.symbol.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

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

    /** Does code generation on this Scope */
    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars, 
                                 SymbolTable table, SymbolTable.ScopeType scpType) {
        // This is used for determining lexical level for decalarations(function/procedure) and other minor scopes
        // The procedure/function entrance and exit code will be emitted when the procedure/function gets called
        if (scpType == SymbolTable.ScopeType.LOOP)
            table.startLoopScope();
        else
            table.startScope(scpType);
        this.currScpType = scpType;
        if (scpType == SymbolTable.ScopeType.PROGRAM) {
            short lexlev = (short) table.getLexicalLevel();
            instructions.add("ADDR", Machine.ADDR);
            instructions.add(null, lexlev);
            instructions.add(null, (short) 0);
            instructions.add("PUSHMT", Machine.PUSHMT);
            instructions.add("SETD", Machine.SETD);
            instructions.add(null, (short) table.getLexicalLevel());
            this.doCodeGenChildren(instructions, numVars, table);
            instructions.add("SETD", Machine.SETD);
            instructions.add(null, (short) table.getLexicalLevel()); 
        }
        else
            this.doCodeGenChildren(instructions, numVars, table);
        table.endScope();
    }

    /** Does code generation on every declaration and statements in this scope */
    public void doCodeGenChildren(Instructions instructions, Deque<Integer> numVars, 
                                  SymbolTable table) {
        boolean majorScope = (currScpType != SymbolTable.ScopeType.LOOP &&
                              currScpType != SymbolTable.ScopeType.ORDINARY);
        // C30, C31 - allocate space for vars in major scope
        if (majorScope) {
            int space = numVars.removeFirst();
            if (space > 0) {
                instructions.add("PUSH", Machine.PUSH);
                instructions.add("UNDEFINED", Machine.UNDEFINED);
                instructions.add("PUSH", Machine.PUSH);
                instructions.add(null, (short) space);
                instructions.add("DUPN", Machine.DUPN);
            }
        }

        LinkedList<Declaration> declList = declarations.getList();
        ListIterator<Declaration> declIterator = declList.listIterator();

        while (declIterator.hasNext())
            (declIterator.next()).doCodeGeneration(instructions, numVars, table, null);

        LinkedList<Stmt> stmtList = statements.getList();
        ListIterator<Stmt> stmtIterator = stmtList.listIterator();

        while (stmtIterator.hasNext())
            (stmtIterator.next()).doCodeGeneration(instructions, numVars, table, this.currScpType);

        // remove allocated space
        if (majorScope) {
            if (currScpType != SymbolTable.ScopeType.PROGRAM) {
                // fill the placeholder issued in return statements with current code address
                List<Integer> routineAddr = table.getRoutineAddr();
                Iterator<Integer> addrIter = routineAddr.iterator();
                short addr = (short) instructions.getSize();
                while (addrIter.hasNext())
                    instructions.set(null, addr, addrIter.next());
            }

            instructions.add("PUSHMT", Machine.PUSHMT);
            instructions.add("ADDR", Machine.ADDR);
            short lexlev = (short) table.getLexicalLevel();
            instructions.add(null, lexlev);
            instructions.add(null, (short) 0);
            instructions.add("SUB", Machine.SUB);
            instructions.add("POPN", Machine.POPN);

            if (currScpType != SymbolTable.ScopeType.PROGRAM) {
                // go back to where the routine call occurs
                instructions.add("SETD", Machine.SETD);
                instructions.add(null, (short) lexlev);
                instructions.add("BR", Machine.BR);
            }
        }
    }
}
