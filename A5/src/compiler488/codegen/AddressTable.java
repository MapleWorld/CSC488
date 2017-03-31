package compiler488.codegen;

import compiler488.runtime.*;
import java.util.*;

public class AddressTable {
    private HashMap<String, Address> addresses;

    public void Initialize() {
        // initialize addresses here
    }

    public void add() {
    }

    private class Address {
        private int lexicalLevel;
        private int orderNumber;
    }
}
