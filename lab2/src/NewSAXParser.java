import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NewSAXParser {

    public static void main(String[] args) throws Exception {
        XSDValidation val = new XSDValidation();
        val.validate();

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();
        parser.parse("gun.xml", handler);

        handler.gunList.sort(new Sorting());
        for ( Gun g : handler.gunList){
            System.out.println(g);
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
            case "gun":
                gun = new Gun();
                break;
            case "TTC":
                gun.ttc = new TTC();
                break;
            case "typeGun":
                gun.setType("gun");
                break;
            case "typeRiffle":
                gun.setType("riffle");
                break;

        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        switch(qName){
            case "gun":
                gunList.add(gun);
                break;
            case "origin":
                gun.setOrigin(content);
                break;
            case "handy":
                gun.setHandy(content);
                break;
            case "model":
                gun.setModel(content);
                break;
            case "range":
                gun.ttc.setRange(Integer.parseInt(content), gun.getType());
                break;
            case "optics":
                gun.ttc.setOptics(content, gun.getType());
                break;
            case "collar":
                gun.ttc.setCollar(content);
                break;
            case "sightingRange":
                gun.ttc.setSightingRange(Integer.parseInt(content));
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }

}