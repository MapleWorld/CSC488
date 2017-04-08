#!/bin/sh
echo ""
echo "# Testing Passing Tests #"
echo ""

for i in `ls tests/pass/`
do
    echo "Testing: $i"
    ./RUNCOMPILER.sh "tests/pass/$i"
    echo "TEST DONE"
    echo ""
done 

echo ""
echo "All Pass Tests Complete" 
echo ""