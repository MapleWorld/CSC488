package compiler488.ast;

import compiler488.ast.expn.Expn;

public class ReadableExpn implements Readable {

    private Expn varExpn;
    
    public ReadableExpn(Expn variable){
        setVarExpn(variable);
    }

    public Expn getVarExpn() {
        return varExpn;
    }
    public void setVarExpn(Expn varExpn) {
        this.varExpn = varExpn;
    }
    
    public String toString() {
        return "" + this.varExpn;
    }

}