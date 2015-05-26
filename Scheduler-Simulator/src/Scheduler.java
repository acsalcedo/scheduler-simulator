
public class Scheduler extends Thread {

    private boolean initialize = true;
    private final int priority_RT;
    private final int interruptInterval; // En milisegundos
    private RunQueue cpu;
    Interfaz interfaz;

    Scheduler(int interruptInterval, int priority_RT, RunQueue cpu, Interfaz interfaz){
        this.interruptInterval = interruptInterval;
        this.priority_RT = priority_RT;
        this.cpu = cpu;
        this.interfaz = interfaz;
        new Thread(this, "schedule");
    }

    public void run() {
        while (true)
            schedule(cpu);
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

            // Retirar un proceso del Runqueue
            if (current.getState().equals("EXIT_DEAD")){
                System.out.printf("\nRetirar proceso %d del RunQueue", current.getPID());
                next = cpu.removeActiveProcess(oldPriority);
                ProcessTableModel model = (ProcessTableModel) interfaz.readyTable.getModel();
                
                int row = model.findProcess(current.getPID());
                if (row >= 0)
                    model.removeProcess(row);

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
                return true;
            }

            // Intercambiar las listas Active y Expired
            if ((cpu.isEmptyActiveProcess()) && (!cpu.isEmptyExpiredProcess())){

                cpu.exchangeActiveExpiredProcesses();
                inicializar(cpu);
                return true;
            } else {
                System.out.printf("\n\nColocar proceso Swapper ");
                // Colocar el proceso swapper
                cpu.setIdleOnCurrent();
                return true;
            }

        }
        // Falta caso FIFO
        //Caso de reemplazo de proceso.
        return false;
    }

    public boolean schedule_tick(RunQueue cpu){

        Process current= cpu.getCurrentProcess();
        Process temp;
        PriorityArray activeProceses;

        if (current != null){
            int timeUpdate = current.getTimeSlice();
            
            System.out.println("TIMESLICEEEEE : " + timeUpdate);
            
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
                        //AQUI DEBO QUITAR DE INTERFAZ
//                                removeColumn(row);
                        // Falta recalcular la prioridad dinamica
                        cpu.addExpiredProcess(temp, temp.getDynamicPriority());
                        //cpu.printExpiredProcesses();
                        //cpu.printActiveProcesses();
                    }
                    break;
                case "RR":
                    System.out.printf("\nPolitica RR");
                    if (timeUpdate > 0) {
                        timeUpdate -= this.interruptInterval;
                        System.out.println("Decrementando time slice de proceso " + 
                                            current.getPID() + " timeslice: " + timeUpdate);
                        current.setTimeSlice(timeUpdate);
                        current.procesar(this.interruptInterval);
                    } else {
                        System.out.printf("\nProceso sin timeslice: %d", current.getPID());
                        baseTime(current);
                        current.setNeeds_ReSched(true);
                        // Los RT se consideran Active nunca van a Expired
                        temp = cpu.removeActiveProcess(current.getDynamicPriority());
                        cpu.addActiveProcess(temp, current.getDynamicPriority());
                    }
                    break;
                case "FIFO":
                    // Si se acaba su tiempo de CPU, cambia de proceso.
                    if (!current.getProcessType().equals("IDLE")) {
                        current.procesar(interruptInterval);
//                        if (current.getTotalTime() <= 0) {
//                           
//                            ProcessTableModel model = (ProcessTableModel) interfaz.readyTable.getModel();
//                            int row = model.findProcess(current.getPID());
//
//                            if (row >= 0)
//                                model.removeProcess(row);
//                            
//                            cpu.removeActiveProcess(current.getDynamicPriority());
////                            cpu.setCurrentProcess(null);
//                        }
                        System.out.printf("\nPolitica FIFO");
                    }
            }
            return true;
        }
        return false;
    }
}
