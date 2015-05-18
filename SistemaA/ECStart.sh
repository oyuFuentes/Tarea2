#!/bin/bash

echo "Starting ECS System"

echo "ECS Monitoring Console"
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java ECSConsole"'
%ECHO Starting Temperature Controller Console
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java TemperatureController"'
echo "Starting Humidity Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java HumidityController"'
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java TemperatureSensor"'
echo "Starting Humidity Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java HumiditySensor"'

echo "ECS Monitoring Security Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityConsole"'

echo "Starting Security Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecuritySensor"'

echo "Starting Security Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityController"'