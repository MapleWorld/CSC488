#!/bin/sh
echo ""
echo "# Testing Passing Tests #"
echo ""

for i in `ls testing/pass/`
do
    echo "Testing: $i"
    ./RUNCOMPILER.sh "testing/pass/$i"
    echo "TEST DONE"
    echo ""
done 

echo ""
echo "All Pass Tests Complete" 
echo ""