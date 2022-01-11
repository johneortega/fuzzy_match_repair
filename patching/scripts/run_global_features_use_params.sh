#!/bin/bash
if [ "$#" -ne 4 ]; then
    echo "Illegal number of parameters"
    echo "run_global_features_use_params.sh lang_pair fms theset eg"
    echo "run_global_features_use_params.sh en_es 90 test esplagomis"
    echo "run_global_features_use_params.sh en_es 90 test none"
    echo "for eg put anything except esplagomis for non-eg runs"
    exit 1
fi
#lang_pair="en_es"
#fms="90"
#theset="test"
#eg=""
lang_pair=$1
fms=$2
theset=$3
eg=$4
if [ $eg == "esplagomis" ]; then
    eg="esplagomis."
else
    eg=""
fi
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
ant clean
ant
inp="run_global_scenario_files/run_global_scenario_${lang_pair}_${fms}.intermediate.${theset}.gz"
outp="run_global_scenario_files/run_global_scenario_${lang_pair}_${fms}.${theset}.bbandbothgrounding.wer.${eg}gz"
echo "This is the input: ${inp}"

java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.patching.features.FeatureManager -i $inp -f $outp
popd
