package syncthing.control.arguments;

/**
 *
 * @author Kris
 */
public class UnknownArgumentException extends IllegalArgumentException{

    public UnknownArgumentException(Iterable unkownArguments) {
        super("Unknown Argument(s): '" + String.join("', '", unkownArguments) + "'");
    }
        
}
