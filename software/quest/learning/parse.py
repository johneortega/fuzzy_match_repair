#!/usr/bin/env python
# encoding: utf-8
## predicted.csv is the default from quest
## argument 1 is the sentence count file from the hypothesis printed and processed in process.py
## argument 2 is the file that contains the word error rates for each hypothesis

from itertools import izip
import sys
#with open(sys.argv[3]"predicted.csv") as textfile1, open(sys.argv[1]) as textfile2, open(sys.argv[2]) as textfile3: 
with open(sys.argv[3]) as textfile1, open(sys.argv[1]) as textfile2, open(sys.argv[2]) as textfile3: 
    for x, y, z in izip(textfile1, textfile2, textfile3):
        x = x.strip()
        y = y.strip()
        z = z.strip()
        print("{0}\t{1}\t{2}".format(x, y, z))
