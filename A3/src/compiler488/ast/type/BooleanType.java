package compiler488.ast.type;

/**
 * The type of things that may be true or false.
 */
public class BooleanType extends Type {
    public BooleanType(int line, int column) {
        super(line, column);
    }

	/** Returns the string <b>"Boolean"</b>. */
	@Override
	public String toString() {
		return "boolean";
	}

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
