#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
ant clean
ant
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en.source.origwithout12.gz --tm-source input/en.en-es.tm.gz --tm-target input/es.en-es.tm.gz --threshold 0.50 -f t --gold output/es.en-es.test.gz 
#rm output/es.features
#gunzip output/es.features.gz
#vi output/es.features 
