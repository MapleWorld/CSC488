package compiler488.symbol;

import java.io.*;
import java.util.*;
import compiler488.ast.type.Type;

/** Symbol Table
 *  This almost empty class is a framework for implementing
 *  a Symbol Table class for the CSC488S compiler
 *
 *  Each implementation can change/modify/delete this class
 *  as they see fit.
 *
 */

public class SymbolTable {

    /** String used by Main to print symbol table
     *  version information.
     */
    public final static String version = "Winter 2017" ;

    public enum ScopeType {
        PROGRAM, FUNCTION, PROCEDURE, ORDINARY, LOOP;
    }

    // current scope for semantics
    private int scopeIndex;
    // lexical level for code gen
    private int lexicalLevel;
    // to keep track of symbols in outer scopes
    private ArrayDeque<SymbolList> stack;
    // all symbols in the program
    private HashMap<String, SymbolTableEntry> table;
    // total variable sizes in the order of major scopes
    private ArrayDeque<Integer> numVars;


    /** Symbol Table  constructor
     *  Create and initialize a symbol table
     */
    public SymbolTable() {
        scopeIndex = 0;
        lexicalLevel = -1;
        stack = new ArrayDeque<SymbolList>();
        table = new HashMap<String, SymbolTableEntry>();
        numVars = new ArrayDeque<Integer>();
    }

    /** The rest of Symbol Table
     *  Data structures, public and private functions
     *  to implement the Symbol Table
     *  GO HERE.
     */

    /**
     *  Returns true iff successfully adding the given
     *  indent and entry to this Symbol Table.
     */
    public boolean addSymbol(String ident, SymbolTableEntry entry) {
        SymbolTableEntry oldEntry = table.get(ident);
        // ensures that ident is not already declared under current scope
        if (oldEntry != null && oldEntry.getScope() == scopeIndex)
            return false;
        entry.setScope(scopeIndex);

        // set address of identifier
        entry.setAddress(lexicalLevel, stack.peek().getOrderNumber());

        // increment order number by size of identifier
        int size = 0;
        if (entry.getType() instanceof ArraySymbolType) {
            ArraySymbolType arrayEntryType = (ArraySymbolType) entry.getType();
            // language.pdf says upperbound is inclusive
            size = arrayEntryType.getUpperBound() - arrayEntryType.getLowerBound() + 1;
        } else if (entry.getType() instanceof ScalarSymbolType) {
            size = 1;
        }
        stack.peek().updateOrderNumber(size);
 
        table.put(ident, entry);
        stack.peek().add(ident, oldEntry);
        return true;
    }

    /** Returns the total size of variables in each major scope */ 
    public ArrayDeque<Integer> getNumVars() {
        return numVars;
    }

    /** Returns the corresponding Symbol Table Entry of ident,
     *  null if indent is not in this Symbol Table.
     */
    public SymbolTableEntry getEntry(String ident) {
        return table.get(ident);
    }

    /**
     * Adds a new scope to this Symbol Table
     */
    // This is called on every scope except for loop and function.
    public void startScope(ScopeType newScope) {
        // we don't increment the lexical level for minor scopes
        // (since we don't want to create a new activation record)
        if (newScope == ScopeType.ORDINARY) {
            int offset = stack.peek().getOrderNumber();
            stack.push(new SymbolList(newScope, getNumLoop()));
            stack.peek().setOrderNumber(offset);
        } else {
            stack.push(new SymbolList(newScope));
            lexicalLevel++;
        }

        scopeIndex++;
    }

    /**
     * Adds a new scope to this Symbol Table
     */
    public void startLoopScope() {
        int numLoop = 1;
        if (getScopeType() == ScopeType.LOOP)
            numLoop += getNumLoop();

        scopeIndex++;
        // don't increment lexicalLevel because loop is a minor scope
        int offset = stack.peek().getOrderNumber();
        stack.push(new SymbolList(ScopeType.LOOP, numLoop));
        stack.peek().setOrderNumber(offset);
    }

    /**
     * Adds a new scope to this Symbol Table
     */
    public void startFunctionScope(Type returnType) {
        scopeIndex++;
        lexicalLevel++;
        stack.push(new SymbolList(ScopeType.FUNCTION, returnType));
    }

