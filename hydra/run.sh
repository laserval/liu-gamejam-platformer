#!/bin/sh
export MAVEN_OPTS=-Djava.library.path=target/natives

mvn -e compile exec:java -Dexec.mainClass=hydra.App
