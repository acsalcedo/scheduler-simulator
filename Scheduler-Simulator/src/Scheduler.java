
public class Scheduler {

    private boolean initialize = true;
    private final int priority_RT;
    private final int interruptInterval; // En milisegundos

    Scheduler(int interruptInterval, int priority_RT){
        this.interruptInterval = interruptInterval;
        this.priority_RT = priority_RT;
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
        PriorityArray activeProcesses = cpu.getActiveProcesses();
        Process temp;
        int highPriority = activeProcesses.getHighestPriorityBitmap();
        temp = activeProcesses.getProcess(highPriority);

        if (temp != null)
            cpu.setCurrentProcess(temp);
    }

    // Funcion equivalente a schedule() de Linux 2.6
    public boolean schedule(RunQueue cpu){
        Process temp = null, next = null, current = null;
        PriorityArray activeProcesses;
        int highPriority;

        // Caso (por razones de prueba ciclar los procesos)
        // Realizar bloqueo del runqueue cuando schedule sea implementado como hilo
        System.out.printf("\nInvocado schedule");
        current = cpu.getCurrentProcess();
        if (current.getNeeds_ReSched()) {
            activeProcesses = cpu.getActiveProcesses();
            highPriority = activeProcesses.getHighestPriorityBitmap();
            if (highPriority == current.getDynamicPriority()) {
                System.out.printf("\nPrioridad sin cambio: %d", current.getDynamicPriority());
                next = activeProcesses.getProcess(current.getDynamicPriority());
                // Lista vacia
                if (next == null) {
                    System.out.printf("\nLISTA VACIA");
                }
                System.out.printf("\nCAMBIAR PROCESO. \nActual: %d \nProximo: %d",
                              current.getPID(), next.getPID());

                current.setNeeds_ReSched(false);
                cpu.setCurrentProcess(next);
                return true;
            } else {
                // Expropiacion sobre current
                if (highPriority > current.getDynamicPriority()){
                    System.out.printf("\nPrioridad nueva: %d", highPriority);
                    System.out.printf("\nPrioridad anterior: %d", current.getDynamicPriority());
                    next = activeProcesses.getProcess(highPriority);
                    System.out.printf("\nCAMBIAR PROCESO. \nActual: %d \nProximo: %d",
                                  current.getPID(), next.getPID());

                    current.setNeeds_ReSched(false);
                    cpu.setCurrentProcess(next);
                    return true;
                    // Dejar que un proceso de menor prioridad acceda al CPU
                } else if (activeProcesses.isPriorityEmpty(current.getDynamicPriority())) {
                    System.out.printf("\nPrioridad nueva: %d", highPriority);
                    System.out.printf("\nPrioridad anterior: %d", current.getDynamicPriority());
                    next = activeProcesses.getProcess(highPriority);
                    System.out.printf("\nCAMBIAR PROCESO. \nActual: %d \nProximo: %d",
                                  current.getPID(), next.getPID());

                    current.setNeeds_ReSched(false);
                    cpu.setCurrentProcess(next);
                    return true;
                }

                // Intercambiar Active y Expired processes
                if (cpu.isEmptyActiveProcess()){
                    System.out.printf("\nNO HAY MAS PROCESOS");
                    cpu.exchangeActiveExpiredProcesses();
                    inicializar(cpu);
                }
            }
        }
        // Falta caso FIFO
        //Caso de reemplazo de proceso.
        return false;
    }

    public boolean schedule_tick(RunQueue cpu, Scheduler sched){

        Process current= cpu.getCurrentProcess();
        Process temp;
        PriorityArray activeProceses;

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
                    } else {
                        System.out.printf("\nProceso expirado: %d", current.getPID());
                        baseTime(current);
                        current.setNeeds_ReSched(true);
                        temp = cpu.removeActiveProcess(current.getDynamicPriority());
                        // Falta recalcular la prioridad dinamica
                        cpu.addExpiredProcess(temp, temp.getDynamicPriority());
                        //cpu.printExpiredProcesses();
                        //cpu.printActiveProcesses();
                        sched.schedule(cpu);
                    }
                    break;
                case "RR":
                    System.out.printf("\nPolitica RR");
                    if (timeUpdate > 0) {
                        timeUpdate -= this.interruptInterval;
                        System.out.printf("\nDecrementando timeslice de proceso %d",
                                          current.getPID());
                        current.setTimeSlice(timeUpdate);
                    } else {
                        System.out.printf("\nProceso expirado: %d", current.getPID());
                        baseTime(current);
                        current.setNeeds_ReSched(true);
                        // Los RT se consideran Active nunca van a Expired
                        temp = cpu.removeActiveProcess(current.getDynamicPriority());
                        cpu.addActiveProcess(temp, current.getDynamicPriority());
                        //cpu.printExpiredProcesses();
                        //cpu.printActiveProcesses();
                        sched.schedule(cpu);
                    }
                    break;
                case "FIFO":
                    System.out.printf("\nPolitica FIFO");
                    current.procesar(interruptInterval);
                    // Si se acaba su tiempo de CPU, cambia de proceso.
                    if (current.getNeeds_ReSched())
                        sched.schedule(cpu);
            }
            return true;
        }
        return false;
    }
}
