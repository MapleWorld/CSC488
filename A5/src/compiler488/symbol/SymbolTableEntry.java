package compiler488.symbol;

/** Symbol Table Entry
 *  A general Symbol Table Entry class.
 *
 *  @author  <B> zixuan </B>
 */

public class SymbolTableEntry {
    private SymbolType type;
    private int scope;
    private int lexicalLevel;
    private int orderNumber;

    /** Symbol Table Entry constructor */
    public SymbolTableEntry(SymbolType type) {
        this.type = type;
    }

    /** Returns the type of this Symbol Table Entry */
    public SymbolType getType() {
        return type;
    }

    /** Returns the number of the scope where the symbol is declared */
    public int getScope() {
        return scope;
    }

    /** Sets this Symbol Table Entry's scope as scope */
    public void setScope(int scope) {
        this.scope = scope;
    }

    /** Sets this entry's lexical level and order number to lexicalLevel and orderNumber */
    public void setAddress(int lexicalLevel, int orderNumber) {
        this.lexicalLevel = lexicalLevel;
        this.orderNumber = orderNumber;
    }

    /** Returns this entry's lexical level */
    public int getLexicalLevel() {
        return lexicalLevel;
    }

    /** Returns this entry's order number */
    public int getOrderNumber() {
        return orderNumber;
    }
}
