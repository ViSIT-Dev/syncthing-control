package syncthing.control.operation;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Kris
 */
public class RemoveDeviceFromFolder extends AbstractFolderOperation {

    @Override
    protected void doWorkOnFolder(Document document, Node targetFolder, String deviceId) {

        NodeList childNodes = targetFolder.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            if (
                    "device".equals(item.getNodeName()) &&
                    deviceId.equals(item.getAttributes().getNamedItem("id").getTextContent())
                ) {
                targetFolder.removeChild(item);
                return;
            }
        }
        throw new RuntimeException("Device ID not found");

    }

    @Override
    public String toString() {
        return "Removing device from folder";
    }

    
    
}
