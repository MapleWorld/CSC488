package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;
import compiler488.symbol.*;
import java.util.*;

/**
 * Place holder for all binary expression where both operands must be integer
 * expressions.
 */
public class ArithExpn extends BinaryExpn {
    public final static String OP_PLUS      = "+";
    public final static String OP_MINUS     = "-";
    public final static String OP_TIMES     = "*";
    public final static String OP_DIVIDE    = "/";

    public ArithExpn(String opSymbol, Expn left, Expn right, int line, int column) {
        super(opSymbol, left, right, line, column);

        assert ((opSymbol == OP_PLUS) ||
                (opSymbol == OP_MINUS) ||
                (opSymbol == OP_TIMES) ||
                (opSymbol == OP_DIVIDE));
    }

    /** Checks the semantics of both operands and returns Integer type */
    public Type doSemantics(SymbolTable table, List<String> errorMsg, SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
        Type leftType = left.doSemantics(table, errorMsg, null);
        Type rightType = right.doSemantics(table, errorMsg, null);

        // S31
        if (leftType == null || !(leftType instanceof IntegerType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       this.getLineNumber(),
                                       this.getColumnNumber(),
                                       "S31",
                                       "expected Integer operand"));
        if (rightType == null || !(rightType instanceof IntegerType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       this.getLineNumber(),
                                       this.getColumnNumber(),
                                       "S31",
                                       "expected Integer operand"));

        // S21
        return new IntegerType(this.getLineNumber(), this.getColumnNumber());
    }
    
    /** Adds code to push the constant values and operation on to stack */
    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
            SymbolTable table, SymbolTable.ScopeType scp) {
        
        IntConstExpn left_int = (IntConstExpn) left;
        IntConstExpn right_int = (IntConstExpn) right;
        
        instructions.add("PUSH", Machine.PUSH);
        instructions.add(null, left_int.getValue().shortValue());
        instructions.add("PUSH", Machine.PUSH);
        instructions.add(null, right_int.getValue().shortValue());
        
        if (opSymbol == OP_PLUS) {
            instructions.add("ADD", Machine.ADD);
        } else if (opSymbol == OP_MINUS) {
            instructions.add("SUB", Machine.SUB);
        } else if(opSymbol == OP_TIMES) {
            instructions.add("MUL", Machine.MUL);
        } else if (opSymbol == OP_DIVIDE) {
            instructions.add("DIV", Machine.DIV);
        } else {
            System.out.println("Error invalid arithmetic opSymbol");
        }
    }
}
