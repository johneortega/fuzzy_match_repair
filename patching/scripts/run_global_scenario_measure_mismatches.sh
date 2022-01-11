#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
if [ -n "$1" ]
then
 echo "threshold set to: ${1}"
else
  echo "please provide a threshold"
  exit 0 
fi
if [ -n "$2" ]
then
 echo "lang pair set to: ${2}"
else
  echo "please provide a language pair"
  exit 0 
fi
ant clean
ant
### en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold 0.80 -f t --gold output/es.omega.test.gz --lang en-es 
if [ $2 = 'en_es' ]
then 
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunMeasureMismatchAndTarget -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es 
elif [ $2 = 'es_pt' ]
then
### es-pt
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunMeasureMismatchAndTarget -s input/es_pt.source.gz --tm-source input/es_pt.omega.tm.gz --tm-target input/pt_es.omega.tm.gz --threshold $1 -f t --gold output/pt.omega.test.gz --lang es-pt  
elif [ $2 = 'es_fr' ]
then
### es-fr
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunMeasureMismatchAndTarget -s input/es_fr.source.gz --tm-source input/es_fr.omega.tm.gz --tm-target input/fr_es.omega.tm.gz --threshold $1 -f t --gold output/fr.omega.test.gz --lang es-fr 
else
  echo "Need Lang pair and threshold"
fi
