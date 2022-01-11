#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
java -Xmx500M -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.omega.gz --tm-source input/en.omega.tm.gz --threshold 0.80 
