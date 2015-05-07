
public class Scheduler {

    boolean inicializar = true;

    public void tiempoBase(Process proceso) {
        int resultado;
        if (proceso.getTimeSlice()>= 120) {
            resultado = (140 - proceso.getTimeSlice())*20;
        } else {
            resultado = (140 - proceso.getTimeSlice())*5;
        }
        proceso.setTimeSlice(resultado);
    }

    public void calcPrioridadDinamica(Process proceso) {
        int resultado = Math.max(100, Math.min(proceso.getStaticPriority()+5, 139));
        proceso.setDynamicPriority(resultado);
    }

    // Funcion equivalente a schedule() de Linux 2.6
    public boolean planificar(RunQueue cpu){

        if (inicializar){
            // Suponiendo que comienza inicia el sistema. Busca un proceso
            // Se elige el proceso de prioridad mas alta que suelen ser RT
            PriorityArray activos = cpu.getActiveProcesses();
            Process nuevoProceso = null;
            int i = 0;
            while (i < activos.length) && isPriorityEmpty(i))
                i++;

            nuevoProceso = getProcess(i);
            cpu.setCurretProcess(nuevoProceso);
            inicializar = false;
            return true;
        }

        //Caso de reemplazo de proceso.
    }
}
