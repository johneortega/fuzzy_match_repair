#!/usr/bin/env python
# encoding: utf-8
# how many times is the oracle better than twiggle

totalerror = 0
totalwords = 0
finalout   = 0.0
total_lines= 0
prevlnum = 0
sent = {}
total_count = 0
with open("parse.out") as fp: 
    for line in fp:
        nl = line.strip()
	arr=nl.split()
	ed    = float(arr[0])
	lnum  = int(arr[2])
	if prevlnum == lnum:
		curr_arr = sent[lnum]
		curr_arr.append(ed)
		sent[lnum] = curr_arr
	else:
		arr = []
		arr.append(ed)
		sent[lnum] = arr
	#print str(ed) + " " + str(lnum)
	prevlnum=lnum
for line_num,edlist in sent.iteritems():
	#print line_num, edlist
	oracle = min(edlist)
	for ed in edlist:
		if ed > oracle:
			total_count+=1
print total_count
