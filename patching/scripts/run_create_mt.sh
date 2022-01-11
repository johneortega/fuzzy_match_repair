#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
echo "going up a dir"
pushd ../
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
if [ -n "$1" ]
then
 echo "lang pair set to: ${1}"
else
  echo "please provide a language pair like en_es"
    exit 3
fi
#ant clean
#ant
### en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold 0.80 -f t --gold output/es.omega.test.gz --lang en-es 
if [ $1 = 'en_es' ]
then 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/en_es.new.source.250.dev.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/original/en.source.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/en_es.source.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s translation_map_en-es.apertium.r85136.en.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s translation_map_en-es.apertium.r48232.en.gz --lang en-es
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/original/en.omega.tm.gz --lang en-es
elif [ $1 = 'es_pt' ]
then
### es-pt
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/es_pt.source.gz --lang es-pt
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/es_pt.new.source.train.gz --lang es-pt
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s all_sigmas/all_sigmas.new.es_pt.train.es.uniq.gz --lang es-pt
elif [ $1 = 'es_fr' ]
then
### es-fr
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/es_fr.new.source.train.gz --lang es-fr
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/es_fr.new.source.250.dev.gz --lang es-fr
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s all_sigmas/all_sigmas.new.es_fr.train.uniq.gz --lang es-fr
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s all_sigmas/all_sigmas.new.es_fr.dev.es.uniq.gz --lang es-fr
elif [ $1 = 'en_de' ]
then
### en-de
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s input/en_de.new.source.gz --lang en-de
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateMT -s all_sigmas/all_sigmas.new.es_pt.train.es.uniq.gz --lang es-pt
else
  echo "Need Lang pair and threshold"
fi
popd
