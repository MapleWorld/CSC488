Assignment 3, due February 27

# How it works
Main.java read the input file, parse it, and create a AST tree using the AST class
Then the ast tree is passed to Semantic class for further analysis
Semantic class uses symbol table to check and perform the analysis

# Compiler Design
### Main.java
* Modifiy program to execute the semantic class, by calling Semantic.Analyze() method

### Abstract Syntax Tree
  * Needs to be complete first
  * Used by Program, Scope, Stmt, and other base classes needed

### Symbol Table

### Semantic Class

1. Analyze method
  * Use a stack to loop through the entire ProgramAST 
  * During each iteration, pop the top node of the stack, perform semantic analysis on it
  * Add any children of the current node to the stack

2. Semantic Analyze
  * Details goes here
