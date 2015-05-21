#javac FireConsole.java
#javac AlarmController.java
#javac SprayController.java
#javac FireSensor.java

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

echo "Starting Fire Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java FireConsole '$1'"'

echo "Starting Alarm Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java AlarmController '$1'"'

echo "Starting Spray Controller Console"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java SprayController '$1'"'

echo "Starting Fire Sensor"
osascript -e 'tell app "Terminal" to do script "cd '${DIR}' && java FireSensor '$1'"'
