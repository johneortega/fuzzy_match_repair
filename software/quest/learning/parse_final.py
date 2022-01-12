#!/usr/bin/env python
# encoding: utf-8
import sys

totalerror = 0
totalwords = 0
finalout   = 0.0
filein     = sys.argv[1]
#with open("parse.out.final") as fp: 
with open(filein) as fp: 
    # dict of line nums with array of double score, error words, totalwords
    lines = {}
    for line in fp:
        nl = line.strip()
	arr=nl.split()
	score = float(arr[0])
	lnum  = int(arr[1])
	ed    = int(arr[2])
	words = int(arr[3])
	if lnum in lines:
	    linearr = lines[lnum]
	    linearr.append((score,ed,words))
	else:
            newarr = []
	    newarr.append((score,ed,words))
	    lines[lnum]=newarr
    for linenum in lines:
        arr = lines[linenum]
        bestscore = 100.0
        bested = 0
        bestwords=0
        for tup in arr:
            scr = tup[0]
            ed  = tup[1]
            words = tup[2]
            if scr < bestscore:
                bestscore = scr
                bested    = ed
                bestwords = words
        totalerror+=bested
        totalwords+=bestwords
finalout = float(totalerror)/totalwords
#print "score for: " + filein + " is: " + str(finalout)
print "wer for: " + filein + " is: " + str(finalout)
print "edits for: " + filein + " is: " + str(totalerror)
print "total for: " + filein + " is: " + str(totalwords)
