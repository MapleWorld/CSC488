package compiler488.symbol;

/** Symbol Table Entry
 *  A general Symbol Table Entry class.
 *
 *  @author  <B> zixuan </B>
 */

public class SymbolTableEntry {
    private SymbolType type;

    /** Symbol Table Entry constructor */
    public SymbolTableEntry(SymbolType type) {
        this.type = type;
    }

    /** Returns the type of this Symbol Table Entry */
    public SymbolType getType() {
        return type;
    }
}
