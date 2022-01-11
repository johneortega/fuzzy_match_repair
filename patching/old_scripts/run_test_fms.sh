#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
ant clean
ant
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-es.tm.gz --tm-target input/es.en-es.tm.gz --threshold 0.80 
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.00.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.01.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.02.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.03.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.04.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.05.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.06.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.07.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.08.gz --threshold 0.80 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestMatching -s input/en.source.gz --tm-source input/en.en-fr.tm.09.gz --threshold 0.80 
#rm output/es.features
#gunzip output/es.features.gz
#vi output/es.features 
