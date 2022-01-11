#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
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
#ant clean
#ant
if [ $2 = 'en_es' ]
then 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/en_es.new.source.train.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold $1 -f t --gold output/en_es.new.target.train.gz --lang en-es 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/en_es.new.source.2000.test.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold $1 -f t --gold output/en_es.new.target.2000.test.gz --lang en-es 
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/en_es.new.source.2000.test.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/en_es.new.target.2000.test.gz --lang en-es 
elif [ $2 = 'es_pt' ]
then
### es-pt
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/es_pt.source.gz --tm-source input/es_pt.omega.tm.gz --tm-target input/pt_es.omega.tm.gz --threshold $1 -f t --gold output/pt.omega.test.gz --lang es-pt  
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/es_pt.new.source.train.gz --tm-source input/es_pt.new.tm.source.gz --tm-target input/es_pt.new.tm.target.gz --threshold $1 -f t --gold output/es_pt.new.target.train.gz --lang es-pt
elif [ $2 = 'es_fr' ]
then
### es-fr
##java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_fr.source.gz --tm-source input/es_fr.omega.tm.gz --tm-target input/fr_es.omega.tm.gz --threshold $1 -f t --gold output/fr.omega.test.gz --lang es-fr 
### this is for the train run only
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/es_fr.new.source.train.gz --tm-source input/es_fr.new.tm.source.gz --tm-target input/es_fr.new.tm.target.gz --threshold $1 -f t --gold output/es_fr.new.target.train.gz --lang es-fr 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/es_fr.new.source.250.dev.gz --tm-source input/es_fr.new.tm.source.gz --tm-target input/es_fr.new.tm.target.gz --threshold $1 -f t --gold output/es_fr.new.target.250.dev.gz --lang es-fr 
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/es_fr.source.gz --tm-source input/es_fr.omega.tm.gz --tm-target input/fr_es.omega.tm.gz --threshold $1 -f t --gold output/fr.omega.test.gz --lang es-fr 
elif [ $2 = 'en_de' ]
then
### en-de
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGetSigmaAndSigmaPrimes -s input/en_de.new.source.4.gz --tm-source input/en_de.new.tm.source.gz --tm-target input/en_de.new.tm.target.gz --threshold $1 -f t --gold output/en_de.new.target.gz --lang en-de 
else
  echo "Need Lang pair and threshold"
fi
popd

