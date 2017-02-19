package compiler488.symbol;

import comiler488.ast.type.Type;

/** Array Symbol Type
 *  A Symbol Type class for an array of Boolean or Integer variables.
 *
 *  @author  <B> zixuan </B>
 */

public class ArraySymbolType extends SymbolType {
    Type type;
    int upperBound;
    int lowerBound;

    public ScalarSymbolType(Type type, int uBound, int lBound) {
        this.type = type;
        upperBound = uBound;
        lowerBound = lBound;
    }

    public ScalarSymbolType(Type type, int uBound) {
        this(type, uBound, 1);
    }

    /** Returns the type of this Array Symbol Type */
    public Type getType() {
        return type;
    }

    /** Returns the lower bound */
    public int getLowerBound() {
        return lowerBound;
    }

    /** Returns the upper bound */
    public int getUpperBound() {
        return upperBound;
    }
}
