#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
ant clean
ant

# run like this
# ./run_global_features.sh en_es 60 train where 60 is fms and train is dev/test/train
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.patching.features.FeatureManager -i run_global_scenario_files/run_global_scenario_es_pt_60.intermediate.dev.gz -f run_global_scenario_files/run_global_scenario_es_pt_60.dev.bbandbothgrounding.wer.esplagomis.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp \
#classes:jars/* es.ua.dlsi.patching.features.FeatureManager -i \
#run_global_scenario_files/run_global_scenario_es_pt_90.intermediate.test.gz -f \
#run_global_scenario_files/run_global_scenario_es_pt_90.test.bbandbothgrounding.nof1nof4.wer.esplagomis.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp \
#classes:jars/* es.ua.dlsi.patching.features.FeatureManager -i \
#run_global_scenario_files/run_global_scenario_es_fr_${1}.intermediate.${2}.gz -f \
#run_global_scenario_files/run_global_scenario_es_fr_${1}.${2}.bbandbothgrounding.nof1nof4.wer.gz
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp \
classes:jars/* es.ua.dlsi.patching.features.FeatureManager -i \
scripts/run.out.intermediate.60.gz -f \
scripts/run.out.features.en_es.60.rerun.gz


#scripts/run.out.intermediate.en_es.${1}.marian.gz -f \
#scripts/run.out.features.en_es.${1}.esplagomis.marian.gz
#run_global_scenario_files/run_global_scenario_${1}_${2}.${3}.bbandbothgrounding.wer.new.testt.gz
#run_global_scenario_files/run_global_scenario_${1}_${2}.intermediate.${3}.gz -f \
#run_global_scenario_files/run_global_scenario_${1}_${2}.${3}.bbandbothgrounding.wer.new.testt.gz
#run_global_scenario_files/run_global_scenario_${1}_${2}.${3}.bbandbothgrounding.wer.new.esplagomis.gz
#run_global_scenario_files/run_global_scenario_${1}_${2}.${3}.bbandbothgrounding.wer.new.esplagomis.gz
#run_global_scenario_files/run_global_scenario_${1}_${2}.${3}.bbandbothgrounding.nobl1bl2bl4bl5bl7bl8.wer.esplagomis.gz
#run_global_scenario_files/run_global_scenario_es_pt_${1}.intermediate.${2}.gz -f \
#run_global_scenario_files/run_global_scenario_es_pt_${1}.${2}.bbandbothgrounding.wer.new.remove.gz
#run_global_scenario_files/run_global_scenario_es_pt_${1}.${2}.bbandbothgrounding.wer.new.esplagomis.gz
#run_global_scenario_files/run_global_scenario_es_fr_${1}.${2}.bbandbothgrounding.wer.new.gz
#run_global_scenario_files/run_global_scenario_en_es_${1}.${2}.bbandbothgrounding.nobl1bl2bl4bl5bl7bl8.wer.esplagomis.gz
#run_global_scenario_files/run_global_scenario_en_es_${1}.${2}.bbandbothgrounding.wer.new.esplagomis.gz
popd
