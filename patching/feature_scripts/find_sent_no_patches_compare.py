#!/usr/bin/env python
# encoding: utf-8
# script called liek this  python find_sent_no_patches_compare.py grep1.o
# run_global_scenario_en_es_patching_with_mt_60t_restr.train.out
# where the grep1.o is an awk of the features file like this
# awk -F'\t' {'print $33'} run_global_scenario_en_es_60.train.bb.wer | sort | uniq > grep1.o
# and the otehr file is the original file run from global scenario

import sys

missing_examples = []
def getexampleinfo(the_file, the_sent):
    with open(the_file, "r") as ins:
	the_sent=the_sent+" (S')"
	prev_line=""
        for line in ins:
	    the_line = line.strip()
            if the_line == the_sent:
                missing_examples.append(prev_line)
            prev_line=the_line
# first get the list under control
with open(sys.argv[1], "r") as ins:
    for line in ins:
	the_line = line.strip()
        getexampleinfo(sys.argv[2], the_line)
    ins.close()

with open(sys.argv[2], "r") as ins:
    error_words=0
    total_words=0
    prev_line=''
    the_line=''
    line_list=[]
    counter = 0
    bestpatch=False
    examplehit=True
    for line in ins:
	the_line = line.strip()
	#print len(missing_examples)
	#print str(missing_examples)
	#if(the_line.startswith('Example #') and counter!=0 and the_line in missing_examples):
	if(the_line.startswith('Example #') and the_line in missing_examples):
		examplehit=True
	## check to make sure it's the two digits
	if the_line.split(' ')[0].isdigit() and the_line.split(' ')[1].isdigit() and examplehit:
		print the_line.split(' ')[0] + " " + the_line.split(' ')[1]
		examplehit=False
	#if(the_line.startswith('Example #') and counter!=0 and the_line not in missing_examples):
		#print line_list[0]
		#word_line = line_list[-1]
		#first=word_line.split()[0]
		#second=word_line.split()[1]
		#error_words+=int(first)
		#total_words+=int(second)
		#print first + " " + second
	#if(the_line.startswith('Example #')):
	#	del line_list[:]
	#line_list.append(the_line)
	counter+=1
