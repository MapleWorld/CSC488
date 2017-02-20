Assignment 3, due February 27
# Current progress
* Completed > 95% of the token parsing from file input to class object in csc488.cup file
* Completed > 95% of all the classes in ast folder, but will need to debug and re-design probably
* Completed ~ 90% of the symbol table (not checked - may have some silly syntax errors)
* However, haven't add any testing cases yet

# How it works
Main.java read the input file, parse it to object, and create a AST tree using the AST class

Then the ast tree is passed to Semantic class for further analysis

Semantic class uses symbol table to check and perform the analysis

# Compiler Design
### Main.java
* Parse the file content
* Execute the semantic class

### Abstract Syntax Tree
  * Needs to be complete first
  * Used by Program, Scope, Stmt, and other base classes needed

### Symbol Table
  * Used a hash table to keep track of all symbols that are visible to the current scope
  * Used a stack to maintain the information about the current scope type and the symbols in previous scopes that are shadowed by the current scope
  * When the current scope is exited, the symbol information stored in the stack will be restored to the symbol table
    
### Semantic Analysis & Code Generation
There are several choices for implementing semantic analysis and code generation.

1. Implement a Visitor pattern with double dispatch as described in the course text book.

2. Build semantic analysis and code generation into the AST classes. For example, add member functions doSemantics and doCodeGen to each of the AST classes. Most of these member functions will be simple and easy to implement. If you are comfortable with recursive tree processing, this is probably the least effort approach.

3. Build a separate class that does the tree walk starting from the root of the AST. This approach might require changing some of the AST fields from private to public. Skeleton classes for this approach are provided as a part of the software packages for Assignments 3 .. 5, but it is your choice whether you use them.
