#!/usr/bin/env python
# encoding: utf-8
## this is meant to take a parsed prediction file and 
## change the first colum to either be a 0.01 or 0.99 for each row
## sentences are taken into account
import sys
from random import randint
filein     = sys.argv[1]
randict = {}
with open(filein) as fp: 
    lines = {}
    prevsent=0
    for line in fp:
        nl = line.strip()
        linearr = nl.split('\t')
        currsent = int(linearr[1])
        if prevsent < currsent:
            randictarr = []
            randict[currsent] = randictarr
        randictarr.append(0.99)
        prevsent = currsent 
    fp.close()
# choose one for all of the options
for sentnum,werarr in randict.iteritems():
    rantop = int(len(werarr))
    thenum = randint(0, rantop - 1)
    randict[sentnum][thenum]=0.01

with open(filein) as fp: 
    arrcnt = 0
    for line in fp:
        nl = line.strip()
        linearr = nl.split('\t')
        currsent = int(linearr[1])
        if prevsent < currsent:
            arrcnt = 0
        pred_val = str(randict[currsent][arrcnt])
        linearr[0] = pred_val
        print "\t".join(linearr)
        arrcnt+=1
        prevsent = currsent 
        
         
    fp.close()


