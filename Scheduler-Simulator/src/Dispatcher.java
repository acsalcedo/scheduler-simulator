
import java.util.Iterator;


public class Dispatcher {
        
    public static void main(String argv[]) {
        
        ReadXML xml = new ReadXML();
        xml.getXML("prueba.xml");
        
        Iterator<Process> listIterator = xml.processList.iterator();
        
        System.out.println(xml.processList.size());
        
        while (listIterator.hasNext()) {
            listIterator.next().print();
        }
    }

}
