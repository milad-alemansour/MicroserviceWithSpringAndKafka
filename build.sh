#!/bin/bash
declare -a services=("Api" "ApiGateway" "EurekaServer" "Stream")
for service in ${services[@]}; do
  cd $service
  mvn clean package
  cd ..
done
