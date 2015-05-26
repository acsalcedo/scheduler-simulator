import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import javax.swing.*;

/**
 * Crea la vista, el modelo y el control del ejemplo de uso de la tabla.
 * Hereda de JFrame.
 */
public class ProcessAppletTable extends JApplet
{
    ReadXML xml = new ReadXML();
    /** Creates a new instance of PrincipalTabla */
    public void init(String file, final int interruptInterval) {
        // Crea el modelo
        ProcessTableModel model        = new ProcessTableModel();
        ProcessTableModel modelExpired = new ProcessTableModel();
        ProcessTableModel modelIO      = new ProcessTableModel();
        ProcessTableModel modelDone    = new ProcessTableModel();
        
        // Crea el control, pasndole el modelo
        TableControl control        = new TableControl(model);
        TableControl controlExpired = new TableControl(modelExpired);
        TableControl controlIO      = new TableControl(modelIO);
        TableControl controlDone    = new TableControl(modelDone);
        
        // Crea la vista, pasandole el control
        
        final Interfaz interfaz = new Interfaz(model, control, modelExpired, controlExpired,
                                      modelIO, controlIO, modelDone, controlDone);
        
                 boolean execute = true;
        
        int i = 0;// interruptInterval = Integer.parseInt(argv[0]);
        xml.getXML(file);

        final RunQueue cpu1 = new RunQueue();
        final Scheduler scheduler = new Scheduler(interruptInterval,99,cpu1,interfaz);
        final InputOutput IO = new InputOutput(interruptInterval+150,interfaz);
        Iterator<Process> listIterator = xml.processList.iterator();

        System.out.println("\nNumero de procesos en RunQueue: " +xml.processList.size());

      //  ReadXML xml = new ReadXML();
        
        List<Process> processList = new ArrayList<>();

        while (listIterator.hasNext()) {
            Process elem;
            elem=listIterator.next();
            elem.setStaticPriority(120);
            elem.setPID(i++);
            elem.setState("TASK_RUNNING");
            if (elem.getTotalTime() > elem.getIOTime()){
                elem.setProcessType("CPU_BOUND");
            } else {
                elem.setProcessType("IO_BOUND");
            }
            scheduler.calcDynamicPriority(elem);
            scheduler.baseTime(elem);
            cpu1.addActiveProcess(elem, elem.getDynamicPriority());
            model.addProcess(elem);
            
        }
        
        cpu1.printActiveProcesses();
        System.out.printf("\nCOMIENZA EL SIMULADOR");

        scheduler.inicializar(cpu1);
        System.out.printf("\nPID del Proceso en CPU: %d \n", cpu1.getCurrentProcess().getPID());


        TimerTask timerTask = new TimerTask() {
            Integer i = 0;

            /**
              * Método al que Timer llamará cada segundo. Se encarga de avisar
              * a los observadores de este modelo.
              */
            public void run() {
                interfaz.timerButton.setText(i.toString());
                System.out.printf("\n\nInvocado scheduler_tick");            
                scheduler.schedule_tick(cpu1);
                i++;
            }
        };
        
        /*Funcion que decrementa el timer para activad I/O */
	TimerTask timerTask1 = new TimerTask() {
		public void run() {
                       IO.run();
		}
	};
        
        java.util.Timer timer = new java.util.Timer();
        java.util.Timer timer1 = new java.util.Timer();
        timer.scheduleAtFixedRate(timerTask, 0, scheduler.getInterruptInterval());
        timer1.scheduleAtFixedRate(timerTask1, 0, interruptInterval+150);
        scheduler.start();
//
        while (true) {}
        
        
        
       
    }
    
   
}