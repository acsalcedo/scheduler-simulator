
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
/*
    Se ejecuta como Dispatcher {Tiempo para accionar el timer}
*/
public class Dispatcher {

    public static void main(String argv[]) {

        ReadXML xml = new ReadXML();
        boolean execute = true;
        System.out.printf("\nIntervalo del timer: %s", argv[0]);
        int i = 0, interruptInterval = Integer.parseInt(argv[0]);
        xml.getXML("prueba.xml");
        final RunQueue cpu1 = new RunQueue();
        final Scheduler scheduler = new Scheduler(interruptInterval, 99);

        Iterator<Process> listIterator = xml.processList.iterator();

        System.out.println("\nNumero de procesos en RunQueue: " +xml.processList.size());

        // Inicializacion de valores iniciales de los Process y RunQueue
        while (listIterator.hasNext()) {
            Process elem;
            elem=listIterator.next();
            elem.setStaticPriority(120);
            elem.setPID(i++);
            scheduler.calcDynamicPriority(elem);
            scheduler.baseTime(elem);
            cpu1.addActiveProcess(elem, elem.getDynamicPriority());
        }

        cpu1.printActiveProcesses();
        System.out.printf("\nCOMIENZA EL SIMULADOR");

        scheduler.inicializar(cpu1);
        System.out.printf("\nProceso en current: %d", cpu1.getCurrentProcess().getPID());

        TimerTask timerTask = new TimerTask()
        {
             /**
              * Método al que Timer llamará cada segundo. Se encarga de avisar
              * a los observadores de este modelo.
              */
            public void run() {
                System.out.printf("\n\nInvocado scheduler_tick");
                scheduler.schedule_tick(cpu1,scheduler);
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, scheduler.getInterruptInterval());

        while (cpu1.getCurrentProcess() != null ){}

        // Timer, Dispatcher, Scheduler.schedule -> Hilos
        /*
            Se usa hilo para ejecutar el scheduler.scheduler_tick() cada
            100 milisegundos para similar el timery usar . Luego
            scheduler.schedule() como hilo parte pero invocado por
            scheduler.scheduler_tick() para simular concurrencia del planificador
        */
    }

}
