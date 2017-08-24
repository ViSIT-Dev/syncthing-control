package syncthing.control.operation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Kris
 */
public class AddDeviceToFolder extends AbstractFolderOperation {

    @Override
    protected void doWorkOnFolder(Document document, Node targetFolder, String deviceId) {

        //neues Device muss an erster stelle sein? Möglicher bug. Beobachten!
        
        Element device = document.createElement("device");
        device.setAttribute("id", deviceId);
        device.setAttribute("introducedBy", "");
        targetFolder.appendChild(device);

    }

    @Override
    public String toString() {
        return "Adding device to folder";
    }
    

}
