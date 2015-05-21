
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

echo "Starting ECS System"

echo "ECS Monitoring Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java ECSConsole '$1'"'
echo "Starting Temperature Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java TemperatureController '$1'"'
echo "Starting Humidity Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java HumidityController '$1'"'
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java TemperatureSensor '$1'"'
echo "Starting Humidity Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java HumiditySensor '$1'"'
echo "ECS Monitoring Security Console"

echo "==SECURITY SYSTEM ==========================================="
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityConsole '$1'"'
echo "Starting Security Controller"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityController '$1'"'
echo "Starting Security DoorSensor"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java DoorSensor '$1'"'
echo "Starting Security WindowsSensor"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java WindowSensor '$1'"'
echo "Starting Security Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java MovementSensor '$1'"'

echo "==FIRE DETECTION ============================================"
echo "Starting Fire Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java FireConsole '$1'"'
echo "Starting Alarm Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java AlarmController '$1'"'
echo "Starting Spray Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SprayController '$1'"'
echo "Starting Fire Sensor"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java FireSensor '$1'"'
echo "==MONITORING DEVICES========================================="
echo "Starting Service Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java ServiceConsole '$1'"'
