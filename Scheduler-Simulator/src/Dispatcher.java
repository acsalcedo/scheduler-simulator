
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Dispatcher {

    public static void main(String argv[]) {

        ReadXML xml = new ReadXML();
        boolean execute = true;
        int i = 0;
        xml.getXML("prueba.xml");
        RunQueue cpu1 = new RunQueue();
        Scheduler scheduler = new Scheduler();

        Iterator<Process> listIterator = xml.processList.iterator();

        System.out.println("Numero de procesos en RunQueue: " +xml.processList.size());

        // Inicializacion de valores iniciales de
        while (listIterator.hasNext()) {
            Process elem;
            elem=listIterator.next();
            elem.setStaticPriority(120);
            elem.setPID(i++);
            //Falta tomar asignacion de prioridad distinta para RT
            scheduler.calcDynamicPriority(elem);
            scheduler.baseTime(elem);
            cpu1.addActiveProcess(elem, elem.getDynamicPriority());
        }

        cpu1.printActiveProcesses();
        System.out.printf("\nCOMIENZA EL SIMULADOR");

        scheduler.schedule(cpu1);
        System.out.printf("\nProceso en current: %d", cpu1.getCurrentProcess().getPID());

        TimerTask timerTask = new TimerTask()
        {
             /**
              * Método al que Timer llamará cada segundo. Se encarga de avisar
              * a los observadores de este modelo.
              */
            public void run() {
                System.out.printf("\nInvocado scheduler_tick");
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
