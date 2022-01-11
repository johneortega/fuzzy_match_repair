#!/bin/bash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
pushd ../
ant clean
ant
### en-es
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles --mt-source translation_map_en-es.jhu.en --mt-target translation_map_en-es.jhu.es --mt-cache translation_map_en-es.jhu.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles --mt-source translation_map_en-es.apertium.r85136.en --mt-target translation_map_en-es.apertium.r85136.es --mt-cache translation_map_en-es.apertium.r85136.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles --mt-source translation_map_en-es.jhu.20171129_id_english.en --mt-target translation_map_en-es.jhu.20171129_id_english.es --mt-cache translation_map_en-es.jhu.20171129_id_english.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles --mt-source translation_map_en-es.jhu.20171129_id_english.en --mt-target translation_map_en-es.jhu.20171129_id_english.es --mt-cache translation_map_en-es.jhu.20171129_id_english.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles --mt-source all_sigmas/all_sigmas.new.es_fr.train.es.uniq --mt-target all_sigmas/all_sigmas.new.es_fr.train.fr.uniq --mt-cache translation_map_files/translation_map_es-fr.new.train.apertium.r62696.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles --mt-source all_sigmas/all_sigmas.new.es_fr.dev.es.uniq --mt-target all_sigmas/all_sigmas.new.es_fr.dev.fr.uniq --mt-cache translation_map_files/translation_map_es-fr.new.dev.apertium.r62696.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en \
#-Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles \
#--mt-source all_sigmas/all_sigmas.new.es_pt.dev.es.uniq \
#--mt-target all_sigmas/all_sigmas.new.es_pt.dev.pt.uniq \
#--mt-cache translation_map_files/translation_map_es-pt.new.dev.apertium.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en \
#-Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles \
#--mt-source all_sigmas/all_sigmas.new.es_pt.train.es.uniq \
#--mt-target all_sigmas/all_sigmas.new.es_pt.train.pt.uniq \
#--mt-cache translation_map_files/translation_map_es-pt.new.train.apertium.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en \
#-Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles \
#--mt-source all_sigmas/all_sigmas.new.2.en_de.en.uniq \
#--mt-target all_sigmas/all_sigmas.new.2.en_de.de.uniq \
#--mt-cache translation_map_files/translation_map_en-de.new.2.ser
#java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en \
#-Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles \
#--mt-source all_sigmas/all_sigmas.new.en_es.test.en \
#--mt-target all_sigmas/all_sigmas.new.en_es.test.es \
#--mt-cache translation_map_files/translation_map_en-es.new.test.moses.felipe.ser
java -Xmx8G -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError -Duser.language=en \
-Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.RunCreateTransMapFromFiles \
--mt-source all_sigmas/all_sigmas.new.en_es.test.marian.en \
--mt-target all_sigmas/all_sigmas.new.en_es.test.marian.es \
--mt-cache translation_map_files/translation_map_en-es.new.test.marian.felipe.ser
popd
