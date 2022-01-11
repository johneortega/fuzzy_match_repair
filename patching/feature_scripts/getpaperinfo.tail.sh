#!/bin/bash
# ./getpaperinfo.tail.sh 60 en_es paper_info_20180219-225823 dev
fms=$1
paper_dir=$3
lang_pair=$2
the_set=$4
pushd $paper_dir
for fl in "paper_info.out.${lang_pair}.${fms}.${the_set}.poc.total paper_info.out.${lang_pair}.${fms}.${the_set}.hc paper_info.out.${lang_pair}.${fms}.${the_set}.wc.total paper_info.out.${lang_pair}.${fms}.${the_set}.avgwcph";do tail $fl; done;
popd
