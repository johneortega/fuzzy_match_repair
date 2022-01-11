import sys
prevline = ""
bestpatchhit = False
totalbestpatch = 0
with open(sys.argv[1],'r') as file1:
    for line in file1:
        theline = line.strip()
        if "Example #" in theline and theline!=prevline:
            if bestpatchhit:
                totalbestpatch+=1
            prevline = theline
            bestpatchhit = False
        if "Best Patch:" in theline:
            bestpatchhit = True
        
            
           
print totalbestpatch 
            
            
