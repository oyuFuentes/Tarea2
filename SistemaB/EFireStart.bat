%ECHO Starting Fire Console
START "FIRE CONTROLLER CONSOLE" /MIN /NORMAL java FireConsole %1

%ECHO Starting Alarm Controller Console
START "ALARM CONTROLLER CONSOLE" /MIN /NORMAL java AlarmController %1

%ECHO Starting Spray Controller Console
START "SPRAY CONTROLLER CONSOLE" /MIN /NORMAL java SprayController %1

%ECHO Starting Fire Sensor
START "FIRE SENSOR CONSOLE" /MIN /NORMAL java FireSensor %1



