#!/usr/bin/env python
# encoding: utf-8
import sys
greater=0
lesser=0
with open(sys.argv[1], "r") as ins:
    counter = 0
    prevline = ""
    for line in ins:
	theline = line.strip()
	arr= theline.split("\t")
	actual =  arr[0] 
	predicted = arr[1]
	if actual >= predicted:
		greater+=1
	else:
		lesser+=1
print "greater:" + str(greater)
print "lesser:" + str(lesser)
