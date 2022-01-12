#!/usr/bin/env python
# encoding: utf-8
import sys
with open(sys.argv[1], "r") as ins:
    counter = 0
    prevline = ""
    for line in ins:
	if line.strip() != prevline:
		counter = counter + 1
	print counter
	prevline = line.strip()

