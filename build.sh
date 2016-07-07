#!/bin/bash

start_time=$(date +%s%N)

echo -n "rm -rf */target"
rm -rf */target
echo " done"

echo "mvn clean package ..."
mvn clean package
echo "mvn clean package done"

execTime=$(( ($(date +%s%N) - $start_time) / 1000000))
echo "Execution time: $execTime ms"