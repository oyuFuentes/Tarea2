# Tarea2-Estilo basado en eventos
Contiene los sistemas A, B y C.


###Veriones de java
Se probo con la version 1.7
En la version 1.8 de java marca deprecated en el EventManager


## How run the systems?

#Windows
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
ESecurityStart.bat
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

#Mac


1. Compile all files inside a directory:
```
javac *.java
```

- **System A:** Start Event Manager and the Security System:
```
sh EMStart.sh
sh ESecurityStart.sh
```

- **System B:** Start Event Manager, and Fire components:
```
sh EMStart.sh
sh EFireStart.sh
```
- **System C:** Start Event Manager, and all others devices
```
sh EMStart.sh
sh EAllStart.sh
```
