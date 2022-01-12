#!/usr/bin/env python
import sys
with open(sys.argv[1], "r") as ins:
    counter = 0
    prevline = ""
    for line in ins:
        line = line.strip()
        if line != prevline:
            counter += 1
        print counter
        prevline = line

