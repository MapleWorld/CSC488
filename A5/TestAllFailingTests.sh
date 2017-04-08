#!/bin/sh
echo ""
echo "# Testing Failing Tests #"
echo ""

for i in `ls tests/fail`
do
    echo "Testing: $i"
    ./RUNCOMPILER.sh "tests/fail/$i"
    echo "TEST DONE"
    echo ""
done 

echo ""
echo "All Fail Tests Complete" 
echo ""