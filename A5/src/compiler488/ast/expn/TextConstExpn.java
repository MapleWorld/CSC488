package compiler488.ast.expn;

import compiler488.ast.Printable;
import java.util.*;
import compiler488.runtime.Machine;
import compiler488.codegen.Instructions;

/**
 * Represents a literal text constant.
 */
public class TextConstExpn extends ConstExpn implements Printable {
    private String value; // The value of this literal.

    public TextConstExpn(String value, int line, int column) {
        super(line, column);
        this.value = value;
    }

    /** Returns a description of the literal text constant. */
    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void doCodeGenerationForWrite(Instructions instructions) {
        for (int i = 0; i < value.length(); i++) {
            instructions.add("PUSH", Machine.PUSH);
            instructions.add(null, (short) value.charAt(i));
            instructions.add("PRINTC", Machine.PRINTC);
        }
    }

}
