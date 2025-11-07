#!/usr/bin/env bash
# build.sh
javac -cp backend/lib/json.jar -d backend/backend_classes backend/src/CalculadoraServer.java
java -cp backend/backend_classes:backend/lib/json.jar backend.CalculadoraServer
