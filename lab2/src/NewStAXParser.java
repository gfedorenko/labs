import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

public class NewStAXParser {
    public static void main(String[] args) throws XMLStreamException {
        List<Gun> gunList = null;
        Gun currGun = null;
        String tagContent = null;
//        XMLInputFactory factory = XMLInputFactory.newInstance();
//        XMLStreamReader reader =
//                factory.createXMLStreamReader(
//                        ClassLoader.getSystemResourceAsStream("gun.xml"));

        XMLInputFactory factory = XMLInputFactory.newInstance();
        StreamSource source = new StreamSource("gun.xml");
        XMLStreamReader reader = factory.createXMLStreamReader(source);

        while(reader.hasNext()){
            int event = reader.next();

            switch(event){
                case XMLStreamConstants.START_ELEMENT:
                    if ("gun".equals(reader.getLocalName())){
                        currGun = new Gun();
                    }
                    if("guns".equals(reader.getLocalName())){
                        gunList = new ArrayList<>();
                    }
                    if("TTC".equals(reader.getLocalName())){
                        currGun.ttc = new TTC();
                    }

                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    switch(reader.getLocalName()){
                        case "gun":
                            gunList.add(currGun);
                            break;
                        case "origin":
                            currGun.setOrigin(tagContent);
                            break;
                        case "handy":
                            currGun.setHandy(tagContent);
                            break;
                        case "model":
                            currGun.setModel(tagContent);
                            break;
                        case "range":
                            currGun.ttc.setRange(Integer.parseInt(tagContent), currGun.getType());
                            break;
                        case "optics":
                            currGun.ttc.setOptics(tagContent, currGun.getType());
                            break;
                        case "collar":
                            currGun.ttc.setCollar(tagContent);
                            break;
                        case "sightingRange":
                            currGun.ttc.setSightingRange(Integer.parseInt(tagContent));
                            break;

                    }
                    break;

                case XMLStreamConstants.START_DOCUMENT:
                    gunList = new ArrayList<>();
                    break;
            }

        }

        //Print the employee list populated from XML
        for ( Gun gun : gunList){
            System.out.println(gun);
        }

    }
}