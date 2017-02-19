package compiler488.symbol;

import comiler488.ast.type.Type;

/** Scalar Symbol Type
 *  A Symbol Type class for a single Boolean or Integer variable.
 *
 *  @author  <B> zixuan </B>
 */

public class ScalarSymbolType extends SymbolType {
    Type type;
    boolean isParam;

    public ScalarSymbolType(Type type, boolean isParameter) {
        this.type = type;
        isParam = isParameter;
    }

    /** Returns the type of this Scalar Symbol Type */
    public Type getType() {
        return type;
    }

    /** Return true iff this ScalarSymbolType is a parameter */
    public boolean isParameter() {
        return isParam;
    }
}
