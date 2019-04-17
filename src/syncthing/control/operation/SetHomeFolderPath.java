package syncthing.control.operation;

import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import static syncthing.control.arguments.Arguments.P_FOLDER_NAME;
import static syncthing.control.arguments.Arguments.P_FOLDER_PATH;

/**
 *
 * @author Kris
 */
public class SetHomeFolderPath extends AbstractOperation {

    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {
          throw new NotImplementedException();
//        checkArguments(arguments, P_FOLDER_PATH);
//        String folderPath = arguments.get(P_FOLDER_PATH);
    }

 
    @Override
    public String toString() {
        return "Set Default Folder";
    }

    
    
}
