#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
ant clean
ant
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslationWithFMS \
-s input/en_es.new.source.2000.test.gz \
--tm-source input/en_es.new.tm.source.gz \
--tm-target input/en_es.new.tm.target.gz \
--threshold $1 \
--gold output/en_es.new.target.2000.test.gz \
--mt input/en_es.new.mt.2000.test.apertium.gz
popd
