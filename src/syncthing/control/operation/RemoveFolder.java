package syncthing.control.operation;

import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import static syncthing.control.arguments.Arguments.*;

/**
 *
 * @author Kris
 */
public class RemoveFolder extends AbstractOperation {

    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {

        checkArguments(arguments, P_FOLDER_ID);

        String folderID = arguments.get(P_FOLDER_ID);

        NodeList divices = document.getElementsByTagName("folder");
        for (int i = 0; i < divices.getLength(); i++) {
            Node item = divices.item(i);
            if (item.getAttributes().getNamedItem("id").getTextContent().equals(folderID)) {
                item.getParentNode().removeChild(item);
                return;
            }
        }
        throw new RuntimeException("Folder ID not found");

    }

    @Override
    public String toString() {
        return "Removing folder";
    }

  
    
}
