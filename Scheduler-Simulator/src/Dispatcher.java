
import java.util.Iterator;


public class Dispatcher {

    public static void main(String argv[]) {

        ReadXML xml = new ReadXML();
        boolean execute = true;
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
            scheduler.calcDynamicPriority(elem);
            scheduler.baseTime(elem);
            cpu1.addActiveProcess(elem, elem.getDynamicPriority());
        }

        cpu1.printActiveProcesses();
        scheduler.schedule(cpu1);

        while (execute){

        }
        
        // Timer, Dispatcher, Scheduler -> Hilos
        /*
            Por aqui deberia ir la simulacion del CPU. Pienso que deberiamos usar hilo
            para ejecutar el scheduler cada 1milisegundo para similar el timer
            y usar scheduler_tick().
        */
    }

}
