#!/usr/bin/env python
import sys
tf=sys.argv[1]
tfl=0.0
with open(tf,'rb') as f:
    for line in f:
        line=line.strip()
        fl = float(line)
        tfl+=fl
    f.close()
with open(tf+".total", 'wb') as f:
    f.write(str(tfl))
    f.close()
