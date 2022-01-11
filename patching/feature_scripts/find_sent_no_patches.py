#!/usr/bin/env python
# encoding: utf-8
# this is used to find the examples that don't match
# probably best not to use this one as it seems to give different scores
import sys
def containsbest(the_list):
    for line in the_list:
        if line.startswith("Best Patch:"):   
            return True
with open(sys.argv[1], "r") as ins:
    error_words=0
    total_words=0
    prev_line=''
    the_line=''
    line_list=[]
    counter = 0
    bestpatch=False
    for line in ins:
	the_line = line.strip()
	if(the_line.startswith('Example #') and counter!=0 and not containsbest(line_list)):
		print line_list[0]
		word_line = line_list[-1]
		first=word_line.split()[0]
		second=word_line.split()[1]
		error_words+=int(first)
		total_words+=int(second)
		print first + " " + second
	if(the_line.startswith('Example #')):
		del line_list[:]
	line_list.append(the_line)
	counter+=1
    print "final: " + str(error_words) + " " + str(total_words)



