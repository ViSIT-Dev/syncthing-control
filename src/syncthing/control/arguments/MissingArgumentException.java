package syncthing.control.arguments;

/**
 *
 * @author Kris
 */
public class MissingArgumentException extends IllegalArgumentException{

    public MissingArgumentException(String missingArgument) {
        super("Argument '" + missingArgument + "' is missing.");
        
    }
    
}
