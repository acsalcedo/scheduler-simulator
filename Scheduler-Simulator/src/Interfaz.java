
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ScrollPaneLayout;
import static javax.swing.SwingConstants.*;
import javax.swing.table.*;


public class Interfaz extends javax.swing.JFrame {

    String[] columnProcessTable = {"PID", "Priority", "Type", "Timer"};
    ProcessTableModel readyList;
    ProcessTableModel expiredList;
    ProcessTableModel IOQueue;
    ProcessTableModel doneList;
    
    public Interfaz() {
        
        initComponents();
        ReadXML xml = new ReadXML();
        xml.getXML("src/prueba.xml");

        readyList = new ProcessTableModel(xml.processList);
        expiredList = new ProcessTableModel(xml.processList);
        IOQueue = new ProcessTableModel(xml.processList);
        doneList = new ProcessTableModel(xml.processList);

        setTitle("Linux Scheduler 2.6 Simulator");
        JLabel title = new JLabel("Linux Scheduler 2.6 Simulator");
        title.setHorizontalAlignment(CENTER);
        title.setVerticalAlignment(TOP);
        title.setFont(new Font("Courier New", Font.BOLD, 20));
        
        setLayout(new CardLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.add(title);
        
        Dimension tableDim = new Dimension(300,100);
        Dimension labelDim = new Dimension(300,20);
        JTable readyTable = new JTable(readyList);
        readyTable.setSize(100,100);
        
        setPreferredSize(new Dimension(500,700));
        JScrollPane readyScrollPane = new JScrollPane(readyTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        readyScrollPane.setPreferredSize(tableDim);

        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout());
        timerPanel.setPreferredSize(new Dimension(500,50));
        
        JButton timerButton = new JButton();
        timerButton.setPreferredSize(new Dimension(20,20));

        JLabel timerLabel = new JLabel("Timer: ");
        timerPanel.add(timerLabel);
        timerPanel.add(timerButton);
        
        JPanel currentPanel = new JPanel();
        currentPanel.setLayout(new FlowLayout());
        currentPanel.setPreferredSize(new Dimension(500,50));
        
        JLabel cpuLabel = new JLabel("Current CPU Process: ");
        JLabel currentIOLabel = new JLabel("Current IO Process: ");
        
        currentPanel.add(cpuLabel);
        currentPanel.add(currentIOLabel);
        
        mainPanel.add(timerPanel);
        mainPanel.add(currentPanel);
        JLabel readyLabel = new JLabel("Ready List");
        JPanel readyPanel = new JPanel();
        
        readyPanel.setPreferredSize(labelDim);
        readyPanel.add(readyLabel);
        
        mainPanel.add(readyPanel);
        mainPanel.add(readyScrollPane);
        
        JTable expiredTable = new JTable(expiredList);
        JScrollPane expiredScrollPane = new JScrollPane(expiredTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        expiredScrollPane.setPreferredSize(tableDim);
        
        JLabel expiredLabel = new JLabel("Expired List");
        JPanel expiredPanel = new JPanel();
        
        expiredPanel.setPreferredSize(labelDim);
        expiredPanel.add(expiredLabel);
        
        mainPanel.add(expiredPanel);
        mainPanel.add(expiredScrollPane);
        
        JTable IOTable = new JTable(IOQueue);
        JScrollPane IOScrollPane = new JScrollPane(IOTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        IOScrollPane.setPreferredSize(tableDim);
 
        JLabel IOLabel = new JLabel("IO Queue");
        JPanel IOPanel = new JPanel();
        
        IOPanel.setPreferredSize(labelDim);
        IOPanel.add(IOLabel);
        
        mainPanel.add(IOPanel);
        mainPanel.add(IOScrollPane);
                
        JTable doneTable = new JTable(doneList);
        JScrollPane doneScrollPane = new JScrollPane(doneTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        doneScrollPane.setPreferredSize(tableDim);
 
        JLabel doneLabel = new JLabel("Done List");
        JPanel donePanel = new JPanel();
        
        donePanel.setPreferredSize(labelDim);
        donePanel.add(doneLabel);
        
        mainPanel.add(donePanel);
        mainPanel.add(doneScrollPane);
        
        
        add(mainPanel);
        pack();
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

            System.out.println("Hola");      
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }
}
