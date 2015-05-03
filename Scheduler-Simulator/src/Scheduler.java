
public class Scheduler {

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
}
