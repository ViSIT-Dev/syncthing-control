package syncthing.control.operation;

import java.util.Map;
import org.w3c.dom.Document;
import syncthing.control.arguments.MissingArgumentException;

/**
 *
 * @author Kris
 */
public abstract class AbstractOperation{
    
    public abstract void execute(Map<String, String> arguments, Document document) throws Exception;

    @Override
    public String toString() {
        return this.getClass().getName(); 
    }
    
    protected void checkArguments(Map<String, String> arguments, String... keys) throws MissingArgumentException
    {
        for (String currentKey : keys) {
            if(! arguments.containsKey(currentKey)) throw new MissingArgumentException(currentKey);
            
        }
    }
}
