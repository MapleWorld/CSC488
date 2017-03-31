package compiler488.codegen;

import java.util.*;

public class Instructions {
    private List<Short> instructions;
    private List<String> stringInstructions;
    private boolean debug;

    public void Initialize() {
        instructions = new ArrayList<Short>();
        stringInstructions = new ArrayList<String>();
        debug = true;
    }

    public void add(String opname, short opcode) {
        if (opname == null) {
            stringInstructions.add(Integer.toString((int) opcode));
        } else {
            stringInstructions.add(opname);
        }
        instructions.add(opcode);
    }

    public void PrintStringInstructions() {
        for (int i=0; i<stringInstructions.size(); i++) {
            System.out.println(stringInstructions.get(i));
        }
    }

    public void PrintInstructions() {
        for (int i=0; i<instructions.size(); i++) {
            System.out.println(instructions.get(i));
        }
    }
}
