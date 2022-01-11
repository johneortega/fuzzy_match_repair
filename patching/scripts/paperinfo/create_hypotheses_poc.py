#!/usr/bin/env python
import sys
from itertools import izip
import operator
import collections


opfile =  sys.argv[1] 
hypfile = sys.argv[2] 

hyps = {}
# create a dict with hyp count per id
with open(hypfile, 'rb') as hf:
    for line in hf:
        line = line.strip()
        linearr = line.split()
        hyps[linearr[1]] = linearr[0] 
    hf.close()


with open(opfile, 'rb') as of:
    for line in of:
        line = line.strip()
        linearr = line.split('\t')
        # add one to the ops
        sentid = linearr[0]
        numops = str(int(linearr[1]) + 1)
        numhyps = hyps[sentid]
        sprime = linearr[2]
        nodes = linearr[3]
        
        print sentid + '\t' + sprime + '\t' + numops + '\t' + numhyps + '\t' + nodes
    of.close()

