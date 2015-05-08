
import java.util.ArrayList;
import java.util.List;


public class InputOutput {
    
    private Process currentProcess;
    private boolean IOAvailable = true;
    private ArrayList<Process> queue = new ArrayList<Process>();
    
    public void addProcess(Process process) {
        
        if (IOAvailable) {
            currentProcess = process;
            IOAvailable = false;
        } else
            queue.add(process);
    }
    
    public Process getCurrentProcess() {
        return currentProcess;
    }
    
    public void setNextProcess() {
        
        if (queue.isEmpty()) {
            IOAvailable = true;
            currentProcess = null;
        } else
            currentProcess = queue.remove(0);
    }
    
    public Process getNextProcess() {
        return queue.get(0); //Retorna null si esta vacia?
    }
    
}
