
import java.util.Iterator;


public class Dispatcher {

    public static void main(String argv[]) {

        ReadXML xml = new ReadXML();
        Boolean ejecutar = true;
        xml.getXML("prueba.xml");
        RunQueue cpu1 = new RunQueue();
        Scheduler planificador = new Scheduler();

        Iterator<Process> listIterator = xml.processList.iterator();

        System.out.println("Numero de procesos en RunQueue: " +xml.processList.size());

        // Inicializacion de valores iniciales de
        while (listIterator.hasNext()) {
            Process elem;
            elem=listIterator.next();
            elem.setStaticPriority(120);
            planificador.calcPrioridadDinamica(elem);
            planificador.tiempoBase(elem);
            cpu1.addActiveProcess(elem, elem.getDynamicPriority());
        }


        cpu1.imprimirProcesosActivos();

        planificador.planificar();


        while (ejecutar){

        }
        /*
            Por aqui deberia ir la simulacion del CPU. Pienso que deberiamos usar hilo
            para ejecutar el planificador cada 1milisegundo para similar el timer
            y usar scheduler_tick().
        */
    }

}
