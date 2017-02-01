#!/bin/bash
#Run a single test case
# eg: ./TestSingle.sh empty_scope.488
# eg: ./TestSingle.sh var_declare.488

echo TEST START
./RUNCOMPILER.sh ./tests/failing/$1
echo TEST DONE
