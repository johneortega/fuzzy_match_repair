### calculate mean average error
# https://en.wikipedia.org/wiki/Mean_absolute_error
import csv
from sklearn.metrics import mean_absolute_error
oravals = []
predvals = []
with open('parse.out','r') as f:
    reader=csv.reader(f,delimiter='\t')
    for vals in reader:
        oravals.append(float(vals[0]))
        predvals.append(float(vals[1])) 
if len(oravals) > 0 and len(predvals) > 0:
    print mean_absolute_error(oravals, predvals)
else:
    print "error in ora/pred vals prepare_mae.py"




