#! /bin/sh

for f in sem*.488
do
    echo "$f - $(head -n 1 $f)"
done