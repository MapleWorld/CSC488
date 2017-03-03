package compiler488.symbol;

import compiler488.ast.type.*;
import compiler488.ast.expn.*;
import compiler488.ast.*;

/** Function Symbol Type
 *  A Symbol Type class for a function.
 *
 *  @author  <B> zixuan </B>
 */

public class FunctionSymbolType extends SymbolType {
    Type returnType;
    int paramCount;
    ASTList<Expn> arguments;

    public FunctionSymbolType(Type returnType, ASTList<Expn> arguments) {
        this.returnType = returnType;
        this.arguments = arguments;
        this.paramCount = arguments.size();
    }

    /** Returns the type of this Function Symbol Type */
    public Type getReturnType() {
        return this.returnType;
    }

    /** Increases the parameter count by 1. */
    public void addParameter() {
        this.paramCount++;
    }

    /** Returns the parameter count. */
    public int getParamCount() {
        return this.paramCount;
    }

    /** Returns the arguments. */
    public ASTList<Expn> getArguments() {
        return this.arguments;
    }
}
