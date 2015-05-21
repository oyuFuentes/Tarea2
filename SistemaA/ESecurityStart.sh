DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
echo "ECS Monitoring Security Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityConsole '$1'"'

echo "Starting Security Sensor Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java DoorSensor '$1'"'

osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java WindowSensor '$1'"'
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java MovementSensor '$1'"'

echo "Starting Security Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SecurityController '$1'"'
