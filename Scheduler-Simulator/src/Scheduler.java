
import java.util.LinkedList;

/**
 * Clase del planificador de Linux 2.6. 
 */

public class Scheduler extends Thread {

    private final int priority_RT; /**< Prioridad.*/
    private final int interruptInterval; /**< Intervalo de la interrupción del timer. */
    private RunQueue cpu; /**< Variable del cpu. */
    InputOutput IO; /**< Cola de la Entrada/Salida. */
    Interfaz interfaz; /**< Variable de la Interfaz del programa. */

    /**
     * @brief Constructor del planificador.
     * @param interruptInterval Intervalo de la interrupción del timer.
     * @param priority_RT
     * @param cpu
     * @param interfaz 
     */
    Scheduler(int interruptInterval, int priority_RT, RunQueue cpu, Interfaz interfaz){
        this.interruptInterval = interruptInterval;
        this.priority_RT = priority_RT;
        this.cpu = cpu;
        this.interfaz = interfaz;
        this.IO = new InputOutput(interruptInterval,interfaz);
        new Thread(this, "schedule");
    }

    public void run() {
        IO.start();
        while (true)
            schedule(cpu);
    }
    
    public Process getCurrentIOProcess() {
        
        if (IO.size() > 0) 
            return IO.getCurrentProcess();
        else
            return null;
    }

    public void baseTime(Process process) {
        int result;
        if (process.getStaticPriority()>= 120) {
            result = (140 - process.getStaticPriority())*20;
        } else {
            result = (140 - process.getStaticPriority())*5;
        }
        process.setTimeSlice(result);
    }

    public void calcDynamicPriority(Process process) {
        int result = 120;

        switch (process.getSchedulerPolitic()) {
            case "FIFO": // Unicamente para probar
                result = priority_RT - 10;
            break;
            case "RR":
                result = priority_RT;
            break;
            case "NORMAL":
                result = Math.max(100, Math.min(process.getStaticPriority()+5, 139));
            break;
        }
        process.setDynamicPriority(result);
    }

    public int getInterruptInterval() {
        return interruptInterval;
    }

    public void inicializar(RunQueue cpu){
        // Suponiendo que comienza inicia el sistema. Busca un proceso
        // Se elige el proceso de prioridad mas alta que suelen ser RT
        Process temp;
        temp =  cpu.getHighestPriorityActive();

        if (temp != null) {
            cpu.setCurrentProcess(temp);
            Integer pid = new Integer(temp.getPID());
            interfaz.processCPULabel.setText(pid.toString());
        }
    }

    // Funcion equivalente a schedule() de Linux 2.6
    public boolean schedule(RunQueue cpu){
        Process temp = null, next = null, current = null;
        PriorityArray activeProcesses;
        int newPriority, oldPriority;

        // Caso (por razones de prueba ciclar los procesos)
        // Realizar bloqueo del runqueue cuando schedule sea implementado como hilo
        
        
        
        current = cpu.getCurrentProcess();
        if (current.getNeeds_ReSched()) {
            System.out.printf("\nInvocado schedule");
            activeProcesses = cpu.getActiveProcesses();
            oldPriority = current.getDynamicPriority();
            cpu.printExpiredProcesses();

            // Retirar un proceso del Runqueue
            if (current.getState().equals("EXIT_DEAD")){
                System.out.printf("\nRetirar proceso %d del RunQueue", current.getPID());
                next = cpu.removeActiveProcess(oldPriority);
                ProcessTableModel model = (ProcessTableModel) interfaz.readyTable.getModel();
                
                int row = model.findProcess(next.getPID());
                
                if (row >= 0)
                    model.removeProcess(row);
                
                if (next.isNeedsIO()) {
                            
                    System.out.printf("\nProceso en Entrada y Salida");

                    IO.addProcess(next);
                    ((ProcessTableModel)interfaz.IOTable.getModel()).addProcess(next);
                        
                } else
                    ((ProcessTableModel)interfaz.doneTable.getModel()).addProcess(next);
                
                next = null;
                cpu.printActiveProcesses();
            }

            newPriority = activeProcesses.getHighestPriorityBitmap();
            if (newPriority == oldPriority) {
                System.out.printf("\nPrioridad sin cambio: %d", oldPriority);
                next = activeProcesses.getProcess(oldPriority);
                System.out.printf("\nCAMBIAR PROCESO. \nActual: %d \nProximo: %d",
                              current.getPID(), next.getPID());

                current.setNeeds_ReSched(false);
                cpu.setCurrentProcess(next);
                Integer pid = new Integer(next.getPID());
                interfaz.processCPULabel.setText(pid.toString());
                return true;
            }

            // Expropiacion sobre current
            if (newPriority > oldPriority && newPriority < 140){
                System.out.printf("\nPrioridad nueva: %d", newPriority);
                System.out.printf("\nPrioridad anterior: %d", oldPriority);
                next = activeProcesses.getProcess(newPriority);
                System.out.printf("\nCAMBIAR PROCESO. \nActual: %d \nProximo: %d",
                              current.getPID(), next.getPID());

                current.setNeeds_ReSched(false);
                cpu.setCurrentProcess(next);
                Integer pid = new Integer(next.getPID());
                interfaz.processCPULabel.setText(pid.toString());
                return true;
            }

            // Dejar que un proceso de menor prioridad acceda al CPU
            if (newPriority < oldPriority && activeProcesses.isPriorityEmpty(oldPriority)) {
                System.out.printf("\nPrioridad nueva: %d", newPriority);
                System.out.printf("\nPrioridad anterior: %d", oldPriority);
                next = activeProcesses.getProcess(newPriority);
                System.out.printf("\nCAMBIAR PROCESO. \nActual: %d \nProximo: %d",
                              current.getPID(), next.getPID());

                current.setNeeds_ReSched(false);
                cpu.setCurrentProcess(next);
                Integer pid = new Integer(next.getPID());
                interfaz.processCPULabel.setText(pid.toString());
                return true;
            }
            
           // cpu.printExpiredProcesses();
            int nroExpiredProcesess = cpu.getExpiredProcesses().getNumActiveProcesses();
            int nroActiveProcesess = cpu.getActiveProcesses().getNumActiveProcesses();
            // Intercambiar las listas Active y Expired
            System.out.println("nroActive: " +nroActiveProcesess+ "nroExpired: " +nroExpiredProcesess);
            
            if (((nroActiveProcesess==0) && (nroExpiredProcesess > 0)) ||
                (interfaz.expiredTable.getModel().getRowCount() > 0 && interfaz.readyTable.getModel().getRowCount() <= 0)){
                System.out.println("\nEXCHANGE ACTIVE AND EXPIRED");
                
                cpu.exchangeActiveExpiredProcesses();
                
                ProcessTableModel modelExpired = (ProcessTableModel) interfaz.expiredTable.getModel();
                ProcessTableModel modelActive = (ProcessTableModel) interfaz.readyTable.getModel();
                
                interfaz.readyTable.setModel(modelExpired);
                interfaz.expiredTable.setModel(modelActive);
                
                inicializar(cpu);
                return true;
            } else {
                System.out.printf("\n\nColocar proceso Swapper ");
                // Colocar el proceso swapper
                cpu.setIdleOnCurrent();
                interfaz.processCPULabel.setText("Idle");
                return true;
            }
        }
        return false;
    }

