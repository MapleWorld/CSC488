package compiler488.ast.type;

/**
 * The type of things that may be true or false.
 */
public class ScalarType extends Type {
    
    public ScalarType(int line, int column) {
        super(line, column);
    }
    
	/** Returns the string <b>"Boolean"</b>. */
	@Override
	public String toString() {
		return "scalar";
	}

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
