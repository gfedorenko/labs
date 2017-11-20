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
        Document document =
                builder.parse(
                        ClassLoader.getSystemResourceAsStream("xml/employee.xml"));


        ArrayList<Gun> gunList = new ArrayList<>();

        //Iterating through the nodes and extracting the data.
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            //We have encountered an <employee> tag.
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Gun gun = new Gun();
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);

                    //Identifying the child tag of employee encountered.
                    if (cNode instanceof Element) {
                        String content = cNode.getLastChild().
                                getTextContent().trim();
                        switch (cNode.getNodeName()) {
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
