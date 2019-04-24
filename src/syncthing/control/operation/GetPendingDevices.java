package syncthing.control.operation;

import java.util.Map;
import org.w3c.dom.Document;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Kris
 */
public class GetPendingDevices extends AbstractOperation {

    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {
          throw new NotImplementedException();
    }

 
    @Override
    public String toString() {
        return "Return pending devices in a json";
    }

    
    
}
