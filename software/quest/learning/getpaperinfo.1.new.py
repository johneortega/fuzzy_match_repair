#!/usr/bin/env python
import sys
from itertools import izip
import operator
import collections

# getting the number of patch ops and the sentence for each line
sentcntfile=sys.argv[1]
sentfile=sys.argv[2]
nodefile=sys.argv[3]
opfile=sys.argv[4]
allsentfile=sys.argv[5]
pocfile=sys.argv[6]
# first make sure that the two files are the same length
numline_sent = 0
numline_allsent = 0
numline_op = 0
numline_sentcnt = 0

nodes = {}
ops = {}
with open(sentcntfile,'rb') as scf:
    for line in scf:
        line=line.strip()
        numline_sentcnt += 1
    scf.close()
with open(sentfile,'rb') as sf:
    for line in sf:
        line=line.strip()
        numline_sent += 1
    sf.close()
if numline_sentcnt != numline_sent:
    print("lines in sentcnt: " + str(numline_sentcnt) + " are not equal to line in sent: " + str(numline_sent))
    sys.exit(-3)

with open(opfile,'rb') as opf:
    for line in opf:
        line=line.strip()
        numline_op += 1
    opf.close()
with open(allsentfile,'rb') as asf:
    for line in asf:
        line=line.strip()
        numline_allsent += 1
    asf.close()
#if numline_op != numline_allsent:
#    print("lines in all op file: " + str(numline_op) + " are not equal to line in all sent: " + str(numline_allsent))
#    sys.exit(-3)

# create a dict with the node file
with open(nodefile,'rb') as nf:
    for line in nf:
        line=line.strip()
        linearr=line.split('\t')
        #print "adding: " + str(linearr[0]) + " with: " + linearr[1]
        nodes[linearr[0]]=linearr[1]
    nf.close()

# create an op dict
with open(opfile) as opf, open(allsentfile) as asf: 
    hc_sent = 0
    numlinecnt = 0
    lastsent = ''
    for sent, op in izip(asf, opf):
        sent = sent.strip()
        op = op.strip()
        op = int(op)
        #print "adding sent: " + str(sent) + " with op: " + str(op)
        ops[sent]=op
    opf.close()
    asf.close()


# they are not unique
sprimes = {}
removelist = {}
sentdict = {}
# this counts up the numer of patchops per sentences
# it will print out to a file each sentence with the count
with open(sentfile) as sf, open(sentcntfile) as scf: 
    hc_sent = 0
    numlinecnt = 0
    lastsc = 0
    for sent, sc in izip(sf, scf):
        # put the nubmer of the sentence here
        sent = sent.strip()
        sc = sc.strip()
        sc = int(sc)
        sentdict[sc] = sent
        #print "this is numlinecnt: " + str(numlinecnt) + " and numlines in numline_sentcnt" + str(numline_sentcnt)
        # it's either the first line or any lines before last
        if (sc == lastsc or numlinecnt==0) and numlinecnt + 1 != numline_sentcnt :
            hc_sent = hc_sent + 1
        elif (numlinecnt + 1) == numline_sentcnt:
            # at the last line
            hc_sent = hc_sent + 1
            sprimes[sc] = str(hc_sent)
        else:
            # add the number of the sentence as the key
            # reset as it's a new line
            sprimes[lastsc] = str(hc_sent)
            hc_sent = 1
        lastsc = sc
        numlinecnt += 1
    sf.close()
    scf.close()

# we use the dictioanry from 60% fms because patching will be the same regardless
# sort by sentence id
# nodes and ops just use the sentence as they come from a bigger file
sorted_sprimex = sorted(sprimes.items(), key=operator.itemgetter(0))
sorted_sprimes = collections.OrderedDict(sorted_sprimex)
#print sorted_sprimes
# this is a list now
for sentid, hyp in sorted_sprimes.items():
        sent = sentdict[sentid]
        #print str(sentid) + '\t' + str(sent) + '\t' +  str(ops[sent]) +  '\t' + str(hyp) + "\t" + str(nodes[sent]) + '\n'
        with open(pocfile, 'ab') as spf:
            spf.write(str(sentid) + '\t' + str(sent) + '\t' +  str(ops[sent]) +  '\t' + str(hyp) + "\t" + str(nodes[sent]) + '\n') 
            spf.close()
'''
for sent,hyp in sprimes.items():
    if sent not in removelist and sent in nodes and sent in ops:
        with open(pocfile+".sentpoc", 'ab') as spf:
            spf.write(str(sent) + '\t' +  str(ops[sent]) +  '\t' + str(hyp) + "\t" + str(nodes[sent]) + '\n')
            spf.close()
'''
    





