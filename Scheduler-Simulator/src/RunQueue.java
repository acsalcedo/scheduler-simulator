public class RunQueue {

    private PriorityArray activeProcesses;
    private PriorityArray expiredProcesses;
    private PriorityArray[] queue = new PriorityArray[2];
    private Process currentProcess;
    private Process nextProcess;
    private Process idleProcess;


    public RunQueue() {
        queue[0] = new PriorityArray();
        queue[1] = new PriorityArray();
        activeProcesses = queue[0];
        expiredProcesses = queue[1];
        idleProcess = new Process("idle","idle");
    }

    public void exchangeActiveExpiredProcesses() {
        PriorityArray exchange = activeProcesses;
        activeProcesses = expiredProcesses;
        expiredProcesses = exchange;
    }

    public PriorityArray getActiveProcesses(){
        return activeProcesses;
    }


    public void addActiveProcess(Process process, int priority) {
        activeProcesses.addProcess(process, priority);
    }

    public void addExpiredProcess(Process process, int priority) {
        expiredProcesses.addProcess(process, priority);
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurretProcess(Process process){
        currentProcess = process;
    }

    public void imprimirProcesosActivos() {
        activeProcesses.imprimirTabla();
    }


}
