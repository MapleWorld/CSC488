package compiler488.symbol;

/** Procedure Symbol Type
 *  A Symbol Type class for a procedure.
 *
 *  @author  <B> zixuan </B>
 */

public class ProcedureSymbolType extends SymbolType {
    int paramCount;

    public ProcedureSymbolType() {
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
}
