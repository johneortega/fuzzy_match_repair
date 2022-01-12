#!/usr/bin/env python
import sys
decc=sys.argv[1]
num=sys.argv[2]
rnd=sys.argv[3]
#print("decc" + str(decc))
#print("num" + str(num))
#print("rnd" + str(rnd))
num=float(num)*float(rnd)
decsize="%."+decc+"f"
#print ("%.2f" % float(num))
print (decsize % float(num))
