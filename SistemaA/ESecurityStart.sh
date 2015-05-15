echo "ECS Monitoring Security Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityConsole"'

echo "Starting Security Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecuritySensor"'

echo "Starting Security Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityController"'
