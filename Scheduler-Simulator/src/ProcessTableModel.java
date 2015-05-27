
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.*;
import javax.swing.event.*;

    public class ProcessTableModel implements TableModel{

        /** Contiene los valores de los procesos*/
        private LinkedList processList = new LinkedList();

        /** Lista de suscriptores */
        private LinkedList listeners = new LinkedList();

        /**
         * @brief Obtener el numero de filas de la tabla
         * @return el numero de filas del modelo
         */
        public int getRowCount() {
            return processList.size();
        }

        /**
         * @brief Obtener el numero de columnas de una fila de la tabla
         * @return numero de columnas de la tabla
         */
        public int getColumnCount() {
            return 4;
        }

        /**
         * @brief Devuelve el nombre de cada columna de la tabla.
         * @param    columnIndex    the index of the column.
         * @return  el nombre de la columna.
         */
        public String getColumnName(int column) {

            switch (column) {
                case 0:
                    return "PID";
                case 1:
                    return "Priority";
                case 2:
                    return "Type";
                case 3:
                    return "Timer";
            }
            return null;
        }

        /**
         * @brief Devuelve la clase del valor de cada columna
         * @param columnIndex  the index of the column
         * @return the common ancestor class of the object values in the model.
         */
        public Class getColumnClass(int columnIndex) {

            switch (columnIndex) {
                case 0:
                    return Integer.class;        /** PID*/
                case 1:
                    return Integer.class;
                default:
                    return Object.class;
            }
        }

        /**
         * @brief Funcion para obtener una fila de la tabla y los valores en cada
         * columna.
         * @param    rowIndex Un proceso
         * @param    columnIndex  datos de cada proceso.
         * @return  Un valor del proceso, que se encuentra en una columna.
         */
        public Object getValueAt(int rowIndex, int columnIndex) {

            Process process = (Process)(processList.get(rowIndex));
            switch (columnIndex) {
                case 0:
                    return process.getPID();
                case 1:
                    return process.getDynamicPriority();
                case 2:
                    return process.getSchedulerPolitic();
                case 3:
                    return process.getTotalTime();
            }
            return null;
        }

    /**
     * @param    rowIndex    the row whose value to be queried
     * @param    columnIndex    the column whose value to be queried
     * @return    true if the cell is editable
     * @see #setValueAt
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * @param    aValue         el nuevo valor
     * @param    rowIndex     La fila donde ingresa el nuevo valor
     * @param    columnIndex  La columna donde ingresa el nuevo valor
     * @see #getValueAt
     * @see #isCellEditable
     *
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Process process;
        process = (Process)(processList.get(rowIndex));

        switch (columnIndex){
            case 0:
                process.setPID((Integer)aValue);
                break;
            default:
                break;
        }

        TableModelEvent evento = new TableModelEvent (this, rowIndex, rowIndex,
            columnIndex);

        notifiesSubscribers (evento);
    }

    /**
     * @brief:  Agrega   Un proceso al final de la fila.
     * @param    process  Un proceso
     */
    public void addProcess (Process process){

        TableModelEvent event;
        processList.add(process);
        event = new TableModelEvent (this, this.getRowCount()-1,
                 this.getRowCount()-1, TableModelEvent.ALL_COLUMNS,
                 TableModelEvent.INSERT);

        // ... y avisando a los suscriptores
        notifiesSubscribers(event);
    }
    
    public int findProcess (int PID) {
         Iterator<Process> iter = processList.iterator();
         
         int i = 0;
         while (iter.hasNext()) {
             if (iter.next().getPID() == PID)
                 return i;
             i++;
         }
         return -1;
    }

    /**
     * @brief:  Borra el proceso de la fila.
     * @param    row  Un proceso
     */
    public void removeProcess (int row){

        processList.remove(row);
        TableModelEvent event = new TableModelEvent (this, row, row,
            TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

        notifiesSubscribers(event);
    }

    /**
     * @brief Agrega un suscriptor a la lista y es notificado cada evento nuevo.
     * @param list
     */
    public void addTableModelListener (TableModelListener list) {
        listeners.add (list);
    }

    /**
     * @brief Elimina un suscriptor de la lista que es notificada en cada cambio
     * @param    list         the TableModelListener
     */
     public void removeTableModelListener (TableModelListener list) {
        // Elimina los Suscriptores.
        listeners.remove (list);
    }

    /**
     * Pasa a los suscriptores el evento.
     * @param evento
     */
    public void notifiesSubscribers (TableModelEvent event){
        int i;
        for (i=0; i<listeners.size(); i++)
            ((TableModelListener)listeners.get(i)).tableChanged(event);
    }
}