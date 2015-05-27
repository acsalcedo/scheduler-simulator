
public class Process {

    private int PID; /**< PID de proceso. */
    private int staticPriority; /**< Prioridad estatica del proceso. */
    private int dynamicPriority; /**< Prioridad dinamica del proceso */
    private String schedulerPolitic; /**< Politica de planificacion del proceso. */
    private int initTime; /**< Tiempo de inicio del proceso. */
    private int totalTime;  /**< Tiempo total que debe estar en el CPU. */
    private int runningTime; /**< Tiempo que lleva corriendo. */
    private int timeInQueue; /**< Tiempo que lleva en la cola. */
    private int timeSlice; /**< Time slice del proceso. */
    private String state; /**< Estado del proceso. */
    private boolean needsIO; /**< Almacena si el proceso necesita utilizar el IO o no. */
    private int initIOTime; /**< Tiempo de inicio del IO. */
    private int IOTime; /**< El tiempo total que debe estar en el IO. */
    private boolean needs_ReSched; /**< Variable que determina si el proceso debe ser planificado nuevamente. */
    private String processType; /**< Tipo del proceso. */

    public Process() {}

    public Process(String schedulerPolitic, String processType) {
        this.schedulerPolitic = schedulerPolitic;
        this.processType = processType;
    }

     public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public void setStaticPriority(int staticPriority) {
        this.staticPriority = staticPriority;
    }

    public int getStaticPriority() {
        return staticPriority;
    }

    public void setDynamicPriority(int dynamicPriority) {
        this.dynamicPriority = dynamicPriority;
    }

    public int getDynamicPriority() {
        return dynamicPriority;
    }
    
    public int getInit_IOTime() {
        return initIOTime;
    }

    public void setinitIOTime(int init) {
        this.initIOTime = init;
    }


    public void setSchedulerPolitic(String schedulerPolitic) {
        this.schedulerPolitic = schedulerPolitic;
    }

    public String getSchedulerPolitic() {
        return schedulerPolitic;
    }

    public void setInitTime(int initTime) {
        this.initTime = initTime;
    }

    public int getInitTime() {
        return initTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void setNeeds_ReSched(boolean cent) {
        needs_ReSched = cent;
    }

    public boolean getNeeds_ReSched() {
        return needs_ReSched;
    }

    /**
     * @brief Metodo que simula la corrida del proceso.
     * @param decremento Tiempo que se le resta al tiempo total que debe correr el proceso.
     */
    public void procesar(int decremento){
        totalTime -= decremento;
        runningTime += decremento;

        if (totalTime <= 0){
            System.out.printf("\nProceso terminado. Cede el CPU");
            totalTime = 0;
            needs_ReSched = true;
            state = "EXIT_DEAD";
        }
    }
    
    /**
     * @brief Simula la corrida en el IO.
     * @param decremento Tiempo a restar del tiempo de IO.
     */
    public void runningIO(int decremento) {
        IOTime -= decremento;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setRunningTime(int runnningTime) {
        this.runningTime = runnningTime;
    }
    public int getRunningTime() {
        return runningTime;
    }

    public void setTimeInQueue(int timeInQueue) {
        this.timeInQueue = timeInQueue;
    }

    public int getTimeInQueue() {
        return timeInQueue;
    }

    public void setTimeSlice(int timeSlice) {
        this.timeSlice = timeSlice;
    }

    public int getTimeSlice() {
        return timeSlice;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setNeedsIO(boolean needsIO) {
        this.needsIO = needsIO;
    }

    public boolean isNeedsIO() {
        return needsIO;
    }

    public void setIOTime(int IOTime) {
        this.IOTime = IOTime;
    }

    public int getIOTime() {
        return IOTime;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessType() {
        return processType;
    }

    /**
     * @brief Imprime la informacion del proceso dado.
     */
    public void print() {

        System.out.println("\n   PID: " + PID +
                           "\n   Scheduler politic: " + schedulerPolitic +
                           "\n   Prioridad estatica: " + staticPriority +
                           "\n   Prioridad dinamica: " + dynamicPriority +
                           "\n   Timeslice: " + timeSlice +
                           "\n   Total Time: " + totalTime +
                           "\n   Init Time: " + initTime +
                           "\n   Needs IO? " + needsIO +
                           "\n   Init IO Time: " + initIOTime +
			   "\n   IO Time: " + IOTime);
    }
}
