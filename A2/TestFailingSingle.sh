#!/bin/bash
#Run a single test case
# eg: ./TestFailingSingle.sh 00_empty_program.488

echo TEST START
./RUNCOMPILER.sh ./tests/failing/$1
echo TEST DONE
