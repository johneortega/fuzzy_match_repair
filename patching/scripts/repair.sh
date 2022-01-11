#!/bin/bash
## check that java is greater than 1.7
if java -version >/dev/null 2>&1; then
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    _java="$JAVA_HOME/bin/java"
else
    echo "Java version is less than 1.7, Aborting!"
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    if [[ "$version" < "1.7" ]]; then
        echo "Java version is less than 1.7, Aborting!"
	exit 1
    fi
fi

# check apertium
command -v apertium >/dev/null 2>&1 || { echo >&2 "I require that apertium be installed and in the path. Aborting!"; exit 1;} 

#export JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
ant clean
ant
java -Dfile.encoding=UTF8 -Xmx10G -XX:+UseConcMarkSweepGC -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.Repair "$@"
#java -Xmx10G -XX:+UseConcMarkSweepGC -Duser.language=en -Duser.country=US -cp classes:jars/* es.ua.dlsi.tests.statystics.Repair "the black dog was barking whole night" "el perro negro ladraba noche entera" "the black cat was barking whole night"
#rm output/es.features
#gunzip output/es.features.gz
#vi output/es.features 
