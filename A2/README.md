Submission 
The submitted file (using Markus) should contain:
 
a) A README.A2 file that describes what each team member did for this assignment.

b) Your csc488.cup file. We will use this file to build a parser and run that parser on our test cases.

c) A design document that describes how you designed your csc488.cup file. 
   Explain the issues that arose with the source language reference grammar and how you resolved those issues.

d) A tests directory containing all of the test cases that you used to test your compiler. There should be two subdirectories:
- tests/passing - tests for the correct operation of the parser (legal programs)
- tests/failing - test to demonstrate handling of syntax errors (invalid programs)



This directory contains a minimal compiler skeleton that
is sufficient to develop the programming language grammar
for Assignment 2.

The files in this directory are:

build.xml 		an ANT build script for building the scanner, 
			parser and the driver program.
			Use:
			  'ant help' to get the list of targets
			  'ant gettools' to download JCup and JFlex DO THIS FIRST
			  'ant compile' or 'ant compiler488' to build everything
			  'ant dist' to create a run-time Jar
			
bin			Binary directory, all compiler class files go here

doc			Documentation directory
doc/javadoc		Javadoc for the compiler skeleton

dist			Distribution directory.  Holds  compiler488.jar file
			produced by 'ant dist'

lib			Library directory (see ant gettools)
			After 'ant gettools' it contains a local copy of the
			libraries required to build the scanner and parser.

compiler488/compiler

	Compiler488.java	a very simple driver program that invokes
				the scanner/parser on a file.

compiler488/parser

	csc488.cup		An input file for the JavaCUP parser generator.
				There's space at the end for you to add your
				grammar for Assignment 2.

	csc488.flex	An input file for the JFlex scanner generator.
			The file as provided is a correct scanner for the
			project language.  You should NOT change this file.

To run the test driver first build a distribution using 'ant dist',
then run using 'java -jar dist/compiler488.jar  inputFile'
A shell script  RUNCOMPILER.sh  that does this has been provided.
