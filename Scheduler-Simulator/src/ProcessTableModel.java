
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


    public class ProcessTableModel extends AbstractTableModel {

        private List<Process> processList;

        public ProcessTableModel(List<Process> processes) {
            this.processList = new ArrayList<>(processes);
        }
        
        public ProcessTableModel() {}

        @Override
        public int getRowCount() {
            return processList.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            String name = "??";
            switch (column) {
                case 0:
                    name = "PID";
                    break;
                case 1:
                    name = "Priority";
                    break;
                case 2:
                    name = "Type";
                    break;
                case 3:
                    name = "Timer";
                    break;
            }
            return name;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class type = String.class;
            switch (columnIndex) {
                case 0:
                case 1:
                    type = Integer.class;
                    break;
            }
            return type;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Process process = processList.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    value = process.getPID();
                    break;
                case 1:
                    value = process.getDynamicPriority();
                    break;
                case 2:
                    value = process.getProcessType();
                case 3:
                    value = process.getTotalTime();
            }
            return value;
        }            
    }  