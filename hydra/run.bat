set MAVEN_OPTS="-Djava.library.path=target/natives"
mvn compile exec:java -Dexec.mainClass=hydra.App
