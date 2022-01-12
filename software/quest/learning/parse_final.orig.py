#!/usr/bin/env python
# encoding: utf-8

totalerror = 0
totalwords = 0
finalout   = 0.0
total_lines= 0
with open("parse.out.final") as fp: 
    total_lines =  sum(1 for _ in fp)
    fp.close()
with open("parse.out.final") as fp: 
    bestscore = 0.0
    bested = 0
    bestwords=0
    lnum = 0
    prevlnum=0
    line_count = 1
    print "total lines: " + str(total_lines)
    for line in fp:
        nl = line.strip()
	arr=nl.split()
	score = float(arr[0])
	lnum  = int(arr[1])
	ed    = int(arr[2])
	words = int(arr[3])
	if(lnum != prevlnum):
		print str(bested) + " " + str(bestwords)
		print "Example #"+str(lnum)
		totalerror = totalerror + bested
		totalwords = totalwords + bestwords
		bestscore=score
		bested=ed
		bestwords=words
	elif(score < bestscore):
	#elif(score > bestscore):
		bestscore=score
		bested=ed
		bestwords=words
	elif(line_count==total_lines):
		print str(bested) + " " + str(bestwords)
		print "Example #"+str(lnum)
		totalerror = totalerror + bested
		totalwords = totalwords + bestwords
	prevlnum=lnum
	line_count=line_count + 1
# dev
#totalwords+=176
#totalerror+=32
# train 60
#totalwords+=10343
#totalerror+=2828
# train 80 
#totalwords+=16960
#totalerror+=46175
finalout = float(totalerror)/totalwords
print finalout
