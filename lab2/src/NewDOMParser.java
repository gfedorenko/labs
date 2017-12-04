import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;

public class NewDOMParser {
    public static void main(String[] args) throws Exception {
        //Get the DOM Builder Factory
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        //Get the DOM Builder
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Load and Parse the XML document
        //document contains the complete XML as a Tree.

        Document document = builder.parse("gun.xml");

        ArrayList<Gun> gunList = new ArrayList<>();

        //Iterating through the nodes and extracting the data.
        //NodeList nodeList = document.getDocumentElement().getChildNodes();
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("gun");
        for (int i = 0; i < nodeList.getLength(); i++) {

            //We have encountered an <employee> tag.
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Gun gun = new Gun();
                TTC t = new TTC();
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);

                    //Identifying the child tag of employee encountered.
                    if (cNode instanceof Element) {
                        String content = cNode.getLastChild().
                                getTextContent().trim();
                        switch (cNode.getNodeName()) {
                            case "type":
                                gun.setType(content);
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
                            case "TTC": {

                                //Node tnode = nodeList.item(j);
                               // System.out.println(cNode);
                                NodeList tNodes = cNode.getChildNodes();
                                for (int z = 0; z < tNodes.getLength(); z++) {
                                    Node tNode = tNodes.item(z);
                                   // System.out.println(tNode);
                                    if (tNode instanceof Element) {
                                        String tcontent = tNode.getLastChild().
                                                getTextContent().trim();
                                        // System.out.println(tcontent);
                                        switch (tNode.getNodeName()) {
                                            case "range":
                                                t.setRange(Integer.parseInt(tcontent), gun.getType());
                                                System.out.println(t.getRange());
                                                break;
                                            case "optics":
                                                t.setOptics(tcontent, gun.getType());
                                                break;
                                            case "collar":
                                                t.setCollar(tcontent);
                                                break;
                                            case "sightingRange":
                                                t.setSightingRange(Integer.parseInt(tcontent));
                                                break;
                                        }

                                    }

                                }
                                //System.out.println(t);
                                gun.setTtc(t);

                            }
                        }
                    }
                }

                gunList.add(gun);
            }

        }

        //Printing the Employee list populated.
        for (Gun emp : gunList) {
            System.out.println(emp);
        }

    }
}
