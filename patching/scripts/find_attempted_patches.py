#!/usr/bin/env python
f='/home/johneortega/patching/run_global_scenario_files/run_global_scenario_en_es_patching_with_mt_60t_restr.out'
count=0
prev2line=""
prevline=""
line=""
with open(f,'rb') as fi:
    for line in fi:
        line = line.strip()
        if line.startswith('Example #') and prev2line.startswith('Best Patch:'):
            count+=1
        prev2line = prevline
        prevline = line
print count
