#!/bin/bash
# used to print out 31 - 39 for example to put in paper
for ((i=71;i<=79;i++));
do
   spread=`source run_spread_fms.sh "run_global_scenario_es_fr_patching_with_mt_${i}_restr.out" | grep 'relative error mas: '`
   prefix='relative error mas: '
   spread=`echo "$spread" | sed -e "s/^$prefix//"`
   echo "($i, $spread)"
done
