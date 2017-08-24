package syncthing.control.operation;

import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import syncthing.control.RestControl;
import static syncthing.control.arguments.Arguments.*;

/**
 *
 * @author Kris
 */
public abstract class AbstractFolderOperation extends AbstractOperation {

   
    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {

        checkArguments(arguments, P_REMOTE_DEV_ID, P_FOLDER_ID);
        
        String deviceId = arguments.get(P_REMOTE_DEV_ID);
        String folderID =  arguments.get(P_FOLDER_ID);

        
         //check id
        if ((deviceId = RestControl.getInstance().checkID(deviceId)) == null) {
            throw new IllegalArgumentException("Malformed ID");
        }


        //add to default folder
        NodeList folders = document.getElementsByTagName("folder");
        for (int i = 0; i < folders.getLength(); i++) {
            Node item = folders.item(i);
            if (folderID.equals(item.getAttributes().getNamedItem("id").getTextContent())) {
                doWorkOnFolder(document, item, deviceId);
                return;
            }
        }
        throw new Exception("Folder ID not found");

    }
    
    protected abstract void doWorkOnFolder(Document document, Node targetFolder, String deviceId);
    
    
}
