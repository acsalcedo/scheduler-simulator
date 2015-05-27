
import java.util.ArrayList;

public class InputOutput extends Thread {
    
    private Process currentProcess = null;
    private boolean IOAvailable = true;
    ArrayList<Process> queue = new ArrayList<Process>();
    private int interruptInterval = 0;
    private Interfaz interfaz;
    
    /**
     * @brief Constructor de la Entrada/Salida.
     * @param interruptInterval Intervalo del timer de la Entrada/Salida
     * @param interfaz  Variable donde se almacena la interfaz
     */
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

                /* Si el proceso actual en el IO termino, se elimina de las tabla del IO
                   de la interfaz y se agrega a la tabla de los procesos terminados. */
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
            } else
                 interfaz.processIOLabel.setText("None");
        }
    }
    
    /**
     * @brief Se agrega el proceso dado a la cola de Entrada/Salida.
     * @param process Proceso a agregar.
     */
    public void addProcess(Process process) {

        queue.add(process);

        if (currentProcess == null) {
            currentProcess = queue.get(0);
            IOAvailable = false;
        }
    }
    
    /**
     * @brief Devuelve el proceso actual en el IO.
     * @return Proceso actual del IO.
     */
    public Process getCurrentProcess() {
        return currentProcess;
    }
    
    /**
     * @brief Asigna el proximo proceso en la cola como el proceso actual.
     */
    public void setNextProcess() {
        
        if (queue.isEmpty()) {
            IOAvailable = true;
            currentProcess = null;
        } else
            currentProcess = queue.remove(0);
    }
    
    /**
     * @brief Devuelve el proximo proceso en la cola del IO.
     * @return El proximo proceo en la cola del IO.
     */
    public Process getNextProcess() {
        return queue.get(0);
    }
    
    /**
     * @brief Devuelve el tamano de la cola de la Entrada/Salida.
     * @return El tamano de la cola del IO. 
     */
    public int size() {
        return queue.size();
    }
}
