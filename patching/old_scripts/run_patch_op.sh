#!/binbash
############## This script will first get the fuzzy match scores according to a threshold
export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
ant clean
ant
#ant clean jars
#ant jars
#java -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestFMSList -s input/en.source.gz -t output/es.target --tm-source input/en.en-es.tm.gz --tm-target input/es.en-es.tm.gz --threshold 90 -f t -o output/es.features
java -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.TestFMSPatchOperator
