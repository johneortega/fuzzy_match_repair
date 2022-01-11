#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
ant clean
ant
thresh="0."
if [ -n "$1" ]
then
 thresh=$thresh${1}
 echo "threshold set to: ${thresh}"
else
  echo "please provide a threshold"
  return
fi
### en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold 0.80 -f t --gold output/es.omega.test.gz --lang en-es 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold 0.60 -f t --gold output/es.omega.test.gz --lang en-es 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.new.source.250.dev.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold 0.70 -f t --gold output/en_es.new.target.250.dev.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.new.source.250.dev.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold 0.90 -f t --gold output/en_es.new.target.250.dev.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold 0.90 -f t --gold output/es.omega.test.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $thresh -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.new.mt.2000.test.mosesfelipe.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.new.source.train.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold 0.60 -f t --gold output/en_es.new.target.train.gz --lang en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.new.source.train.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold 0.90 -f t --gold output/en_es.new.target.train.gz --lang en-es

### en-es
### we added the trick of putting the 80 instead of 0.80 here
    #--tm-source input/en_es.omega.tm.gz \
    #--tm-target input/es_en.omega.tm.gz \
    java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
    -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.new.source.2000.test.gz \
    --tm-source input/en_es.new.tm.source.gz \
    --tm-target input/en_es.new.tm.target.gz \
    --threshold $thresh \
    -f t \
    --gold output/en_es.new.target.2000.test.gz \
    --lang en-es \
    --mt input/en_es.new.mt.2000.test.mosesfelipe.gz


### we added the trick of putting the 80 instead of 0.80 here
    #java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
    #-cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.new.source.2000.test.gz \
    #--tm-source input/en_es.new.tm.source.gz \
    #--tm-target input/en_es.new.tm.target.gz \
    #--threshold $thresh \
    #-f t \
    #--gold output/en_es.new.target.2000.test.gz \
    #--lang en-es \
    #--mt input/en_es.new.mt.2000.test.apertium.gz

#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/en_es.new.source.2000.test.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $thresh -f t --gold output/es.omega.test.gz --lang en-es

### es-pt we added the trick of putting the 80 instead of 0.80 here
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError \
#-Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne \
#-s input/en_de.new.source.2.gz \
#--tm-source input/en_de.new.tm.source.gz \
#--tm-target input/en_de.new.tm.target.gz \
#--threshold $thresh \
#-f t \
#--gold output/en_de.new.target.2.gz \
#--lang en-de \
#--mt input/en_de.new.mt.moses.2.gz

#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/es_pt.new.source.250.dev.gz --tm-source input/es_pt.new.tm.source.gz --tm-target input/es_pt.new.tm.target.gz --threshold $1 -f t --gold output/es_pt.new.target.250.dev.gz --lang es-pt
### es-fr
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/es_fr.source.gz --tm-source input/es_fr.omega.tm.gz --tm-target input/fr_es.omega.tm.gz --threshold $1 -f t --gold output/fr.omega.test.gz --lang es-fr 
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/es_fr.new.source.250.dev.gz --tm-source input/es_fr.new.tm.source.gz --tm-target input/es_fr.new.tm.target.gz --threshold $thresh -f t --gold output/es_fr.new.target.250.dev.gz --lang es-fr

### we added the trick of putting the 80 instead of 0.80 here
#    java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#    -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne -s input/es_fr.source.gz \
#    --tm-source input/es_fr.omega.tm.gz \
#    --tm-target input/fr_es.omega.tm.gz \
#    --threshold $thresh \
#    -f t \
#    --gold output/fr.omega.test.gz \
##    --lang es-fr \

### es-pt we added the trick of putting the 80 instead of 0.80 here
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError \
#-Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGlobalScenarioOne \
#-s input/en_de.new.source.2.gz \
#--tm-source input/en_de.new.tm.source.gz \
#--tm-target input/en_de.new.tm.target.gz \
#--threshold $thresh \
#-f t \
#--gold output/en_de.new.target.2.gz \
#--lang en-de \
#--mt input/en_de.new.mt.moses.2.gz
popd
