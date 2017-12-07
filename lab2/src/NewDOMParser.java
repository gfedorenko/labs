import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;

public class NewDOMParser {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        XSDValidation val = new XSDValidation();
        val.validate();

        Document document = builder.parse("gun.xml");

        ArrayList<Gun> gunList = new ArrayList<>();

        NodeList nodeList = document.getDocumentElement().getElementsByTagName("gun");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Gun gun = new Gun();
                TTC t = new TTC();
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);

                    if (cNode instanceof Element) {
                        String content = cNode.getLastChild().
                                getTextContent().trim();
                        switch (cNode.getNodeName()) {
                            case "origin":
                                gun.setOrigin(content);
                                break;
                            case "typeGun":{
                                gun.setType("gun");
                                NodeList tNodes = cNode.getChildNodes();
                                for (int z = 0; z < tNodes.getLength(); z++) {
                                    Node tNode = tNodes.item(z);
                                    // System.out.println(tNode);
                                    if (tNode instanceof Element) {
                                        String tcontent = tNode.getLastChild().
                                                getTextContent().trim();
                                        // System.out.println(tcontent);
                                        switch (tNode.getNodeName()) {
                                            case "type":
                                                gun.setType(tcontent);
                                                break;
                                            case "handy":
                                                gun.setHandy(tcontent);
                                                break;
                                            case "model":
                                                gun.setModel(tcontent);
                                                break;
                                            case "TTC": {


                                                NodeList nNodes = tNode.getChildNodes();
                                                for (int p = 0; p < nNodes.getLength(); p++) {
                                                    Node nNode = nNodes.item(p);
                                                    if (nNode instanceof Element) {
                                                        String ncontent = nNode.getLastChild().
                                                                getTextContent().trim();
                                                        switch (nNode.getNodeName()) {
                                                            case "range":
                                                                t.setRange(Integer.parseInt(ncontent), gun.getType());
                                                                break;
                                                            case "optics":
                                                                t.setOptics(ncontent, gun.getType());
                                                                break;
                                                            case "collar":
                                                                t.setCollar(ncontent);
                                                                break;
                                                            case "sightingRange":
                                                                t.setSightingRange(Integer.parseInt(ncontent));
                                                                break;
                                                        }

                                                    }

                                                }
                                                gun.setTtc(t);

                                            }

                                        }
                                    }
                                 }
                            }
                            case "typeRiffle":{
                                gun.setType("riffle");
                                NodeList tNodes = cNode.getChildNodes();
                                for (int z = 0; z < tNodes.getLength(); z++) {
                                    Node tNode = tNodes.item(z);
                                    // System.out.println(tNode);
                                    if (tNode instanceof Element) {
                                        String tcontent = tNode.getLastChild().
                                                getTextContent().trim();
                                        switch (tNode.getNodeName()) {
                                            case "type":
                                                gun.setType(tcontent);
                                                break;
                                            case "handy":
                                                gun.setHandy(tcontent);
                                                break;
                                            case "model":
                                                gun.setModel(tcontent);
                                                break;
                                            case "TTC": {
                                                NodeList nNodes = tNode.getChildNodes();
                                                for (int p = 0; p < nNodes.getLength(); p++) {
                                                    Node nNode = nNodes.item(p);
                                                    if (nNode instanceof Element) {
                                                        String ncontent = nNode.getLastChild().
                                                                getTextContent().trim();
                                                        switch (nNode.getNodeName()) {
                                                            case "range":
                                                                t.setRange(Integer.parseInt(ncontent), gun.getType());
                                                            case "optics":
                                                                t.setOptics(ncontent, gun.getType());
                                                                break;
                                                            case "collar":
                                                                t.setCollar(ncontent);
                                                                break;
                                                            case "sightingRange":
                                                                t.setSightingRange(Integer.parseInt(ncontent));
                                                                break;
                                                        }

                                                    }

                                                }
                                                gun.setTtc(t);

                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                gunList.add(gun);
            }

        }
        gunList.sort(new Sorting());
        for (Gun gun : gunList) {
            System.out.println(gun);
        }

    }
}
