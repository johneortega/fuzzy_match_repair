#!/bin/bash
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
#ant clean
#ant
## en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_pt.source.gz --tm-source input/es_pt.omega.tm.gz --tm-target input/pt_es.omega.tm.gz --threshold 0.52 -f t --gold output/pt.omega.test.gz --lang es-pt 
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold 0.52 -f t --gold output/es.omega.test.gz --lang en-es 
