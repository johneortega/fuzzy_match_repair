#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
ant clean
ant
#echo "starting en_es"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/en_es.new.source.250.dev.gz --tm-source input/en_es.new.tm.source.gz --tm-target input/en_es.new.tm.target.gz --gold output/en_es.new.target.250.dev.gz --threshold 0.60
#echo "starting es_fr"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/es_fr.new.source.250.dev.gz --tm-source input/es_fr.new.tm.source.gz --tm-target input/es_fr.new.tm.target.gz --gold output/es_fr.new.target.250.dev.gz --threshold 0.60
#echo "starting es_pt"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/es_pt.new.source.250.dev.gz --tm-source input/es_pt.new.tm.source.gz --tm-target input/es_pt.new.tm.target.gz --gold output/es_pt.new.target.250.dev.gz --threshold 0.60
#echo "starting en_es test"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/en_es.source.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --gold output/es.omega.test.gz --threshold 0.60
#echo "starting es_fr test"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/es_fr.source.gz --tm-source input/es_fr.omega.tm.gz --tm-target input/fr_es.omega.tm.gz --gold output/fr.omega.test.gz --threshold 0.60
#echo "starting es_pt test"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/es_pt.source.gz --tm-source input/es_pt.omega.tm.gz --tm-target input/pt_es.omega.tm.gz --gold output/pt.omega.test.gz --threshold 0.60
#echo "starting es_fr train"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/es_fr.new.source.train.gz --tm-source input/es_fr.new.tm.source.gz --tm-target input/es_fr.new.tm.target.gz --gold output/es_fr.new.target.train.gz --threshold 0.60
echo "starting en_es test"
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s input/en_es.new.source.2000.test.gz --tm-source input/en_es.omega.tm.gz --tm-target input/es_en.omega.tm.gz --gold output/en_es.new.target.2000.test.gz --threshold 0.60
#echo "starting en_de test"
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunTMGetNumMatchesByFMS -s /home/johneortega/for_marco_set2/2018_07_20_WMT2018/1000_test_sentences/source.1000.gz --tm-source input/en_de.new.tm.source.gz --tm-target input/en_de.new.tm.target.gz --gold /home/johneortega/for_marco_set2/2018_07_20_WMT2018/1000_test_sentences/ref.1000.gz --threshold 0.60
popd


