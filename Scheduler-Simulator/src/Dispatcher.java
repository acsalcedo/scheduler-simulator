
/*
    Cosas que faltan:
    -Implementar IO. Con wake up de procesos dormidos.
    -Agregar procesos en tiempo de ejecucion. Esto se logra usando hilos para
        ejecutar esas tareas.
    -Heuristica interactividad sobre IO_BOUND y CPU_BOUND (Si da tiempo)
    -La interfaz de usuario.
    -Probar el simulador
    -Implementar los tipos enumerados. Considero que es opcional.ya esta hecho
        con Strings.
    -Pasar el archivo xml por la consola.
    -Documentar
    -Alguna otra cosa importante.

    Se ejecuta como Dispatcher {Tiempo para accionar el timer}. El valor base
        debe ser 100. Si es mayor a 100 hay que ajustar los timeslices, totaltime y
        IOtime.
*/
public class Dispatcher {

    public static void main(String argv[]) {
        
        String file = argv[0];
        int interruptInterval = Integer.parseInt(argv[1]);
        ProcessAppletTable table = new ProcessAppletTable();
        table.init(file, interruptInterval);
    }
}
