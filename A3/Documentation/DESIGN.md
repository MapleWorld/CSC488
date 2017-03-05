
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

The symbol table contains several properties:

 - the index of the current scope
 - a stack of SymbolLists
 - a mapping from identifiers to its attribute properties

 The SymbolList keeps track of all previously declared symbols when a new declaration occurs in the current scope.
 It also keeps track the type of current scope - one of {PROGRAM, FUNCTION, PROCEDURE, LOOP, ORDINARY (for if-statments)}.
 We do not differentiate between major and minor scope, they all get handled in the same way.
 The symbol table also keeps track of the index of the current scope, when a new declaration happens, it checks if the 
 identifier has already been declared in the current scope by checking the scope index stored in the identifier mapping.
 If the identifier is declared already, the old value gets pushed to the current SymbolList on the stack.


 Each identifier has a corresponding entry in the symbol table of abstract type
 SymbolTableEntry. Each entry contains attributes of each identifier depending on
 its type. If the identifier is a boolean or integer, it tracked its type. If
 it is a function or procedure, it keeps track of its parameters and return type
 (if it's a function). If it's an array, it keeps track of its bounds and type. 
 These information gets used to check the corresponding rules.


# Semantic Analysis Design:


     Should describe all of the mechanisms that they
     added to the skeleton to implement semantic analysis.
     e.g. type tracking, scope tracking, etc.
     What method did they use to process the AST?


To implement semantic analysis, we had to add type tracking, and scope tracking to the skeleton. We tracked type by adding an entry to the symbol table for each node, and then looking up each reference to a variable in the symbol table. Scopes were also tracked using the symbol table. 

For semantic analysis, we decided to build doSemantics() into east AST class and then recursively processed the tree. doSemantics() required that the symbol table and error messages be passed in, so that the symbol table could be checked and updated, as well as the error messages.

The two goals for our methods were that the semantic analysis of each AST class would only be based on the information associated with its node, which avoids dependency on the structure of the surrounding parts of the AST. The other goal was to provide clear error messages. To do this, we saved the line and column of each error. 



