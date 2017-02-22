package compiler488.ast.decl;

/**
 * Represents the declaration of a simple variable.
 */
public class ScalarDeclPart extends DeclarationPart {

    public ScalarDeclPart(String name, int line, int column) {
        super(name, line, column);
    }
    
	/**
	 * Returns a string describing the name of the object being
	 * declared.
	 */
	@Override
	public String toString() {
		return name;
	}

    public void doSemantics() {
        // do semantic analysis for this node
    }
}
