#!/usr/bin/env python
## script to remove from both source and target
## where source matches input
from itertools import izip
import sys

sourcefile = "run_tm_get_best_es_fr_60.out"
sourcetmfile = "es_fr.new.tm.source"
targettmfile = "es_fr.new.tm.target"
sourcetmfileout = "es_fr.new.tm.source.out"
targettmfileout = "es_fr.new.tm.target.out"

sflinemap = {}
with open(sourcefile, 'rb') as sf:
    for sfline in sf:
        sfline = sfline.strip()
        sflinemap[sfline]=1
print "size of sflinemap: " + str(len(sflinemap))
sys.exit()
with open(sourcetmfile) as stmf, open(targettmfile) as ttmf: 
    for x, y in izip(stmf, ttmf):
        x = x.strip()
        y = y.strip()
        if x not in sflinemap:
            with open(sourcetmfileout, "ab") as stmfout, open(targettmfileout, "ab") as ttmfout: 
                stmfout.write(x+'\n')
                ttmfout.write(y+'\n')
                stmfout.close()
                ttmfout.close()
        else:
            print("skipping {0}".format(x))
        
print "done"
