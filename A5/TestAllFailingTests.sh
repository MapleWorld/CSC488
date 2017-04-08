#!/bin/sh
echo ""
echo "# Testing Failing Tests #"
echo ""

for i in `ls testing/fail`
do
    echo "Testing: $i"
    ./RUNCOMPILER.sh "testing/fail/$i"
    echo "TEST DONE"
    echo ""
done 

echo ""
echo "All Fail Tests Complete" 
echo ""