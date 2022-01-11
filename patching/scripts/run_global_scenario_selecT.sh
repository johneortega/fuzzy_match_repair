#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
pushd ../
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
if [ -n "$1" ]
then
 echo "threshold set to: ${1}"
else
  echo "please provide a threshold"
  return
fi
if [ -n "$2" ]
then
 echo "lang pair set to: ${2}"
else
  echo "please provide a language pair"
  return 
fi
ant clean
ant
if [ $2 = 'en_es' ]
then 
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOneSelecT -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.moses.gz
else
  echo "Need Lang pair and threshold"
fi
popd
