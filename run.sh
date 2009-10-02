#!/bin/sh

INT_HOME=/home/robin/svn_work/robin/Development/Projects/Others/spring/installs/cmake/gai/linux32/debug/AI/Interfaces/Java/0.1
INT_JARS=${INT_HOME}/AIInterface.jar:${INT_HOME}/jlib/vecmath.jar:${INT_HOME}/jlib/jna/jna.jar
MY_COMPILE_CP=${INT_JARS}:target/dependency/*:target/GAI-0.1-SNAPSHOT.jar
MY_RUNTIME_CP=data/jscript:data/jconfig
MY_CP=${MY_COMPILE_CP}:${MY_RUNTIME_CP}

java -cp ${MY_CP} gai.AIFactory

