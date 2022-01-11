#!/bin/bash
COUNTER=1
START=7
LANG=es_pt
while [  $COUNTER -lt 10 ]; do
     #echo The counter is 0.$START$COUNTER
     source run_global_scenario.sh 0.${START}${COUNTER} $LANG > run_global_scenario_${LANG}_patching_with_mt_${START}${COUNTER}_restr.out 2>&1 &
     let COUNTER=COUNTER+1 
done
