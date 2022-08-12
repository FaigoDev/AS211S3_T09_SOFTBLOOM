@echo off
call mvn clean package
call docker build -t com.mycompany/PlantillaFreya .
call docker rm -f PlantillaFreya
call docker run -d -p 9080:9080 -p 9443:9443 --name PlantillaFreya com.mycompany/PlantillaFreya