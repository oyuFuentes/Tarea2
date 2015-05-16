
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

echo "Starting ECS System"

echo "ECS Monitoring Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java ECSConsole"' 
echo "Starting Temperature Controller Console"
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
echo "================================================"
echo "Starting Fire Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java FireConsole"' 
echo "Starting Alarm Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java AlarmController"' 
echo "Starting Spray Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SprayController"' 
echo "Starting Fire Sensor"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java FireSensor"' 
echo "================================================"
echo "Starting Service Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java ServiceConsole"' 
