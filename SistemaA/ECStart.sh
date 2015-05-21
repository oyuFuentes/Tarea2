#!/bin/bash

echo "Starting ECS System"

echo "ECS Monitoring Console"
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java ECSConsole '$1'"'
%ECHO Starting Temperature Controller Console
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java TemperatureController '$1'"'
echo "Starting Humidity Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java HumidityController '$1'"'
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java TemperatureSensor '$1'"'
echo "Starting Humidity Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java HumiditySensor '$1'"'

echo "ECS Monitoring Security Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityConsole '$1'"'

echo "Starting Security Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecuritySensor '$1'"'

echo "Starting Security Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityController '$1'"'
