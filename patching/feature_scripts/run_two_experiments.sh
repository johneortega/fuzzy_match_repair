#!/bin/bash 
for x in 1 2 3 4 5;do for lang in en_es es_fr es_pt;do for fms in 60 70 80 90;do for sett in none esplagomis;do ./run_experiments.new.sh $lang $fms $sett test >> run_experiments.new.nofs.etr.test.out 2>&1 ;done;done;done;done;
for x in 1 2 3 4 5;do for lang in en_es es_fr es_pt;do for fms in 60 70 80 90;do for sett in none esplagomis;do ./run_experiments.new.60.sh $lang $fms $sett test >> run_experiments.new.nofs.60.etr.test.out 2>&1 ;done;done;done;done;
