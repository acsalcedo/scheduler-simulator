
import java.util.ArrayList;
import java.util.List;


public class InputOutput extends Thread {
    
    private Process currentProcess = null;
    private boolean IOAvailable = true;
    ArrayList<Process> queue = new ArrayList<Process>();
    private int interruptInterval = 0;
    private Interfaz interfaz;
    
    public InputOutput(int interruptInterval, Interfaz interfaz) {
        this.interruptInterval = interruptInterval;
        this.interfaz = interfaz;
        new Thread(this,"IO");
    }
    public void run() {
        
        while (true) {
            if (currentProcess != null) {

                System.out.println("\nTimer de IO");
                Integer pid = currentProcess.getPID();
                interfaz.processIOLabel.setText(pid.toString());
                currentProcess.runningIO(1);

                if (currentProcess.getIOTime() <= 0) {

                    queue.remove(0);
                    
                    System.out.println("\nTermino proceso " + currentProcess.getPID() + " en el IO.");

                    ProcessTableModel model = (ProcessTableModel) interfaz.IOTable.getModel();

                    int row = model.findProcess(currentProcess.getPID());

                    if (row >= 0)
                        model.removeProcess(row);
                    
                    currentProcess.setNeedsIO(false);
                    ((ProcessTableModel)interfaz.doneTable.getModel()).addProcess(currentProcess);
//                    
                    if (queue.size() > 0) 
                        currentProcess = queue.get(0);
                    else
                        currentProcess = null;
                } 
            } else {
                 interfaz.processIOLabel.setText("None");
            }
        }
    }
    public void addProcess(Process process) {

        queue.add(process);

        if (currentProcess == null) {
            currentProcess = queue.get(0);
            IOAvailable = false;
        }
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
        return queue.get(0);
    }
    
    public int size() {
        return queue.size();
    }
    
    
    
}
