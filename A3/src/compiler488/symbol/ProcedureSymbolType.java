package compiler488.symbol;

import compiler488.ast.decl.*;
import compiler488.ast.expn.*;
import compiler488.ast.*;

/** Procedure Symbol Type
 *  A Symbol Type class for a procedure.
 *
 *  @author  <B> zixuan </B>
 */

public class ProcedureSymbolType extends SymbolType {
    int paramCount;
    ASTList<ScalarDecl> arguments;

    public ProcedureSymbolType(ASTList<ScalarDecl> arguments) {
        this.arguments = arguments;
        paramCount = arguments.size();
    }

    /** Returns the parameter count. */
    public int getParamCount() {
        return paramCount;
    }

    /** Returns the arguments. */
    public ASTList<ScalarDecl> getArguments() {
        return this.arguments;
    }
}
