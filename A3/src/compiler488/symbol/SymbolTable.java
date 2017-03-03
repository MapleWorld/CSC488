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
 *  @author  <B> zixuan </B>
 */

public class SymbolTable {

    /** String used by Main to print symbol table
     *  version information.
     */
    public final static String version = "Winter 2017" ;

    public enum ScopeType {
        PROGRAM, FUNCTION, PROCEDURE, ORDINARY, LOOP;
    }

    // current scope
    private int scopeIndex;
    // to keep track of symbols in outer scopes
    private ArrayDeque<SymbolList> stack;
    // all symbols in the program
    private HashMap<String, SymbolTableEntry> table;

    /** Symbol Table  constructor
     *  Create and initialize a symbol table
     */
    public SymbolTable() {
        scopeIndex = 0;
        stack = new ArrayDeque<SymbolList>();
        table = new HashMap<String, SymbolTableEntry>();
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
        // ensures that indent is not already declared under current scope
        if (oldEntry != null && oldEntry.getScope() == scopeIndex)
            return false;
        entry.setScope(scopeIndex);
        table.put(ident, entry);
        stack.peek().add(ident, oldEntry);
        return true;
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
    public void startScope(ScopeType newScope) {
        scopeIndex++;
        stack.push(new SymbolList(newScope));
    }

    /**
     * Adds a new scope to this Symbol Table
     */
    public void startFunctionScope(Type returnType) {
        scopeIndex++;
        stack.push(new SymbolList(ScopeType.FUNCTION, returnType));
    }

    /**
     * Removes the current scope from this Symbol Table
     */
    public void endScope() {
        stack.pop().updateSymbols();
        scopeIndex--;
    }

    /**
     * Returns the current Scope Type.
     */
    public ScopeType getScopeType() {
        return stack.peek().getScopeType();
    }

    /** SymbolList
     *  This class keeps track of all previously declared symbols.
     */
    private class SymbolList {
        private ScopeType sType;
        private Type returnType = null;
        private LinkedList<Symbol> symbols;

        /** SymbolList constructor */
        public SymbolList(ScopeType sType) {
            this.sType = sType;
            symbols = new LinkedList<Symbol> ();
        }

        public SymbolList(ScopeType sType, Type returnType) {
            this.returnType = returnType;
            this.sType = sType;
            symbols = new LinkedList<Symbol> ();
        }

        /** Returns the Scope Type of this Symbol List */
        public ScopeType getScopeType() {
            return sType;
        }

        /** Returns the return Type of this Symbol List */
        public Type getReturnType() {
            return returnType;
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
