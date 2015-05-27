
import java.awt.*;
import static java.awt.GridBagConstraints.CENTER;
import javax.swing.*;

/**
 * Frame con toda la parte visual del programa.
 */
public class Interfaz extends JFrame {

    /** Modelo de la tabla */
    private ProcessTableModel model = null;

    /** Para modificar el modelo */
    private TableControl control = null;
    
    /**Modelos para la tabla de listos, expirados, I/O, terminado**/
    ProcessTableModel readyList;
    ProcessTableModel expiredList;

    public ProcessTableModel getReadyList() {
        return readyList;
    }

    public ProcessTableModel getExpiredList() {
        return expiredList;
    }

    public ProcessTableModel getIOQueue() {
        return IOQueue;
    }

    public ProcessTableModel getDoneList() {
        return doneList;
    }
    
    ProcessTableModel IOQueue;
    ProcessTableModel doneList;
    
    ReadXML xml = new ReadXML();
    
    JLabel title, timerLabel, readyLabel, expiredLabel, IOLabel, doneLabel;
    JLabel currentIOLabel, cpuLabel, processIOLabel, processCPULabel, timerButton;
    JPanel mainPanel, timerPanel, currentPanel, currentIOPanel, buttonPanel;
    JPanel readyPanel, expiredPanel, IOPanel, donePanel;
    JTable readyTable, expiredTable, IOTable, doneTable;
    JScrollPane readyScrollPane, expiredScrollPane, IOScrollPane, doneScrollPane;
	 
     /**
      * Constructor que recibe el modelo de la tabla y el control. Guarda ambos
      * y llama al metodo construyeVentana() que se encarga de crear los
      * componentes.
      */
     public Interfaz(ProcessTableModel model, TableControl control,
                     ProcessTableModel modelExpired, TableControl controlExpired,
                     ProcessTableModel modelIO, TableControl controlIO,
                     ProcessTableModel modelDone, TableControl controlDone){
         
         readyTable = new JTable(model);
         expiredTable = new JTable(modelExpired);
         IOTable = new JTable(modelIO);
         doneTable = new JTable(modelDone);
         this.control = control;
         buildingWindow();
     }

     /**
      * @brief Crea los componentes de este panel.
      */
     private void buildingWindow(){

        /** Panel principal*/
        mainPanel = new JPanel();
        mainPanel.setSize(200, 200);
        mainPanel.setLocation(50, 500);
        
        /** Titulo */
        setTitle("Linux Scheduler 2.6 Simulator");
        title = new JLabel("Linux Scheduler 2.6 Simulator");
        title.setHorizontalAlignment(CENTER);
        title.setFont(new Font("Courier New", Font.BOLD, 20));
		 setTitle("Linux Scheduler 2.6 Simulator");
        
        setLayout(new CardLayout());
        
        Dimension tableDim = new Dimension(300,100);
        Dimension labelDim = new Dimension(300,20);
        
        readyTable.setSize(100,100);
        
        setPreferredSize(new Dimension(500,700));
        readyScrollPane = new JScrollPane(readyTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        readyScrollPane.setPreferredSize(tableDim);

        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout());
        timerPanel.setPreferredSize(new Dimension(300,40));
        
        timerButton = new JLabel();

        timerLabel = new JLabel("Timer: ");
        timerLabel.setBounds(10,20,300,30);
        
        timerPanel.add(timerLabel);
        timerPanel.add(timerButton);
        
        currentPanel = new JPanel();
        currentIOPanel = new JPanel();
        currentPanel.setLayout(new FlowLayout());
        currentIOPanel.setLayout(new FlowLayout());
        currentPanel.setPreferredSize(new Dimension(300,20));
        currentIOPanel.setPreferredSize(new Dimension(300,20));
        
        cpuLabel = new JLabel("Current CPU Process: ");
        processCPULabel = new JLabel("None");
        currentIOLabel = new JLabel("Current IO Process: ");

        processIOLabel = new JLabel("None");
        
        currentPanel.add(cpuLabel);
        currentPanel.add(processCPULabel);
        currentIOPanel.add(currentIOLabel);
        currentIOPanel.add(processIOLabel);
       
        readyLabel = new JLabel("Ready List");
        readyPanel = new JPanel();
        
        readyPanel.setPreferredSize(labelDim);
        readyPanel.add(readyLabel);

        JScrollPane expiredScrollPane = new JScrollPane(expiredTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        expiredScrollPane.setPreferredSize(tableDim);

        expiredLabel = new JLabel("Expired List");
        expiredPanel = new JPanel();
        
        expiredPanel.setPreferredSize(labelDim);
        expiredPanel.add(expiredLabel);
        
        JScrollPane IOScrollPane = new JScrollPane(IOTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        IOScrollPane.setPreferredSize(tableDim);
        
        IOLabel = new JLabel("IO Queue");
        IOPanel = new JPanel();
        
        IOPanel.setPreferredSize(labelDim);
        IOPanel.add(IOLabel);
        
        doneScrollPane = new JScrollPane(doneTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        doneScrollPane.setPreferredSize(tableDim);
 
        doneLabel = new JLabel("Done List");
        donePanel = new JPanel();
        donePanel.setPreferredSize(labelDim);
        donePanel.add(doneLabel);

        /**Agregandp los componentes al panel principal*/
        
        mainPanel.add(title);
        mainPanel.add(timerPanel);
        mainPanel.add(currentPanel);
        mainPanel.add(currentIOPanel);
        mainPanel.add(readyPanel);
        mainPanel.add(readyScrollPane);
        mainPanel.add(expiredPanel);
        mainPanel.add(expiredScrollPane);
        mainPanel.add(IOPanel);
        mainPanel.add(IOScrollPane);        
        mainPanel.add(donePanel);
        mainPanel.add(doneScrollPane);
        
        add(mainPanel);
        pack();
        setVisible(true);
    }
}