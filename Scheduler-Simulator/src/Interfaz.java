
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
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
    ProcessTableModel DoneList;
    
    public Interfaz() {
        
        initComponents();
        ReadXML xml = new ReadXML();
        xml.getXML("src/prueba.xml");

        readyList = new ProcessTableModel(xml.processList);
        expiredList = new ProcessTableModel(xml.processList);
        IOQueue = new ProcessTableModel(xml.processList);
        DoneList = new ProcessTableModel(xml.processList);

        setTitle("Linux Scheduler 2.6 Simulator");
        JLabel title = new JLabel("Linux Scheduler 2.6 Simulator");
        title.setHorizontalAlignment(CENTER);
        title.setVerticalAlignment(TOP);
        
        setLayout(new CardLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.add(title);
        
        Dimension tableDim = new Dimension(300,100);
        Dimension labelDim = new Dimension(300,20);
        JTable readyTable = new JTable(readyList);
        readyTable.setSize(100,100);
        
        setPreferredSize(new Dimension(500,500));
        JScrollPane readyScrollPane = new JScrollPane(readyTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        readyScrollPane.setPreferredSize(tableDim);

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
