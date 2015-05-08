
public class Scheduler {

    boolean initialize = true;

    public void baseTime(Process process) {
        int result;
        if (process.getTimeSlice()>= 120) {
            result = (140 - process.getTimeSlice())*20;
        } else {
            result = (140 - process.getTimeSlice())*5;
        }
        process.setTimeSlice(result);
    }

    public void calcDynamicPriority(Process process) {
        int result = Math.max(100, Math.min(process.getStaticPriority()+5, 139));
        process.setDynamicPriority(result);
    }

    // Funcion equivalente a schedule() de Linux 2.6
    public boolean schedule(RunQueue cpu){

        if (initialize){
            // Suponiendo que comienza inicia el sistema. Busca un proceso
            // Se elige el proceso de prioridad mas alta que suelen ser RT
            PriorityArray active = cpu.getActiveProcesses();
            Process newProcess = null;
            int i = 0;
            while ((i < active.getLengthBitmap()) && (active.isPriorityEmpty(i)))
                i++;

            newProcess = active.getProcess(i);
            cpu.setCurrentProcess(newProcess);
            initialize = false;
            return true;
        }
        return false;
        //Caso de reemplazo de proceso.
    }
}
