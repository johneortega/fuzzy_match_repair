#!/bin/bash
# script to measure the numbers from Examples
filename=$1
grep -B 2 "Example\ #" $filename > lines.out
sed -e '/(T)--/,+3d' lines.out > linesgood.out
filename=linesgood.out
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
rel_err_mas=`echo "($error_words / $total_words)*100" | bc -l`
rel_err=`printf "%.4f\n" ${rel_err}`
rel_err_mas=`printf "%.2f\n" ${rel_err_mas}`

num_fms_matched=`expr $num_segments - $num_fms_segments`

echo "relative error: ${rel_err}"
echo "relative error mas: ${rel_err_mas}"
echo "total words: ${total_words}"
echo "total words matched: ${words_matched}"
echo "total fms matched segments: ${num_segments}"
echo "total words not matched: ${error_words}"
echo "total segments: ${num_segments}"
echo "avg seg len: ${average_seg_len}"

echo "${rel_err}"
echo "${total_words}"
echo "${words_matched}"
echo "${error_words}"
echo "${num_segments}"
echo "${num_segments}"
echo "${average_seg_len}"

rm lines.out linesgood.out file1 file2 file3 file4 file5

