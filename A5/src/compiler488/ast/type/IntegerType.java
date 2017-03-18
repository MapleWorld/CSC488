package compiler488.ast.type;

/**
 * Used to declare objects that yield integers.
 */
public class IntegerType extends Type {
    public IntegerType(int line, int column) {
        super(line, column);
    }

	/** Returns the string <b>"Integer"</b>. */
	@Override
	public String toString() {
		return "integer";
	}

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
