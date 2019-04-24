package syncthing.control.operation;

import java.util.Map;
import org.w3c.dom.Document;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import syncthing.control.RestControl;

/**
 *
 * @author Kris
 */
public class GetPendingDevices extends AbstractOperation {

    private String output;
    
    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {
        RestControl rc = RestControl.getInstance();
        this.output = "{" + rc.getPendingDevices() + "}";
        System.out.println( this.output );
    }

 
    @Override
    public String toString() {
        return this.output;
    }

    
    
}
