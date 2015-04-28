
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
        this.numActiveProcesses++;
    }

    public void addPriorityBitmap(int priority) {
        this.bitmap[priority] = 1;
    }
    
    public void removePriorityBitmap(int priority) {
        this.bitmap[priority] = 0;
    }
    
    public boolean isPriorityEmpty(int priority) {
        return bitmap[priority] == 0;
    }
    
    
    
}
