cd ServidorArchivos
call mvn package

cd target

java -jar .\DistributedFileSystemFacade-1.0-SNAPSHOT.jar