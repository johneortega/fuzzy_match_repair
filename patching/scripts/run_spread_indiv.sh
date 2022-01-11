#!/bin/bash
# script to measure the numbers from Examples
filename=$1
num_fms_segments=`grep '(S)-- did not meet threshold, but should be counted in overall error' $filename|wc|awk '{print $1}'`

grep -B 1 "Example\ #" $filename > file1
sed '/--/d' ./file1 > file2
sed '/Example/d' ./file2 > file3
tail -n 1 $filename >> file3
awk '{print $1,$2,$1/$2}' file3

#rel_err=`echo "$error_words / $total_words" | bc -l`
#rel_err=`printf "%.4f\n" ${rel_err}`

#echo "${rel_err}"

rm file1 file2 file3 file4 file5

