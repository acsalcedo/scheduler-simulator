
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TableControl{

    ReadXML xml = new ReadXML();
	
    /** El modelo de la tabla */
    public ProcessTableModel model = null;
     
     /** Contador para agregar procesos a la tabla*/
     private static int count = 0;
    
    /**
     * @brief Constructor. Se le pasa el modeo de la tabla 
     *        y anade los procesos del XML inicial. 
     * @param model
     */
     public TableControl(ProcessTableModel model){
        
         this.model = model;
        
        
     }
     
    /** 
     * @brief Agrega un proceso al fina de la fila de la tabla
     */
     public void addRow(){
         Process process = new Process();
         /**process.setPID(Integer.toString(count));
                 
            "PID " +Integer.toString(count),
            "Priority "+ Integer.toString (count), 
            "Type "+ Integer.toString (count), 
            "Timer "+ Integer.toString (count), 
            count);*/
         
          model.addProcess(process);
         
         // Incrementa count para que el siguiente Process a agregar sea
         // distinto.
         count++;
     }
     
     public void addRow(Process process){
         /**process.setPID(Integer.toString(count));
                 
            "PID " +Integer.toString(count),
            "Priority "+ Integer.toString (count), 
            "Type "+ Integer.toString (count), 
            "Timer "+ Integer.toString (count), 
            count);*/
         
          model.addProcess(process);
         
         // Incrementa count para que el siguiente Process a agregar sea
         // distinto.
         count++;
     }
     
    /** 
     * @brief Elimina el primer proceso de la fila de la tabla
     */
    public void removeRow(){
         if (model.getRowCount() > 0)
            model.removeProcess(0);
     }
}