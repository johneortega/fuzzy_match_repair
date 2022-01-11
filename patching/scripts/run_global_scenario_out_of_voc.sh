#!/bin/bash
############## This script will measure ooc of a file
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
if [ -n "$1" ]
then
 echo "file to measure out of vocab rate is: ${1}"
else
  echo "please provide a file for oov rate"
  exit 0 
fi
bad_trans=$(grep -c \* $1)
num_words=$(wc -w < $1)
final_rate=$(bc -l <<< $bad_trans/$num_words)
echo "Total Number of Non-Translated Stars: $bad_trans"
echo "Total Number of Words: $num_words"
echo "Rate of OOV: $final_rate"

