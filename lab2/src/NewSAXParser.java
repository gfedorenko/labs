import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NewSAXParser {

    public static void main(String[] args) throws Exception {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();
        parser.parse(ClassLoader.getSystemResourceAsStream("xml/employee.xml"),
                handler);

        //Printing the list of employees obtained from XML
        for ( Gun gun : handler.gunList){
            System.out.println(gun);
        }
    }
}
/**
 * The Handler for SAX Events.
 */
class SAXHandler extends DefaultHandler {

    ArrayList<Gun> gunList = new ArrayList<>();
    Gun gun = null;
    String content = null;
    @Override
    //Triggered when the start of tag is found.
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {

        switch(qName){
            //Create a new Employee object when the start tag is found
            case "gun":
                gun = new Gun();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        switch(qName){
            //Add the employee to list once end tag is found
            case "employee":
                gunList.add(gun);
                break;
            //For all other end tags the employee has to be updated.
            case "origin":
                gun.setOrigin(content);
                break;
            case "handy":
                gun.setHandy(content);
                break;
            case "model":
                gun.setModel(content);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }

}