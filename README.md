# Tarea2-Estilo basado en eventos
Contiene los sistemas A, B y C.

## How run the systems?
1. Compile all files inside a directory:
```
javac *.java
```

2. Build the Event Manager interface stubs (Only the first time):
```
rmic EventManager
```

3. For the base system: Start the EventManager and the Miseum Enviromnmental Control System:
```
EMStart.bat
ECStart.bat
```

- **System A:** Start Event Manager and the Security System:
```
EMStart.bat
ESecurity.bat
```

- **System B:** Start Event Manager, and Fire components:
```
EMStart.bat
EFire.bat
```

- **System C:** Start Event Manager, and all others devices
```
EMStart.bat
EAllStart.bat
```