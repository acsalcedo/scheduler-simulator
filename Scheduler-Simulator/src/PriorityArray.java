import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class PriorityArray {

    private int numActiveProcesses = 0;
    private int[] bitmap = new int[139];
    // private LinkedList<Process> queue[] = new [139];
    // No se puede combinar arreglos y listas. Por eso uso Tabla hash con Integer
    private ConcurrentHashMap<Integer,LinkedList<Process>> queue
                    = new ConcurrentHashMap<Integer,LinkedList<Process>>(139);

    public int getNumActiveProcesses() {
        return numActiveProcesses;
    }

    public void increaseNumActiveProcesses() {
        numActiveProcesses++;
    }

    public void addPriorityBitmap(int priority) {
        bitmap[priority] = 1;
    }

    public void removePriorityBitmap(int priority) {
        bitmap[priority] = 0;
    }

    public boolean isPriorityEmpty(int priority) {
        return bitmap[priority] == 0;
    }

    public Process getProcess(int priority) {
        List<Process> temp;
        if (priority == bitmap.length){
            return null;
        } else {
            temp=queue.get(priority);
            return temp.get(0);
        }
    }

    public int tamanioMax() {
        return bitmap.length;
    }

    public void addProcess(Process process, int priority) {
            //System.out.println("holaaa");
        LinkedList<Process> lista ;

        if (bitmap[priority] == 0)
            addPriorityBitmap(priority);


        if (queue.get(priority) == null) {
            lista = new LinkedList<Process>();
            lista.addLast(process);
            queue.put(priority, lista);
        } else {
            lista = queue.get(priority);
            lista.addLast(process);
        }
        increaseNumActiveProcesses();
    }


    /* Acomodar este metodo

    public Process removeProcess(int priority) {
        if (bitmap[priority] == 1) {
            removePriorityBitmap(priority);
        }
        return queue[priority].pollFirst();
    }*/

    /*
        Funcion nueva creada para verificar las listas prioridad
    */
    public void imprimirTabla(){
        Iterator<LinkedList<Process>> listIterator;
        Iterator<Process> listIteratorProceso;
        List<Process> elem;
        Process proceso;

        for (int i = 0; i < 140; i++) {
            elem = queue.get(i);
            if (elem != null) {
                System.out.printf("\nLista de prioridad %d", i);
                listIteratorProceso = elem.iterator();
                while (listIteratorProceso.hasNext()){
                    proceso = listIteratorProceso.next();
                    proceso.print();
                }
            }

        }
    }
}
