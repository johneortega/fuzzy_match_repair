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
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/original/en.source.gz --tm-source input/original/en.en-es.tm.gz --tm-target input/original/es.en-es.tm.gz --threshold $1 -f t --gold output/original/es.en-es.test.gz --lang en-es --mt input/original/es.en-es.mt.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.da.b1.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.baseline.b1.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.baseline.11102017.dgt.11.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.baseline.11102017.dgt.9.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.20171128_id_15_dgtsmallsmt.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.20171129_id_english.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.new.source.train.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold $1 -f t --gold output/en_es.new.target.train.gz --lang en-es --mt input/en_es.new.mt.train.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.new.source.250.dev.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold $1 -f t --gold output/en_es.new.target.250.dev.gz --lang en-es --mt input/en_es.new.mt.250.dev.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.new.source.250.dev.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --threshold $1 -f t --gold output/en_es.new.target.250.dev.gz --lang en-es --mt input/en_es.new.mt.250.dev.apertium.gz
## note we use all old here (old tm too) because it's a test
## thus, features are from the old tm, trained features use the new one
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.20171125_id_nmtdgt12.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.20171125_id_nmtdgt12.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --threshold $1 -f t --gold output/es.omega.test.gz --lang en-es --mt input/en_es.mt.neural.20171128_id_15_dgtsmallsmt.gz
### en-es
    #cp ./translation_map_files/translation_map_en-es.new.train.ser translation_map_files/translation_map_en-es.ser
    #java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
    #-cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.new.source.train.gz \
    #--tm-source input/en_es.new.tm.source.gz \
    #--tm-target input/en_es.new.tm.target.gz \
    #--threshold $1 \
    #-f t \
    #--gold output/en_es.new.target.train.gz \
    #--lang en-es \
    #--mt input/en_es.new.mt.train.apertium.gz
    #--mt input/en_es.new.mt.train.mosesfelipe.gz
    #--tm-source input/en_es.omega.tm.gz \
    #--tm-target input/es_en.omega.tm.gz \
### en-es
    #--mt input/en_es.mt.neural.20171129_id_english.gz
    #--mt input/en_es.new.mt.2000.test.apertium.gz
    #--mt input/en_es.mt.neural.20171129_id_english.gz
    #--mt input/en_es.new.mt.2000.test.apertium.gz
    #--mt input/en_es.new.mt.2000.test.mosesfelipe.gz
    cp translation_map_files/translation_map_en-es.apertium.r48232.ser translation_map_files/translation_map_en-es.ser
    #cp translation_map_files/translation_map_en-es.apertium.r85136.ser translation_map_files/translation_map_en-es.ser
    #cp translation_map_files/translation_map_en-es.new.test.marian.felipe.ser translation_map_files/translation_map_en-es.ser
    #--tm-source input/en_es.omega.tm.gz \
    #--tm-target input/es_en.omega.tm.gz \
    #--tm-source input/en_es.new.tm.source.gz \
    #--tm-target input/en_es.new.tm.target.gz \
    java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
    -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_es.new.source.2000.test.gz \
    --tm-source input/en_es.new.tm.source.gz \
    --tm-target input/en_es.new.tm.target.gz \
    --threshold $1 \
    -f t \
    --gold output/en_es.new.target.2000.test.gz \
    --lang en-es \
    --mt input/en_es.new.mt.2000.test.apertium.gz
    #--mt input/en_es.mt.r83165.apertium.gz
    #--mt input/en_es.new.mt.2000.test.apertium.gz
    #--mt input/en_es.mt.marian.gz
    #--mt input/en_es.new.mt.2000.test.apertium.gz
    #--mt input/en_es.mt.r83165.apertium.gz
    #--mt input/en_es.mt.apertium.gz
    #--mt input/en_es.mt.r83165.apertium.gz
    #--mt input/en_es.mt.apertium.gz
    #--mt input/en_es.new.mt.2000.test.apertium.gz
    #--mt input/en_es.mt.r83165.apertium.gz
    #--tm-source input/en_es.new.tm.source.gz \
    #--tm-target input/en_es.new.tm.target.gz \
elif [ $2 = 'es_pt' ]
then
### es-pt
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_pt.source.gz --tm-source input/es_pt.omega.tm.gz --tm-target input/pt_es.omega.tm.gz --threshold $1 -f t --gold output/pt.omega.test.gz --lang es-pt  --mt input/es_pt.mt.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_pt.new.source.train.gz --tm-source input/es_pt.new.tm.source.gz --tm-target input/es_pt.new.tm.target.gz --threshold $1 -f t --gold output/es_pt.new.target.train.gz --lang es-pt --mt input/es_pt.new.mt.train.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_pt.new.source.250.dev.gz --tm-source input/es_pt.new.tm.source.gz --tm-target input/es_pt.new.tm.target.gz --threshold $1 -f t --gold output/es_pt.new.target.250.dev.gz --lang es-pt --mt input/es_pt.new.mt.250.dev.apertium.gz
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_pt.source.gz --tm-source input/es_pt.omega.tm.gz --tm-target input/pt_es.omega.tm.gz --threshold $1 -f t --gold output/pt.omega.test.gz --lang es-pt --mt input/es_pt.mt.apertium.gz
elif [ $2 = 'es_fr' ]
then
### es-fr
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_fr.source.gz --tm-source input/es_fr.omega.tm.gz --tm-target input/fr_es.omega.tm.gz --threshold $1 -f t --gold output/fr.omega.test.gz --lang es-fr --mt input/es_fr.mt.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_fr.new.source.train.gz --tm-source input/es_fr.new.tm.source.gz --tm-target input/es_fr.new.tm.target.gz --threshold $1 -f t --gold output/es_fr.new.target.train.gz --lang es-fr --mt input/es_fr.new.mt.train.apertium.gz
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/es_fr.new.source.250.dev.gz --tm-source input/es_fr.new.tm.source.gz --tm-target input/es_fr.new.tm.target.gz --threshold $1 -f t --gold output/es_fr.new.target.250.dev.gz --lang es-fr --mt input/es_fr.new.mt.250.dev.apertium.gz
elif [ $2 = 'en_de' ]
then
### en-de
    java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
    -cp classes:jars/* es.ua.dlsi.tests.statystics.RunGlobalScenarioOne -s input/en_de.new.source.2.gz \
    --tm-source input/en_de.new.tm.source.gz \
    --tm-target input/en_de.new.tm.target.gz \
    --threshold $1 \
    -f t \
    --gold output/en_de.new.target.2.gz \
    --lang en-de \
    --mt input/en_de.new.mt.moses.2.gz
else
  echo "Need Lang pair and threshold"
fi
popd
