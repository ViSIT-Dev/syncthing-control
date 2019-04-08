package syncthing.control;



import syncthing.control.operation.Shutdown;
import static syncthing.control.Constants.*;
import static syncthing.control.arguments.Arguments.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import syncthing.control.arguments.Arguments;
import syncthing.control.operation.*;
import syncthing.stuff.Util;

/**
 *
 * @author RaichKrispin
 * 
 * See syncthing.resources/Help.txt
 * 
 */
public class SyncthingControl {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            
        try {
            dirtyMain(args);
        } catch (Exception e) {
            //avoiding stacktrace
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
    }
    
    private static void dirtyMain(String[] args) throws Exception{
        File configFile = searchConfigFile();

        if (configFile == null) {
            throw new FileNotFoundException("Config File not found");
        }

        Document doc = initXMLFile(configFile);
        
        //read gui params
        Node guiNode = doc.getElementsByTagName("gui").item(0);
        NodeList childNodes = guiNode.getChildNodes();
        String apiKey = null;
        String serverAddress = null;

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node n = childNodes.item(i);

            if ("apikey".equalsIgnoreCase(n.getNodeName())) {
                apiKey = n.getTextContent();
            }
            if ("address".equalsIgnoreCase(n.getNodeName())) {
                serverAddress = n.getTextContent();
            }
        }

        if (apiKey == null) {
            throw new Exception("API Key not found!");
        }

        if (serverAddress == null) {
            throw new Exception("Server Address not found!");
        }

        RestControl rc = RestControl.createInstance(serverAddress, apiKey);

        try {
            rc.ping();
        } catch (Exception e) {
            throw new IOException("Server not accessible / online");
        }
        
        if (args.length == 0) {
            System.out.println(rc.getDeviceID());
            return;
        }

        
        Map<String, String> arguments = Arguments.parseArgs(args);
        
        if(arguments.containsKey(P_HELP)){
            System.out.println(
                    Util.inputStreamToString(
                            SyncthingControl.class.getClassLoader().getResourceAsStream("syncthing/resources/Help.txt")
                    )
            );
            return;
        }
        
        if(arguments.containsKey(P_QUIET)) Util.setOutputQuiet();
        
        AbstractOperation mode = null;
        //String.valueOf converts null to "null" --> default
        switch(String.valueOf(arguments.get("mode"))){
            case P_MODE_ADD_DEVICE: mode = new AddDevice(); break;
            case P_MODE_REMOVE_DEVICE: mode = new RemoveDevice(); break;
            case P_MODE_ADD_FOLDER: mode = new AddFolder(); break;
            case P_MODE_REMOVE_FOLDER: mode = new RemoveFolder(); break;
            case P_MODE_ADD_DEV_TO_FOLDER: mode = new AddDeviceToFolder(); break;
            case P_MODE_REMOVE_DEV_FROM_FOLDER: mode = new RemoveDeviceFromFolder(); break;
            case P_SHUTDOWN: mode = new Shutdown(); break;
            default: 
                Util.println("No mode selected, start with --help for manual"); 
        }
        
        if(mode != null){
            Util.println("Operation: " + mode);
            mode.execute(arguments, doc);
            writeXML(doc,configFile);
        }
        
        if(arguments.containsKey(P_RESTART)){
            rc.restart();   
        }else{
            Util.println("Restart Syncthing to load new configuration (--restart)");
        }

    }
    
    private static void writeXML(Document doc, File configFile) throws Exception{
        Util.print("writing xml... ");
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(doc), new StreamResult(configFile));
        Util.println("done");
    }

    private static Document initXMLFile(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        //parsing XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static File searchConfigFile() {
        //search config file
        String userHome = System.getProperty("user.home");

        for (String currentLocation : CONFIG_FILE_LOCATIONS) {

            File f = new File(userHome + File.separatorChar + currentLocation + File.separatorChar + CONFIG_FILE_NAME);

            if (f.exists() && f.isFile() && f.canRead()) {
                return f;
            }
        }
        
          File f = new File("/var/syncthing/config/" + CONFIG_FILE_NAME);

            if (f.exists() && f.isFile() && f.canRead()) {
                return f;
            }
        
        return null;
    }

  


}
