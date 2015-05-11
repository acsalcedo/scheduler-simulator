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

    public PriorityArray getActiveProcesses() {
        return activeProcesses;
    }

    public PriorityArray getExpiredProcesses() {
        return expiredProcesses;
    }


    public void addActiveProcess(Process process, int priority) {
        activeProcesses.addProcess(process, priority);
    }

    public Process removeActiveProcess(int priority) {
        return activeProcesses.removeProcess(priority);

    }

    public void addExpiredProcess(Process process, int priority) {
        expiredProcesses.addProcess(process, priority);
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process process){
        currentProcess = process;
    }

    public void printActiveProcesses() {
        System.out.printf("\nPROCESOS ACTIVOS");
        activeProcesses.printTable();
    }

    public void printExpiredProcesses() {
        System.out.printf("\nPROCESOS EXPIRADOS");
        expiredProcesses.printTable();
    }

    public Process getHighestPriorityActive() {
        return activeProcesses.getHighestPriorityProcess();
    }


}
