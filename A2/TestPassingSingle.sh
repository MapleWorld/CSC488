#!/bin/bash
#Run a single test case
# eg: ./TestPassingSingle.sh empty_scope.488

echo TEST START
./RUNCOMPILER.sh ./tests/passing/$1
echo TEST DONE
