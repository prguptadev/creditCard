#!/bin/bash

# Exit on error
set -e

APP_NAME="credit-card-service"
JAR_FILE="target/credit-card-service-0.0.1-SNAPSHOT.jar"

# JVM Performance & Diagnostic Flags
JVM_OPTS="-Xms4G 
-Xmx4G 
-XX:+AlwaysPreTouch 
-XX:+UseCompactObjectHeaders 
-XX:+UseZGC 
-XX:ZCollectionInterval=300 
-XX:ZAllocationSpikeTolerance=2.0 
-Xlog:safepoint,class+load=info,thread+smr=debug 
-XX:+HeapDumpOnOutOfMemoryError 
-XX:HeapDumpPath=./dumps/ 
-XX:+UnlockDiagnosticVMOptions 
-XX:+DebugNonSafepoints 
-XX:ParallelGCThreads=4 
-XX:ConcGCThreads=2 
-Djava.util.concurrent.ForkJoinPool.common.parallelism=8 
-XX:NativeMemoryTracking=summary"

# Create dumps directory if it doesn't exist
mkdir -p ./dumps

echo "Building the application..."
./mvnw clean package -DskipTests

if [ -f "$JAR_FILE" ]; then
    echo "Starting $APP_NAME with optimized JVM settings..."
    java $JVM_OPTS -jar $JAR_FILE
else
    echo "Error: Jar file not found at $JAR_FILE"
    exit 1
fi
