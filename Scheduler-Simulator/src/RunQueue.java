public class RunQueue {

    private PriorityArray activeProcesses; /**< Apuntador a los prcesos activos. */
    private PriorityArray expiredProcesses; /**< Apuntador a los procesos expirados. */
    private PriorityArray[] queue = new PriorityArray[2]; /**< Arreglo de los priority arrays. */
    private Process currentProcess; /**< Proceso actual. */
    private Process nextProcess; /**< Proximo proceso. */
    private Process idleProcess; /**< Proceso swapper. */


    public RunQueue() {
        queue[0] = new PriorityArray();
        queue[1] = new PriorityArray();
        activeProcesses = queue[0];
        expiredProcesses = queue[1];
        idleProcess = new Process("FIFO","IDLE");
    }

    /**
     * @brief Intercambia el apuntador de los procesos activos con el de los expirados. 
     */
    synchronized public void exchangeActiveExpiredProcesses() {
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

    public void setIdleOnCurrent() {
        currentProcess = idleProcess;
    }

    synchronized public void addActiveProcess(Process process, int priority) {
        activeProcesses.addProcess(process, priority);
    }

    synchronized public Process removeActiveProcess(int priority) {
        return activeProcesses.removeProcess(priority);
    }

    synchronized public void addExpiredProcess(Process process, int priority) {
        expiredProcesses.addProcess(process, priority);
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public boolean isEmptyActiveProcess(){
        return activeProcesses.isEmpty();
    }

    public boolean isEmptyExpiredProcess(){
        return expiredProcesses.isEmpty();
    }

    synchronized public void setCurrentProcess(Process process){
        currentProcess = process;
    }

    synchronized public void printActiveProcesses() {
        System.out.printf("\nPROCESOS ACTIVOS");
        activeProcesses.printTable();
    }

    synchronized public void printExpiredProcesses() {
        System.out.printf("\nPROCESOS EXPIRADOS");
        expiredProcesses.printTable();
    }

    public Process getHighestPriorityActive() {
        return activeProcesses.getHighestPriorityProcess();
    }
}
