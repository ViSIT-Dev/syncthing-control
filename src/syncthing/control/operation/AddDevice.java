package syncthing.control.operation;

import static syncthing.control.arguments.Arguments.*;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import syncthing.control.RestControl;

/**
 *
 * @author Kris
 */
public class AddDevice extends AbstractOperation {

    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {

        checkArguments(arguments, P_REMOTE_DEV_ID, P_REMOTE_DEV_NAME);
        
        String remoteDeviceId = arguments.get(P_REMOTE_DEV_ID);
        String remoteDeviceName = arguments.get(P_REMOTE_DEV_NAME);

        //check id
        if ((remoteDeviceId = RestControl.getInstance().checkID(remoteDeviceId)) == null) {
            throw new IllegalArgumentException("Malformed ID");
        }
        Element newElem = document.createElement("device");
        newElem.setAttribute("id", remoteDeviceId);
        newElem.setAttribute("introducedBy", "");
        //add to default folder
        NodeList folders = document.getElementsByTagName("folder");
        for (int i = 0; i < folders.getLength(); i++) {
            Node item = folders.item(i);
            if ("default".equals(item.getAttributes().getNamedItem("id").getTextContent())) {
                item.appendChild(newElem.cloneNode(true));
                break;
            }
        }
        newElem.setAttribute("name", remoteDeviceName);
        newElem.setAttribute("introducer", "true");
        newElem.setAttribute("skipIntroductionRemovals", "false");
        newElem.setAttribute("compression", "metadata");
        Element address = document.createElement("address");
        address.setTextContent("dynamic");
        Element paused = document.createElement("paused");
        paused.setTextContent("false");
        Element autoAcceptFolders = document.createElement("autoAcceptFolders");
        autoAcceptFolders.setTextContent("true");
        newElem.appendChild(address);
        newElem.appendChild(paused);
        newElem.appendChild(autoAcceptFolders);
        Node gui = document.getElementsByTagName("gui").item(0);
        gui.getParentNode().insertBefore(newElem, gui);

    }

    @Override
    public String toString() {
        return "Adding Device";
    }
    
    

}
