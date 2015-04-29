
import java.util.concurrent.ConcurrentHashMap;

public class PriorityArray {
    private int numActiveProcesses = 0;
    private int[] bitmap = new int[139];
    private ConcurrentHashMap<String,Process> queue;
    
    public PriorityArray() {
        this.queue = new ConcurrentHashMap<String,Process>(139);
        
    }    
    
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
    
    public Process getProcess() {
        
        int i = 0;
        while (i < bitmap.length && bitmap[i] == 0)
            i++;
        
        if (i == bitmap.length)
            return null;
        else
            return queue.get(i); //TODO poner el string en vez de la prioridad
    }
    
    public void addProcess(Process process, int priority) {
        
        if (bitmap[priority] == 0)
            addPriorityBitmap(priority);
        
        // queue.put(priority, process); //TODO poner el string en vez de la prioridad
    }
    
    
    public Process removeProcess(int priority) {
        return queue.remove(priority);
        // TODO - cambiar bitmap
    }
    
    
    
    
    
}
