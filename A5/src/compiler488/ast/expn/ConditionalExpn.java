package compiler488.ast.expn;

import compiler488.ast.type.*;
import compiler488.symbol.*;
import java.util.*;

import compiler488.codegen.Instructions;
import compiler488.runtime.Machine;

/** Represents a conditional expression (i.e., x>0?3:4). */
public class ConditionalExpn extends Expn {
    private Expn condition; // Evaluate this to decide which value to yield.

    private Expn trueValue; // The value is this when the condition is true.

    private Expn falseValue; // Otherwise, the value is this.

    public ConditionalExpn(Expn cond, Expn ifTrue, Expn ifFalse, int line, int column) {
        super(line, column);
        condition = cond;
        trueValue = ifTrue;
        falseValue = ifFalse;
    }

    /** Returns a string that describes the conditional expression. */
    @Override
    public String toString() {
        return "(" + condition + " ? " + trueValue + " : " + falseValue + ")";
    }

    public Expn getCondition() {
        return condition;
    }

    public void setCondition(Expn condition) {
        this.condition = condition;
    }

    public Expn getFalseValue() {
        return falseValue;
    }

    public void setFalseValue(Expn falseValue) {
        this.falseValue = falseValue;
    }

    public Expn getTrueValue() {
        return trueValue;
    }

    public void setTrueValue(Expn trueValue) {
        this.trueValue = trueValue;
    }

    /** Checks the condition of this conditional expression is
     * of Boolean type. Also checks the expressions after ? are of the
     * same type and returns this type as the result type.
     */
    public Type doSemantics(SymbolTable table, List<String> errorMsg, SymbolTable.ScopeType scp) {
        // do semantic analysis for this node
        Type condType = condition.doSemantics(table, errorMsg, null);
        Type trueType = trueValue.doSemantics(table, errorMsg, null);
        Type falseType = falseValue.doSemantics(table, errorMsg, null);

        // S30
        if (condType == null || !(condType instanceof BooleanType))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       this.getLineNumber(),
                                       this.getColumnNumber(),
                                       "S30",
                                       "expected Boolean type condition"));

        // S33
        if (trueType == null || falseType == null ||
            !(trueType.getClass().equals(falseType.getClass())))
            errorMsg.add(String.format("%d:%d: error %s: %s\n",
                                       this.getLineNumber(),
                                       this.getColumnNumber(),
                                       "S33",
                                       "the result expressions in conditional are not the same type"));

        // S24
        // returns the type of trueType
        // if trueType is null, returns type of falseType
        // if both are null, returns null (no way to know the
        // type of the condition expressions, shouldn't just return
        // a random type because the compiler will pass the semantics
        // checks if the randomly selected type is exactly the type expected
        // and fail otherwise - not consistent)
        if (trueType != null) {
            trueType.setLineNumber(this.getLineNumber());
            trueType.setColumnNumber(this.getColumnNumber());
            return trueType;
        }
        else if (falseType != null) {
            falseType.setLineNumber(this.getLineNumber());
            falseType.setColumnNumber(this.getColumnNumber());
            return falseType;
        }
        else
            return null;
    }


    public void doCodeGeneration(Instructions instructions, Deque<Integer> numVars,
                                 SymbolTable table, SymbolTable.ScopeType scp) {
        // PUSH condition
        condition.doCodeGeneration(instructions, numVars, table, null);
        // PUSH F_ADDR
        instructions.add("PUSH", Machine.PUSH);
        int fAddrToFill = instructions.getSize();
        instructions.add("UNDEFINED", Machine.UNDEFINED);
        // BF
        instructions.add("BF", Machine.BF);

        // code to execute true
        trueValue.doCodeGeneration(instructions, numVars, table, null);

        // PUSH T_ADDR
        instructions.add("PUSH", Machine.PUSH);
        int tAddrToFill = instructions.getSize();
        instructions.add("UNDEFINED", Machine.UNDEFINED);

        // BR
        instructions.add("BR", Machine.BR);

        // patch in f_addr
        instructions.set("F_ADDR", (short) instructions.getSize(), fAddrToFill);

        // code to execute false
        falseValue.doCodeGeneration(instructions, numVars, table, null);


        // patch in t_addr
        instructions.set("T_ADDR", (short) instructions.getSize(), tAddrToFill);

    }
}
