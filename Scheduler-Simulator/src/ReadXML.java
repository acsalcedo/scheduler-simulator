
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXML extends DefaultHandler {

    public List<Process> processList = new ArrayList<Process>();
    private Process currentProcess = new Process();

    /**
     * Lee la informacion del procesos definidos en un archivo XML y 
     * los crea y carga en una lista. 
     * @param file Archivo a leer. 
     */
    public void getXML(String file) {

        try {

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            DefaultHandler defaultHandler = new DefaultHandler() {

                boolean processTag = false;
                boolean politicTag = false;
                boolean totalTimeTag = false;
                boolean initTimeTag = false;
                boolean needsIOTag = false;
                boolean initIOTag = false;
                boolean ioTag = false;

                @Override
                public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {

                    if (qName.equalsIgnoreCase("PROCESS")) {
                        processTag = true;
                        currentProcess = new Process();
                    }
                    if (qName.equalsIgnoreCase("SCHEDULERPOLITIC"))
                        politicTag = true;
                    if (qName.equalsIgnoreCase("TOTALTIME"))
                        totalTimeTag = true;
                    if (qName.equalsIgnoreCase("INITTIME"))
                        initTimeTag = true;
                    if (qName.equalsIgnoreCase("NEEDSIO"))
                        needsIOTag = true;
                    if (qName.equalsIgnoreCase("INITIOTIME"))
                        initIOTag = true;
                    if (qName.equalsIgnoreCase("IOTIME"))
                        ioTag = true;
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {

                    if (politicTag)
                        currentProcess.setSchedulerPolitic(new String(ch,start,length));
                    if (totalTimeTag)
                        currentProcess.setTotalTime(Integer.parseInt(new String(ch,start,length)));
                    if (initTimeTag)
                        currentProcess.setInitTime(Integer.parseInt(new String(ch,start,length)));
                    if (needsIOTag)
                        currentProcess.setNeedsIO(Boolean.parseBoolean(new String(ch,start,length)));
                    if (initIOTag)
                        currentProcess.setinitIOTime(Integer.parseInt(new String(ch,start,length)));
                    if (ioTag)
                        currentProcess.setIOTime(Integer.parseInt(new String(ch,start,length)));
                }


                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {

                    if (qName.equalsIgnoreCase("PROCESS")) {
                        processTag = false;
                        processList.add(currentProcess);
                    }
                    if (qName.equalsIgnoreCase("SCHEDULERPOLITIC"))
                        politicTag = false;
                    if (qName.equalsIgnoreCase("TOTALTIME"))
                        totalTimeTag = false;
                    if (qName.equalsIgnoreCase("INITTIME"))
                        initTimeTag = false;
                    if (qName.equalsIgnoreCase("NEEDSIO"))
                        needsIOTag = false;
                    if (qName.equalsIgnoreCase("INITIOTIME"))
                        initIOTag = false;
                    if (qName.equalsIgnoreCase("IOTIME"))
                        ioTag = false;
                }

            };

            saxParser.parse(file, defaultHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
