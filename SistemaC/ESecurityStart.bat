%ECHO ECS Monitoring Security Console
START "MONITORING SECURITY CONSOLE" /NORMAL java SecurityConsole %1

%ECHO Starting Security Sensor
START "SECURITY SENSOR CONSOLE" /MIN /NORMAL java DoorSensor %1

%ECHO Starting Security Sensor
START "SECURITY SENSOR CONSOLE" /MIN /NORMAL java WindowSensor %1

%ECHO Starting Security Sensor
START "SECURITY SENSOR CONSOLE" /MIN /NORMAL java MovementSensor %1

%ECHO Starting Security Controller Console
START "SECURITY CONTROLLER CONSOLE" /MIN /NORMAL java SecurityController %1