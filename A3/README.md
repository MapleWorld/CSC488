Assignment 3, due February 27
# Current progress
* Completed > 95% of the token parsing from file input to class object in csc488.cup file
* Completed > 95% of all the classes in ast folder, but will need to debug and re-design probably
* However, haven't add any testing cases yet

# How it works
Main.java read the input file, parse it to object, and create a AST tree using the AST class

Then the ast tree is passed to Semantic class for further analysis

Semantic class uses symbol table to check and perform the analysis

# Compiler Design
### Main.java
* Execute the semantic class, by calling Semantic.Analyze() method

### Abstract Syntax Tree
  * Needs to be complete first
  * Used by Program, Scope, Stmt, and other base classes needed

### Symbol Table

### Semantic Analysis & Code Generation
There are several choices for implementing semantic analysis and code generation.

1. Implement a Visitor pattern with double dispatch as described in the course text book.

2. Build semantic analysis and code generation into the AST classes. For example, add member functions doSemantics and doCodeGen to each of the AST classes. Most of these member functions will be simple and easy to implement. If you are comfortable with recursive tree processing, this is probably the least effort approach.

3. Build a separate class that does the tree walk starting from the root of the AST. This approach might require changing some of the AST fields from private to public. Skeleton classes for this approach are provided as a part of the software packages for Assignments 3 .. 5, but it is your choice whether you use them.
