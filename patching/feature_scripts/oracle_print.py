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
	# tuple with words 3 11, etc..
	ed    = (arr[3],arr[4])
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
final_ed = 0.00
final_w = 0
for line_num,tup in sent.iteritems():
	#print line_num, edlist
	bested = 100.00	
	total_ed = 0
	total_w = 0
	for ed in tup:
		if ((int(ed[0])/int(ed[1]))*100 < bested):
			bested = int(ed[0])/int(ed[1])
			total_ed = int(ed[0])
			total_w = int(ed[1])
	final_ed = final_ed + total_ed
	final_w = final_w + total_w
print final_ed/final_w
