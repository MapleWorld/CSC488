package compiler488.symbol;

import compiler488.ast.decl.*;
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
    ASTList<ScalarDecl> arguments;
    int startAddr;

    public FunctionSymbolType(Type returnType, ASTList<ScalarDecl> arguments) {
        this.returnType = returnType;
        this.arguments = arguments;
        this.paramCount = arguments.size();
    }

    /** Returns the type of this Function Symbol Type */
    public Type getReturnType() {
        return this.returnType;
    }

    /** Returns the parameter count. */
    public int getParamCount() {
        return this.paramCount;
    }

    /** Returns the arguments. */
    public ASTList<ScalarDecl> getArguments() {
        return this.arguments;
    }

    /** Sets the function code's starting addr to addr */
    public void setStartAddr(int addr) {
        this.startAddr = addr;
    }

    /** Returns the function code's starting addr */
    public int getStartAddr() {
        return this.startAddr;
    }
}
