#!/bin/bash
# script to measure the numbers from Examples
filename=$1
num_fms_segments=`grep '(S)-- did not meet threshold, but should be counted in overall error' $filename|wc|awk '{print $1}'`

grep -B 1 "Example\ #" $filename > file1
sed '/--/d' ./file1 > file2
sed '/Example/d' ./file2 > file3
tail -n 1 $filename >> file3
num_segments=`wc file3 | awk '{print $1}'`
awk '{print $1}' file3 > file4
awk '{print $2}' file3 > file5

error_words=`awk '{ sum += $1 } END { print sum }' file4`

total_words=`awk '{ sum += $1 } END { print sum }' file5`

words_matched=`expr $total_words - $error_words`

average_seg_len=`echo "$total_words / $num_segments" | bc -l`
average_seg_len=`printf "%.4f\n" ${average_seg_len}`

rel_err=`echo "$error_words / $total_words" | bc -l`
rel_err=`printf "%.4f\n" ${rel_err}`

num_fms_matched=`expr $num_segments - $num_fms_segments`

echo "relative error: ${rel_err}"
echo "total words: ${total_words}"
echo "total words matched: ${words_matched}"
echo "total words not matched: ${error_words}"
echo "total fms matched segments: ${num_fms_matched}"
echo "total segments: ${num_segments}"
echo "avg seg len: ${average_seg_len}"

echo "${rel_err}"
echo "${total_words}"
echo "${words_matched}"
echo "${error_words}"
echo "${num_fms_matched}"
echo "${num_segments}"
echo "${average_seg_len}"

rm file1 file2 file3 file4 file5

