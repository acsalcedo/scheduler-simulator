import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class PriorityArray {

    private int numActiveProcesses = 0; /**< El numero de procesos activos.*/
    private int[] bitmap = new int[140]; /**< Bitmap para verificar rapidamente si una prioridad esta vacia.*/
    private ConcurrentHashMap<Integer,LinkedList<Process>> queue
                    = new ConcurrentHashMap<Integer,LinkedList<Process>>(140); /**< Arreglo de listas enlazadas. */

    /**
     * @brief Retorna el numero de procesos activos.
     * @return Numero de procesos activos.
     */
    public int getNumActiveProcesses() {
        return numActiveProcesses;
    }

    /**
     * @brief Aumenta el numero de procesos activos.
     */
    public void increaseNumActiveProcesses() {
        numActiveProcesses++;
    }

    /**
     * @brief Decrementa el numero de procesos activos.
     */
    public void decreaseNumActiveProcesses() {
        numActiveProcesses--;
    }

    /**
     * @brief Devuelve el proceso con la prioridad mas alta.
     * @return Proceso con la prioridad mas alta.
     */
    public Process getHighestPriorityProcess() {
        Process temp;
        int i = 0;

        while (i < bitmap.length && bitmap[i] != 1)
            i++;

        if (i == bitmap.length)
            return null;

        temp = queue.get(i).get(0);
        return temp;
    }
    
    /**
     * Devuelve la prioridad mas alta.
     * @return La prioridad mas alta
     */

    public int getHighestPriorityBitmap() {
        int i = 0;

        while (i < bitmap.length && bitmap[i] != 1)
            i++;

        return i;
    }

    /**
     * @brief Se agrega una prioridad al Bitmap.
     * @param priority Prioridad a agregar.
     */
    public void addPriorityBitmap(int priority) {
        bitmap[priority] = 1;
    }

    /**
     * @brief Se elimina del Bitmap la prioridad dada.
     * @param priority Prioridad a eliminar.
     */
    public void removePriorityBitmap(int priority) {
        bitmap[priority] = 0;
    }

    /**
     * @brief Verifica si la prioridad dada esta vacia.
     * @param priority Prioridad a verificar.
     * @return Si la prioridad dada esta vacia.
     */
    public boolean isPriorityEmpty(int priority) {
        return bitmap[priority] == 0;
    }

    /**
     * Devuelve si el priorityArray esta vacio.
     * @return Booleano diciendo si el priorityArray esta vacio.
     */
    public boolean isEmpty() {
        boolean cent = true;
        int i = 0;

        while (i < bitmap.length && (cent = (bitmap[i] != 1)))
            i++;

        return cent;
    }

    /**
     * @brief Retorna el primer proceso de la prioridad dada.
     * @param priority Prioridad del proceso.
     * @return Primer proceso de la prioridad dada.
     */
    public Process getProcess(int priority) {

        if (priority == bitmap.length)
            return null;
        else
            return queue.get(priority).get(0);
    }

    /**
     * @brief Retorna la longitud del Bitmap.
     * @return Longitud del Bitmap.
     */
    public int getLengthBitmap() {
        return bitmap.length;
    }

    /**
     * @brief Agrega un proceso a la prioridad dada.
     * @param process Proceso a agregar.
     * @param priority Prioridad donde se agrega el proceso.
     */
    public void addProcess(Process process, int priority) {

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

    /**
     * @brief Elimina el primer proceso de la prioridad dada.
     * @param priority Prioridad de donde se elimina el proceso.
     * @return Proceso que se elimino.
     */
    public Process removeProcess(int priority) {

        Process process = null;

        if (bitmap[priority] == 1) {
            process = queue.get(priority).removeFirst();

            if (queue.get(priority).isEmpty())
                removePriorityBitmap(priority);

            decreaseNumActiveProcesses();
        }
        return process;
    }

    /**
     * @brief Imprime las listas de prioridades.
     */
    public void printTable(){
        Iterator<LinkedList<Process>> listIterator;
        Iterator<Process> listIteratorProcess;
        List<Process> elem;
        Process process;

        for (int i = 0; i < 140; i++) {
            elem = queue.get(i);
            if (elem != null) {
                listIteratorProcess = elem.iterator();
                if (!elem.isEmpty()) {
                    System.out.printf("\nLista de prioridad %d", i);
                    while (listIteratorProcess.hasNext()){
                        process = listIteratorProcess.next();
                        process.print();
                    }
                }
            }
        }
    }
}
