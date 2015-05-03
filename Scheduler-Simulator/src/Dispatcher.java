
import java.util.Iterator;


public class Dispatcher {

    public static void main(String argv[]) {

        ReadXML xml = new ReadXML();
        xml.getXML("prueba.xml");
        RunQueue cpu1 = new RunQueue();
        Scheduler planificador = new Scheduler();

        Iterator<Process> listIterator = xml.processList.iterator();

        System.out.println(xml.processList.size());

        while (listIterator.hasNext()) {
            Process elem;
            elem=listIterator.next();
            elem.setStaticPriority(120);
            planificador.calcPrioridadDinamica(elem);
            planificador.tiempoBase(elem);
            cpu1.addActiveProcess(elem, elem.getDynamicPriority());
        }

        cpu1.imprimirProcesosActivos();

    }

}
