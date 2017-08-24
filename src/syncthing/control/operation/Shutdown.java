package syncthing.control.operation;

import java.util.Map;
import org.w3c.dom.Document;
import syncthing.control.RestControl;

/**
 *
 * @author Kris
 */
public class Shutdown extends AbstractOperation {

    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {
        RestControl.getInstance().requestServer("/rest/system/shutdown", "POST");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "Shutting down";
    }
    
}
