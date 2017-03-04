package compiler488.symbol;

import compiler488.ast.expn.*;
import compiler488.ast.*;

/** Procedure Symbol Type
 *  A Symbol Type class for a procedure.
 *
 *  @author  <B> zixuan </B>
 */

public class ProcedureSymbolType extends SymbolType {
    int paramCount;
    ASTList<Expn> arguments;

    public ProcedureSymbolType(ASTList<Expn> arguments) {
        this.arguments = arguments;
        paramCount = 0;
    }

    /** Increases the parameter count by 1. */
    public void addParameter() {
        paramCount++;
    }

    /** Returns the parameter count. */
    public int getParamCount() {
        return paramCount;
    }

    /** Returns the arguments. */
    public ASTList<Expn> getArguments() {
        return this.arguments;
    }
}
