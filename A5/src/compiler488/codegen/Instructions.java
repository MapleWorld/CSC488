package compiler488.codegen;

import compiler488.runtime.*;
import java.util.*;

/** This Class is used to keep track of all the instructions */
public class Instructions {
    /** the actual instructions to the memory */
    private List<Short> instructions;
    /** the string representation of the instructions added */
    private List<String> stringInstructions;
 
    public void Initialize() {
        instructions = new ArrayList<Short>();
        stringInstructions = new ArrayList<String>();
    }

    /** Adds opcode to instructions and opname as its string rep */
    public void add(String opname, short opcode) {
        /** if null, use the actual integer value as the string rep of opcode */
        if (opname == null) {
            stringInstructions.add(Integer.toString((int) opcode));
        } else {
            stringInstructions.add(opname);
        }
        instructions.add(opcode);
    }

    /** Outputs the string representation of this Instructions */
    public void PrintStringInstructions() {
        for (int i=0; i<stringInstructions.size(); i++) {
            System.out.println(stringInstructions.get(i));
        }
    }

    /** write the actual instruction to memory */
    public void WriteToMemory() {
        for (int i = 0; i < instructions.size(); i++) {
            Short opcode = instructions.get(i);
            try {
                Machine.writeMemory((short) i, opcode);
            } catch (MemoryAddressException e) {
			    System.err.println("Exception during WriteToMemory: " + e.getMessage());
            }
        }
    }

    /** Returns the size of instructions */
    public short getSize() {
        return (short)instructions.size();
    }

    /** Replaces the instructions with opcode and opname at index */
    public void set(String opname, short opcode, int index) {
        /** if null, use the actual integer value as the string rep of opcode */
        if (opname == null) {
            stringInstructions.set(index, Integer.toString((int) opcode));
        } else {
            stringInstructions.set(index, opname);
        }
        instructions.set(index, opcode);
    }
}
