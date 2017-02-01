#!/bin/sh
echo ""
echo "# Testing Passing Tests #"
echo ""

for i in `ls tests/`
do
    echo "Testing: $i"
    ./RUNCOMPILER.sh "tests/$i"
    echo "TEST DONE"
    echo ""
done 

echo ""
echo "All Tests Complete" 
echo ""