    /**
     * Removes the current scope from this Symbol Table
     */
    public void endScope() {
        // check the scope type of the top of the stack -- only decrement lexical level
        // if it's not a minor scope.
        // TODO
        SymbolList top = stack.pop();
        ScopeType scopetype = top.getScopeType();
        if (scopetype == ScopeType.ORDINARY || scopetype == ScopeType.LOOP) {
            // adds the order number in minor scope to its enclosing scope
            stack.peek().updateOrderNumber(top.getOrderNumber());
        } else {
            lexicalLevel--;
            numVars.addLast(top.getOrderNumber());
        }

        top.updateSymbols();
        scopeIndex--;
    }

    /**
     * Returns the current Scope Type.
     */
    public ScopeType getScopeType() {
        ScopeType scp = stack.peek().getScopeType();
        ArrayDeque<SymbolList> tmp = new ArrayDeque<SymbolList> ();
        while (scp == ScopeType.ORDINARY) {
            tmp.push(stack.pop());
            scp = stack.peek().getScopeType();
        }
        while (tmp.size() > 0)
            stack.push(tmp.pop());
        return scp;
    }

   
    /** Returns the return type of this scope */
    public Type getReturnType() {
        ScopeType scp = stack.peek().getScopeType();
        ArrayDeque<SymbolList> tmp = new ArrayDeque<SymbolList> ();
        while (scp == ScopeType.ORDINARY) {
            tmp.push(stack.pop());
            scp = stack.peek().getScopeType();
        }
        Type retType = stack.peek().getReturnType();
        while (tmp.size() > 0)
            stack.push(tmp.pop());
        return retType;
    }

    /** Returns the number of loops that this scope resides in */
    public int getNumLoop() {
        return stack.peek().getNumLoop();
    }
    
    /** Returns the current lexical level */
    public int getLexicalLevel() {
        return lexicalLevel;
    }

    /** SymbolList
     *  This class keeps track of all previously declared symbols.
     */
    private class SymbolList {
        private ScopeType sType;
        private Type returnType = null;
        private LinkedList<Symbol> symbols;
        private int numLoop = 0;
        // offset from the display[lexicalLevel] to the variable 
        private int orderNumber = 0;

        /** SymbolList constructors */
        public SymbolList(ScopeType sType) {
            this.sType = sType;
            symbols = new LinkedList<Symbol> ();
        }

        public SymbolList(ScopeType sType, Type returnType) {
            this.returnType = returnType;
            this.sType = sType;
            symbols = new LinkedList<Symbol> ();
        }

        public SymbolList(ScopeType sType, int numLoop) {
            this.numLoop = numLoop;
            this.sType = sType;
            symbols = new LinkedList<Symbol> ();
        }

        /** Increments the order number by value */
        public void updateOrderNumber(int value) {
            orderNumber += value;
        }

        /** Returns the order number */
        public int getOrderNumber() {
            return orderNumber;
        }

        /** Sets the order number to value */
        public void setOrderNumber(int value) {
            orderNumber = value;
        }

        /** Returns the Scope Type of this Symbol List */
        public ScopeType getScopeType() {
            return sType;
        }

        /** Returns the return Type of this Symbol List */
        public Type getReturnType() {
            return returnType;
        }

        /** Returns the number of loops that this scope resides in */
        public int getNumLoop() {
            return numLoop;
        }

        /**
         * Adds a new symbol with id and entry
         */
        public void add(String id, SymbolTableEntry entry) {
            symbols.add(new Symbol(id, entry));
        }

        /**
         * Updates the symbol table with symbols in this Symbol List,
         * if a symbol entry is null, remove the entry from the symbol table.
         */
        public void updateSymbols() {
            for (Symbol oldSymbol : symbols) {
                // checks if symbol is only declared under current scope
                if (oldSymbol.getEntry() == null)
                    table.remove(oldSymbol.getIdentifier());
                else
                    table.put(oldSymbol.getIdentifier(), oldSymbol.getEntry());
            }
        }

        /** Symbol
         *  This class is used to keep track of the symbol
         *  in the outer scope when the current scope
         *  uses the same identifier to declare variables.
         */
        private class Symbol {
            private String identifier;
            private SymbolTableEntry entry;

            /** Symbol constructor
             *  Create Symbol with given identifier and entry
             */
            public Symbol(String identifier, SymbolTableEntry entry) {
                this.identifier = identifier;
                this.entry = entry;
            }

            /** Returns identifier of the symbol. */
            public String getIdentifier() {
                return identifier;
            }

            /** Returns the symbol table entry of the symbol. */
            public SymbolTableEntry getEntry() {
                return entry;
            }
        }
    }
}
