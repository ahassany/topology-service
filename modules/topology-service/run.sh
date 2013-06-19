#!/bin/bash
#java -Xmx256m -Djava.net.preferIPv4Stack=true -jar target/test-atmosphere-0.0.1-SNAPSHOT.one-jar.jar  $*
mvn clean package
cd target
unzip topology-service-0.1.0-SNAPSHOT-distribution.zip
./bin/nettosphere.sh
cd ../
