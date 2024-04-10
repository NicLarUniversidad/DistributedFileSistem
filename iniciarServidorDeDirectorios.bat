::@echo off

::timeout /t 30 /nobreak
::es para que espere 30 segundos después que el explorador inicie y no forzar tanto el sistema
::FOR %%X IN ("C:\MisProgramas\*.*") DO rundll32 shell32.dll,ShellExec_RunDLL %%X

:: "C:\MisProgramas\*.*" -> crear una carpeta con las aplicaciones a iniciar.

::Este archivo batch al ejecutarse, va a cargar todos los programas o aplicaciones
::que estén en la carpeta que creaste MisProgramas.
::Ahora el próximo paso es situar este batch en tu carpeta de Inicio.

cd ServidorDeDirectorios
call mvn package
cd target
java -jar .\FileServerManager-1.0-SNAPSHOT.jar