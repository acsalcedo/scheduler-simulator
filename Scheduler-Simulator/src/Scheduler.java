
public class Scheduler {

    boolean initialize = true;

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
        int result = Math.max(100, Math.min(process.getStaticPriority()+5, 139));
        process.setDynamicPriority(result);
    }

    // Funcion equivalente a schedule() de Linux 2.6
    public boolean schedule(RunQueue cpu){
        Process temp = null, next = null;
        PriorityArray activeProcesses;

        if (initialize){
            // Suponiendo que comienza inicia el sistema. Busca un proceso
            // Se elige el proceso de prioridad mas alta que suelen ser RT
            activeProcesses = cpu.getActiveProcesses();
            temp = activeProcesses.getProcess(activeProcesses.getHighestPriorityBitmap());

            if (temp != null)
                cpu.setCurrentProcess(temp);

            initialize = false;
            return true;
        }

        // Caso (por razones de prueba ciclar los procesos)
        System.out.printf("\nInvocado schedule");
        temp = cpu.getCurrentProcess();
        //System.out.printf("\nPrueba: %s", temp.getNeeds_ReSched());
        if (temp.getNeeds_ReSched()) {
            activeProcesses = cpu.getActiveProcesses();
            if (activeProcesses.getHighestPriorityBitmap() ==
                    temp.getDynamicPriority()){

                System.out.printf("\nPrioridad sin cambio: %d", temp.getDynamicPriority());
                next = activeProcesses.getProcess(temp.getDynamicPriority());
                // Lista vacia
                if (next == null) {
                    System.out.printf("\nLISTA VACIA");
                }
                System.out.printf("\nCAMBIAR PROCESO. \nActual: %d \nProximo: %d",
                              temp.getPID(), next.getPID());

                temp.setNeeds_ReSched(false);
                cpu.setCurrentProcess(next);
                return true;
            } else {
                System.out.printf("\nNO HAY MAS PROCESOS");
                cpu.setCurrentProcess(null); //Para finalizar el simulador
            }

        }

        //Caso de reemplazo de proceso.
        return false;
    }

    public boolean schedule_tick(RunQueue cpu, Scheduler sched){

        Process current= cpu.getCurrentProcess();
        Process temp;
        PriorityArray activeProceses;

        if (current != null){
            int timeUpdate = current.getTimeSlice();
            if (timeUpdate > 0) {
                timeUpdate = timeUpdate - 100 ;
                current.setTimeSlice(timeUpdate);
                System.out.printf("\nDecrementando timeSlice de proceso %d", current.getPID());
                if (timeUpdate <= 0) {
                    System.out.printf("\nProceso expirado: %d", current.getPID());
                    baseTime(current);
                    current.setNeeds_ReSched(true);
                    temp = cpu.removeActiveProcess(current.getDynamicPriority());

                    cpu.addExpiredProcess(temp, temp.getDynamicPriority());
                    //cpu.printExpiredProcesses();
                    //cpu.printActiveProcesses();
                    sched.schedule(cpu);
                    return true;
                }
            }

            if (timeUpdate == 0) {
                sched.schedule(cpu);
                return true;
            }
        }
        return false;
    }
}
