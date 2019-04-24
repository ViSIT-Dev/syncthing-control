package syncthing.control;

import java.io.File;
import java.util.regex.Pattern;

/**
 *
 * @author Kris
 */
public class Constants {
    
    public static final String CONFIG_FILE_NAME = "config.xml";
    
    public static final String[] getConfigFileLocations() {
        final String userHome = System.getProperty("user.home");
        
        String[] paths = new String[]{
            userHome + "/.config/syncthing/" + CONFIG_FILE_NAME,
            userHome + "/Library/Application Support/Syncthing/" + CONFIG_FILE_NAME,
            userHome + "/syncthing/" + CONFIG_FILE_NAME,
            userHome + "\\AppData\\Roaming\\Syncthing\\" + CONFIG_FILE_NAME,
            userHome + "\\AppData\\Local\\Syncthing\\" + CONFIG_FILE_NAME,
            "/var/www/syncthing/" + CONFIG_FILE_NAME
        };
        return paths;
    }
        
    public static final Pattern MY_ID_PATTERN = Pattern.compile("\"myID\"\\s*:\\s*\"([A-Z0-9-]*)\"");
    
    public static final Pattern ID_PATTERN = Pattern.compile("\"id\"\\s*:\\s*\"([A-Z0-9-]*)\"");
    
    public static final Pattern PENDING_DEVICES_PATTERN = Pattern.compile("\"pendingDevices\"[^\\]]*\\]");
    
}
