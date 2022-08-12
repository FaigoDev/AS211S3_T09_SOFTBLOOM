#!/bin/sh
mvn clean package && docker build -t com.mycompany/PlantillaFreya .
docker rm -f PlantillaFreya || true && docker run -d -p 9080:9080 -p 9443:9443 --name PlantillaFreya com.mycompany/PlantillaFreya