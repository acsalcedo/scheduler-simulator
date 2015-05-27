# scheduler-simulator
Simulador del planificador de CPU de Linux 2.6

## Para correr:
1. Compilar todas las clases utilizando: `javac -Xlint:unchecked *.java`
2. Correr el main con la siguiente instrucción: `java Dispatcher <nombreArchivo> <intervaloInterrupción>`

Por ejemplo si quiero correr el test "RR.xml" utilizo el siguiente comando de linea:
* `java Dispatcher ../test/RR.xml 1000`
