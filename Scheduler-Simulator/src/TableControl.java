
public class TableControl{

    ReadXML xml = new ReadXML();
	
    /** El modelo de la tabla */
    public ProcessTableModel model = null;
     
     /** Contador para agregar procesos a la tabla*/
     private static int count = 0;
    
    /**
     * @brief Constructor. Se le pasa el modeo de la tabla 
     *        y anade los procesos del XML inicial. 
     * @param model Modelo de la tabla.
     */
     public TableControl(ProcessTableModel model){
         this.model = model;
     }
     
    /** 
     * @brief Agrega un proceso al final de la fila de la tabla
     */
     public void addRow(){
         Process process = new Process();         
         model.addProcess(process);
         count++;
     }
     
    /** 
     * @brief Agrega un proceso al final de la fila de la tabla
     */
     public void addRow(Process process){ 
        model.addProcess(process);
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