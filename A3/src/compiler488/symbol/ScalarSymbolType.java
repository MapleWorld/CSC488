package compiler488.symbol;

import compiler488.ast.type.Type;

/** Scalar Symbol Type
 *  A Symbol Type class for a single Boolean or Integer variable.
 *
 *  @author  <B> zixuan </B>
 */

public class ScalarSymbolType extends SymbolType {
    Type type;

    public ScalarSymbolType(Type type) {
        this.type = type;
    }

    /** Returns the type of this Scalar Symbol Type */
    public Type getType() {
        return type;
    }

}
