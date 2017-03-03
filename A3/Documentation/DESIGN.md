
# AST Design:

The AST was mostly based on the starter code provided. 

All terminals stays what it is or converted to its equivalent object, and all non-terminals generate an AST node. 
For example, the program statement is generated as such:
```
    program ::= scope:s {: 
        RESULT = new Program(s); 
    :};
```

Many non-terminals takes two extra arguments, ileft and iright,
which references to the line and column numbers of the location of the content.

```
variablename
    ::= IDENT:i                                                         {: RESULT = new ScalarDeclPart(i, ileft, iright); :}
    |   IDENT:i L_SQUARE INTCONST:ic R_SQUARE                           {: RESULT = new ArrayDeclPart(i, ic, ileft, iright); :}
    |   IDENT:i L_SQUARE bound:b1 DOT DOT bound:b2 R_SQUARE             {: RESULT = new ArrayDeclPart(i, b1, b2, ileft, iright); :}
    ;
```

Extra Classes:
ReadableExpn.class - To convert expression into readable object

# Symbol Table:


# Semantic Analysis Design:




