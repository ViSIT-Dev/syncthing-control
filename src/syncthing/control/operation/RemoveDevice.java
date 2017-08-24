package syncthing.control.operation;

import static syncthing.control.arguments.Arguments.*;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import syncthing.control.RestControl;

/**
 *
 * @author Kris
 */
public class RemoveDevice extends AbstractOperation {

    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {

        checkArguments(arguments, P_REMOTE_DEV_ID);

        String remoteDeviceId = arguments.get(P_REMOTE_DEV_ID);

        if ((remoteDeviceId = RestControl.getInstance().checkID(remoteDeviceId)) == null) {
            throw new IllegalArgumentException("Malformed ID");
        }
        NodeList divices = document.getElementsByTagName("device");
        for (int i = 0; i < divices.getLength(); i++) {
            Node item = divices.item(i);
            if (item.getAttributes().getNamedItem("id").getTextContent().equals(remoteDeviceId)) {
                item.getParentNode().removeChild(item);
                //kein return, iteration über alle
            }
        }

    }

    @Override
    public String toString() {
        return "Removing device";
    }
    
    

}
