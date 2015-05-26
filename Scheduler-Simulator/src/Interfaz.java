/*
 * Ejemplo de manejo de JTable y TableModel
 */

import java.awt.*;
import static java.awt.GridBagConstraints.CENTER;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JSplitPane.TOP;

/**
 * Panel con toda la parte visual del ejemplo. Crea un JScrollPane con el JTable
 * en su interior y dos JButton para a?adir y borrar elementos de la tabla.
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
//    JButton timerButton,addProcButton;
	 
     /**
      * Constructor que recibe el modelo de la tabla y el control. Guarda ambos
      * y llama al metodo construyeVentana() que se encarga de crear los
      * componentes.
     * @param model
     * @param control
      */
     public Interfaz(ProcessTableModel model, TableControl control,
                     ProcessTableModel modelExpired, TableControl controlExpired,
                     ProcessTableModel modelIO, TableControl controlIO,
                     ProcessTableModel modelDone, TableControl controlDone){
         
         //super ("Ejemplo 1 GridBagLayout");
         //this.getContentPane().setLayout(new GridBagLayout());
         
         //GridBagConstraints constraints = new GridBagConstraints();
	 //super (new GridBagLayout());
         readyTable = new JTable(model);
         expiredTable = new JTable(new ProcessTableModel());
         IOTable = new JTable(new ProcessTableModel());
         doneTable = new JTable(new ProcessTableModel());
         this.control = control;
         buildingWindow();
     }

     /**
      * Crea los componentes de este panel.
      * Un JScrollPane, el JTable que va dentro y dos JButton para a?adir y
      * quitar elementos del JTable.11111
      */
     private void buildingWindow(){

        /** Panel principal*/
        mainPanel = new JPanel();
        //mainPanel.setBackground(new java.awt.Color(10, 16, 150));
        mainPanel.setSize(200, 200);
        //mainPanel.setResizable(false);
        mainPanel.setLocation(50, 500);
        
        /** Titulo */
        setTitle("Linux Scheduler 2.6 Simulator");
        title = new JLabel("Linux Scheduler 2.6 Simulator");
        title.setHorizontalAlignment(CENTER);
        //title.setVerticalAlignment(CENTER);
        title.setFont(new Font("Courier New", Font.BOLD, 20));
		 setTitle("Linux Scheduler 2.6 Simulator");
        
        setLayout(new CardLayout());
        
        Dimension tableDim = new Dimension(300,100);
        Dimension labelDim = new Dimension(300,20);
        
       // readyTable = new JTable(model);
        readyTable.setSize(100,100);
        
        setPreferredSize(new Dimension(500,700));
        readyScrollPane = new JScrollPane(readyTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        readyScrollPane.setPreferredSize(tableDim);

        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout());
        timerPanel.setPreferredSize(new Dimension(300,40));
        
        timerButton = new JLabel();
//        timerButton.setPreferredSize(new Dimension(40,40));

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
        //cpuLabel.setBounds(10,1000,1000,30);
        processCPULabel = new JLabel("None");
        currentIOLabel = new JLabel("Current IO Process: ");
        //cpuLabel.setBounds(10,100,1000,30);

        processIOLabel = new JLabel("None");
        
        currentPanel.add(cpuLabel);
        currentPanel.add(processCPULabel);
        currentIOPanel.add(currentIOLabel);
        currentIOPanel.add(processIOLabel);
       
        /**Boton para agregar a la cola de listos*/
//        addProcButton = new JButton("Agregar Listos");
//        addProcButton.setBounds(110, 110, 100, 40);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(300,40));
       // buttonPanel.add(addProcButton);
        
//        addProcButton.addActionListener(new ActionListener() {
//          public void actionPerformed (ActionEvent evento){
//                 control.addRow();
//          }
//         });
        
        /**--------------------- Fin de Cola Listos --------------------------*/

        readyLabel = new JLabel("Ready List");
        readyPanel = new JPanel();
        
        readyPanel.setPreferredSize(labelDim);
        readyPanel.add(readyLabel);

//        expiredTable = new JTable(expiredList);
        JScrollPane expiredScrollPane = new JScrollPane(expiredTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        expiredScrollPane.setPreferredSize(tableDim);
        
        expiredLabel = new JLabel("Expired List");
        expiredPanel = new JPanel();
        
        expiredPanel.setPreferredSize(labelDim);
        expiredPanel.add(expiredLabel);
        
        
//        IOTable = new JTable(IOQueue);
        JScrollPane IOScrollPane = new JScrollPane(IOTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        IOScrollPane.setPreferredSize(tableDim);
 
        IOLabel = new JLabel("IO Queue");
        IOPanel = new JPanel();
        
        IOPanel.setPreferredSize(labelDim);
        IOPanel.add(IOLabel);
        
//        doneTable = new JTable(doneList);
        doneScrollPane = new JScrollPane(doneTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        doneScrollPane.setPreferredSize(tableDim);
 
        doneLabel = new JLabel("Done List");
        donePanel = new JPanel();
        donePanel.setPreferredSize(labelDim);
        donePanel.add(doneLabel);

        /**Agregandp los componentes al panel principal*/
        /* mainPanel.add(timerLabel);
        mainPanel.add(timerButton);
        mainPanel.add(cpuLabel);*/
        
        mainPanel.add(title);
        mainPanel.add(timerPanel);
        mainPanel.add(currentPanel);
        mainPanel.add(currentIOPanel);
        mainPanel.add(readyPanel);
        mainPanel.add(readyScrollPane);
        //mainPanel.add(buttonPanel);
        mainPanel.add(expiredPanel);
        mainPanel.add(expiredScrollPane);
        mainPanel.add(IOPanel);
        mainPanel.add(IOScrollPane);        
        mainPanel.add(donePanel);
        mainPanel.add(doneScrollPane);
        
        add(mainPanel);
        pack();
        setVisible(true);
        System.out.println("Hola");	 
    }
     
     
}