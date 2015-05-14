
public class Process {
    private int PID;
    private int staticPriority;
    private int dynamicPriority;
    private String schedulerPolitic; // TODO enum
    private int initTime;
    private Process proximo;
    private Process anterior;
    private int totalTime;  // Tiempo de CPU
    private int runnningTime;
    private int timeInQueue;
    private int timeSlice;
    private String state; // TODO enum
    private boolean needsIO;
    private int IOTime;
    private boolean needs_ReSched;
    private String processType; // TODO enum

    public Process() {}

    public Process(String schedulerPolitic, String processType) {
        this.schedulerPolitic = schedulerPolitic;
        this.processType = processType;
        this.anterior = null;
        this.proximo = null;
        // TODO set PID
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

    public void procesar(int decremento){
        if (!(schedulerPolitic.equals("FIFO"))) {
            totalTime -= timeSlice;
        } else {
            totalTime -= decremento;
        }

        if (totalTime <= 0){
            totalTime = 0;
            needs_ReSched = true;
            /*if (needsIO) {

            }*/
        }
    }


    public int getTotalTime() {
        return totalTime;
    }

    public void setRunnningTime(int runnningTime) {
        this.runnningTime = runnningTime;
    }
    public int getRunnningTime() {
        return runnningTime;
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

    public void print() {

        System.out.println("\n   PID: " + PID +
                           "\n   Scheduler politic: " + schedulerPolitic +
                           "\n   Prioridad estatica: " + staticPriority +
                           "\n   Prioridad dinamica: " + dynamicPriority +
                           "\n   Timeslice: " + timeSlice +
                           "\n   Total Time: " + totalTime +
                           "\n   Init Time: " + initTime +
                           "\n   Needs IO? " + needsIO +
                           "\n   Init IO Time: " + IOTime);
    }




}
