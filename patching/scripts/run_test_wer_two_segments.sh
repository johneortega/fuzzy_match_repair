#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
pushd ../
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
ant clean
ant
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestWERTwoSegments
popd
