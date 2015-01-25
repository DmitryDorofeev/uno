@ECHO OFF

mvn compile assembly:single
echo assemble done
copy .\target\uno-1.0-jar-with-dependencies.jar .\
echo copy done
pause
