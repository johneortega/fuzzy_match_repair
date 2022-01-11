#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
#ant clean
#ant

lang=$1
fms=$2

#en-es 60 percent dev test
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
#-s input/en_es.new.source.250.dev.gz \
#--mt input/en_es.new.mt.250.dev.apertium.gz \
#--gold output/en_es.new.target.250.dev.gz \


#en-es 70 percent dev test
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
#-s input/en_es.new.source.250.dev.gz \
#--mt input/en_es.new.mt.250.dev.apertium.gz \
#--gold output/en_es.new.target.250.dev.gz \



#en-es neural, notice TM meanss nothing here
#--mt input/en_es.mt.neural.20171125_id_nmtbaseline12.gz \
#--mt input/en_es.mt.neural.20171128_id_15_dgtsmallsmt.gz \
#--mt input/en_es.mt.moses.gz \
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
#-s input/en_es.source.gz \
#--mt input/en_es.mt.selecT.gz \
#--gold output/es.omega.test.gz \
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
-s /home/johneortega/patching/input/felipe_mt_call/marian/${lang}_${fms}_source.esplagomis.out.gz \
--mt /home/johneortega/patching/input/felipe_mt_call/marian/${lang}_${fms}_source.esplagomis.mt.gz \
--gold /home/johneortega/patching/input/felipe_mt_call/marian/${lang}_${fms}_source.esplagomis.ref.gz 
echo "The above is for ${lang} and ${fms}"


#en-es apertium, notice TM meanss nothing here
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
#-s input/en.omega.gz \
#--tm-source input/en.omega.tm.gz \
#--tm-target input/en.omega.tm.gz \
#--mt input/en_es.mt.moses.gz  \
#--threshold 0.60 \
#-f t \
#--gold output/es.omega.test.gz \

#en-es apertium, notice TM meanss nothing here
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
#-s input/en.omega.gz \
#--tm-source input/en.omega.tm.gz \
#--tm-target input/en.omega.tm.gz \
#--mt input/en_es.mt.apertium.gz  \
#--threshold 0.70 \
#-f t \
#--gold output/es.omega.test.gz \

#es-fr apertium, notice TM meanss nothing here
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
#-s input/es_fr.source.gz \
#--tm-source input/en.omega.tm.gz \
#--tm-target input/en.omega.tm.gz \
#--mt input/es_fr.mt.apertium.gz  \
#--threshold 0.70 \
#-f t \
#--gold output/fr.omega.test.gz \

#es-pt apertium, notice TM means nothing here
# java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US \
#-cp classes:jars/* es.ua.dlsi.tests.statystics.RunMachineTranslation \
#-s input/es_pt.source.gz \
#--tm-source input/en.omega.tm.gz \
#--tm-target input/en.omega.tm.gz \
#--mt input/es_pt.mt.apertium.gz  \
#--threshold 0.70 \
#-f t \
#--gold output/pt.omega.test.gz \
popd
