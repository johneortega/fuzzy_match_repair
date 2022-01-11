#!/bin/bash

#lang="es_fr"
lang="en_es"
#lang="es_pt"
#for tset in 'dev' 'train' 'test';
for tset in 'dev' 'train';
do
    for fms in '60' '70' '80' '90';
    do
        grep '>>>>>' ${lang}/run_global_scenario_${lang}_$fms.intermediate.$tset | awk -F'\t' {'print $2'} > ${lang}/pop.$fms.po.out.$tset.hc
    done
done
