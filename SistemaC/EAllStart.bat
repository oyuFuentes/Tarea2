%ECHO OFF
%ECHO Starting ECS System
PAUSE
%ECHO ECS Monitoring Console
START "MUSEUM ENVIRONMENTAL CONTROL SYSTEM CONSOLE" /NORMAL java ECSConsole %1
%ECHO Starting Temperature Controller Console
START "TEMPERATURE CONTROLLER CONSOLE" /MIN /NORMAL java TemperatureController %1
%ECHO Starting Humidity Sensor Console
START "HUMIDITY CONTROLLER CONSOLE" /MIN /NORMAL java HumidityController %1
START "TEMPERATURE SENSOR CONSOLE" /MIN /NORMAL java TemperatureSensor %1
%ECHO Starting Humidity Sensor Console
START "HUMIDITY SENSOR CONSOLE" /MIN /NORMAL java HumiditySensor %1

%ECHO ECS Monitoring Security Console
START "MONITORING SECURITY CONSOLE" /NORMAL java SecurityConsole %1

%ECHO Starting Security Sensor Console
START "SECURITY SENSOR CONSOLE" /MIN /NORMAL java SecuritySensor %1

%ECHO Starting Security Controller Console
START "SECURITY CONTROLLER CONSOLE" /MIN /NORMAL java SecurityController %1

%ECHO ================================================
%ECHO Starting Fire Console
START "FIRE CONTROLLER CONSOLE" /MIN /NORMAL java FireConsole %1

%ECHO Starting Alarm Controller Console
START "ALARM CONTROLLER CONSOLE" /MIN /NORMAL java AlarmController %1

%ECHO Starting Spray Controller Console
START "SPRAY CONTROLLER CONSOLE" /MIN /NORMAL java SprayController %1

%ECHO Starting Fire Sensor
START "FIRE SENSOR CONSOLE" /MIN /NORMAL java FireSensor %1

%ECHO ================================================
%ECHO Starting Service Console
START "SERVICE MONITORING CONSOLE" /MIN /NORMAL java ServiceConsole %1




