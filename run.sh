#!/bin/sh

MY_JAR=$(ls target/GAI*.jar)
#INT_HOME=/home/robin/svn_work/robin/Development/Projects/Others/spring/installs/cmake/gai/linux32/debug/AI/Interfaces/Java/0.1
#INT_JARS=${INT_HOME}/AIInterface.jar:${INT_HOME}/jlib/*
#MY_COMPILE_CP=${INT_JARS}:target/dependency/*:${MY_JAR}
MY_COMPILE_CP=$(cat target/cp.txt):${MY_JAR}
MY_RUNTIME_CP=data/jscript:data/jconfig
MY_CP=${MY_RUNTIME_CP}:${MY_COMPILE_CP}
MY_MAIN_CLASS=gai.AIFactory

java -cp ${MY_CP} ${MY_MAIN_CLASS} $@