    public boolean schedule_tick(RunQueue cpu){

        Process current= cpu.getCurrentProcess();
        Process temp;

        if (current != null){
            int timeUpdate = current.getTimeSlice();
                        
            switch (current.getSchedulerPolitic()) {
                case "NORMAL":
                    System.out.printf("\nPolitica NORMAL");
                    
                    if (timeUpdate > 0) {
                        timeUpdate -= this.interruptInterval;
                        System.out.printf("\nDecrementando timeslice de proceso %d",
                                          current.getPID());
                        
                        current.setTimeSlice(timeUpdate);
                        current.procesar(this.interruptInterval);
                    
                    } else {
                        
                        System.out.printf("\nProceso expirado: %d", current.getPID());
                        baseTime(current);
                        current.setNeeds_ReSched(true);
                        temp = cpu.removeActiveProcess(current.getDynamicPriority());
                        
                        ProcessTableModel model = (ProcessTableModel) interfaz.readyTable.getModel();
                
                        int row = model.findProcess(temp.getPID());
                        
                        if (row >= 0)
                            model.removeProcess(row);

                        ((ProcessTableModel)interfaz.expiredTable.getModel()).addProcess(temp);
                        
                        cpu.addExpiredProcess(temp, temp.getDynamicPriority());
                    }
                    break;
                case "RR":
                    System.out.println("\nPolitica RR");
                    
                    if (timeUpdate > 0) {
                        timeUpdate -= this.interruptInterval;
                        System.out.println("Decrementando time slice de proceso " + 
                                            current.getPID());
                        current.setTimeSlice(timeUpdate);
                        current.procesar(this.interruptInterval);
                        
                    } else {
                        System.out.printf("\nProceso sin timeslice: %d", current.getPID());
                        
                        baseTime(current);
                        current.setNeeds_ReSched(true);
                        
                        // Los RR se consideran Active nunca van a Expired
                        temp = cpu.removeActiveProcess(current.getDynamicPriority());
                        cpu.addActiveProcess(temp, current.getDynamicPriority());
                        
                        ProcessTableModel model = (ProcessTableModel) interfaz.readyTable.getModel();
                
                        int row = model.findProcess(temp.getPID());
                        
                        if (row >= 0)
                            model.removeProcess(row);

                        model.addProcess(temp);
                    }
                    break;
                case "FIFO":
                    // Si se acaba su tiempo de CPU, cambia de proceso.
                    if (!current.getProcessType().equals("IDLE")) {
                        current.procesar(interruptInterval);
                        System.out.printf("\nPolitica FIFO");
                    }
            }
            return true;
        }
        return false;
    }
}
