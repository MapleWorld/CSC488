package compiler488.symbol;

import compiler488.ast.type.Type;

/** Function Symbol Type
 *  A Symbol Type class for a function.
 *
 *  @author  <B> zixuan </B>
 */

public class FunctionSymbolType extends SymbolType {
    Type type;
    int paramCount;

    public FunctionSymbolType(Type returnType) {
        type = returnType;
        paramCount = 0;
    }

    /** Returns the type of this Function Symbol Type */
    public Type getReturnType() {
        return type;
    }

    /** Increases the parameter count by 1. */
    public void addParameter() {
        paramCount++;
    }

    /** Returns the parameter count. */
    public int getParamCount() {
        return paramCount;
    }
}
