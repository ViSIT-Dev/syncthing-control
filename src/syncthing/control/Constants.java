package syncthing.control;

import java.util.regex.Pattern;

/**
 *
 * @author Kris
 */
public class Constants {
    
    public static final String CONFIG_FILE_NAME = "config.xml";
    
    public static final String[] CONFIG_FILE_LOCATIONS = {
        "/.config/syncthing",
        "/Library/Application Support/Syncthing",
        "/syncthing",
        "/var/www/syncthing",
        "\\AppData\\Roaming\\Syncthing",
        "\\AppData\\Local\\Syncthing"
    };

    public static final Pattern MY_ID_PATTERN = Pattern.compile("\"myID\"\\s*:\\s*\"([A-Z0-9-]*)\"");
    
    public static final Pattern ID_PATTERN = Pattern.compile("\"id\"\\s*:\\s*\"([A-Z0-9-]*)\"");
    
    
}
