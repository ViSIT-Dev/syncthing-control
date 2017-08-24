package syncthing.control.arguments;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Kris
 */
public class Arguments {

    @CLIArgument(hasInput = true)
    public static final String P_REMOTE_DEV_ID = "--remote-device-id=";

    @CLIArgument(hasInput = true)
    public static final String P_REMOTE_DEV_NAME = "--remote-device-name=";

    @CLIArgument(hasInput = true)
    public static final String P_FOLDER_NAME = "--folder-name=";

    @CLIArgument(hasInput = true)
    public static final String P_FOLDER_ID = "--folder-id=";

    @CLIArgument(hasInput = true)
    public static final String P_FOLDER_PATH = "--folder-path=";

    @CLIArgument(keyOverride = "mode")
    public static final String P_MODE_ADD_DEVICE = "--add-device";

    @CLIArgument(keyOverride = "mode")
    public static final String P_MODE_REMOVE_DEVICE = "--remove-device";

    @CLIArgument(keyOverride = "mode")
    public static final String P_MODE_ADD_FOLDER = "--add-folder";

    @CLIArgument(keyOverride = "mode")
    public static final String P_MODE_REMOVE_FOLDER = "--remove-folder";

    @CLIArgument(keyOverride = "mode")
    public static final String P_MODE_ADD_DEV_TO_FOLDER = "--add-dev-to-folder";

    @CLIArgument(keyOverride = "mode")
    public static final String P_MODE_REMOVE_DEV_FROM_FOLDER = "--remove-dev-from-folder";

    @CLIArgument(keyOverride = "mode")
    public static final String P_SHUTDOWN = "--shutdown";

    @CLIArgument()
    public static final String P_HELP = "--help";
    
    @CLIArgument()
    public static final String P_QUIET = "--quiet";
    
    @CLIArgument()
    public static final String P_RESTART = "--restart";

    @Retention(RetentionPolicy.RUNTIME)
    public @interface CLIArgument {

        boolean hasInput() default false;

        String keyOverride() default "";

    }

    public static Map<String, String> parseArgs(String[] args) {

        Map<String, String> paresdArguments = new HashMap();

        Set<String> argList = new HashSet(Arrays.asList(args));

        for (Field field : Arguments.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(CLIArgument.class)) {
                try {
                    Object get = field.get(null);
                    String currentFieldValue = get.toString();
                    String keyOverride = field.getAnnotation(CLIArgument.class).keyOverride();
                    String key = keyOverride.length() > 0 ? keyOverride : currentFieldValue;

                    
                        if (field.getAnnotation(CLIArgument.class).hasInput()) {
                            for (String currentArgument : argList) {
                                if(currentArgument.startsWith(currentFieldValue)){
                                    paresdArguments.put(key, currentArgument.substring(currentFieldValue.length()));
                                    argList.remove(currentArgument);
                                }
                            }
                        } else {
                            if(argList.contains(currentFieldValue)){
                                paresdArguments.put(key, currentFieldValue);
                                argList.remove(currentFieldValue);
                            }
                        }
                } catch (Exception ex) {
                    //nothing.. next iteration
                }
            }
        }
        
        if(!argList.isEmpty())
            throw new UnknownArgumentException(argList);

        return paresdArguments;
    }

}
