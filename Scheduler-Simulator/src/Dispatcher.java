
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
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
        
        ProcessAppletTable table = new ProcessAppletTable();
        table.init();
//
//        ReadXML xml = new ReadXML();
//        boolean execute = true;
//        System.out.printf("\nIntervalo del timer: %s", argv[0]);
//        int i = 0, interruptInterval = Integer.parseInt(argv[0]);
//        xml.getXML("prueba.xml");
//        final RunQueue cpu1 = new RunQueue();
//        final Scheduler scheduler = new Scheduler(interruptInterval, 99, cpu1);
//
//        Iterator<Process> listIterator = xml.processList.iterator();
//
//        System.out.println("\nNumero de procesos en RunQueue: " +xml.processList.size());
//
//        // Inicializacion de valores iniciales de los Process y RunQueue
//        // Cambiar esto, si es posible durante la carga
//        while (listIterator.hasNext()) {
//            Process elem;
//            elem=listIterator.next();
//            elem.setStaticPriority(120);
//            elem.setPID(i++);
//            elem.setState("TASK_RUNNING");
//            if (elem.getTotalTime() > elem.getIOTime()){
//                elem.setProcessType("CPU_BOUND");
//            } else {
//                elem.setProcessType("IO_BOUND");
//            }
//            scheduler.calcDynamicPriority(elem);
//            scheduler.baseTime(elem);
//            cpu1.addActiveProcess(elem, elem.getDynamicPriority());
//        }
//
//        cpu1.printActiveProcesses();
//        System.out.printf("\nCOMIENZA EL SIMULADOR");
//
//        scheduler.inicializar(cpu1);
//        System.out.printf("\nProceso en current: %d", cpu1.getCurrentProcess().getPID());
//
//
//        TimerTask timerTask = new TimerTask()
//        {
//             /**
//              * Método al que Timer llamará cada segundo. Se encarga de avisar
//              * a los observadores de este modelo.
//              */
//            public void run() {
//                System.out.printf("\n\nInvocado scheduler_tick");
//                scheduler.schedule_tick(cpu1);
//            }
//        };
//
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(timerTask, 0, scheduler.getInterruptInterval());
//        scheduler.start();
//
//        while (true) {}
//
//        // Schedule_tick, Scheduler.schedule -> Hilos

    }

}
