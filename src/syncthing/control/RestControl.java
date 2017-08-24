package syncthing.control;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import syncthing.stuff.Util;

/**
 *
 * @author Kris
 */
public class RestControl {

    private static RestControl instance = null;
    
    public static RestControl getInstance(){
        return instance;
    }
    public static synchronized RestControl createInstance(String address, String apiKey){
        if(instance == null)
            instance = new RestControl(address, apiKey);
        return instance;
    }
    
    private final String address;
    private final String apiKey;

    private RestControl(String address, String apiKey) {
        this.address = address;
        this.apiKey = apiKey;
    }

    public String requestServer(String path, String method) throws MalformedURLException, IOException {
        URL url = new URL("http://" + address + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("X-API-Key", apiKey);
        connection.setUseCaches(false);
        int responsecode = connection.getResponseCode();
        return Util.inputStreamToString(connection.getInputStream());
    }

    public String ping() throws MalformedURLException, IOException {
        return requestServer("/rest/system/ping", "GET");
    }
    
    public String getStatus() throws MalformedURLException, IOException {
        return requestServer("/rest/system/status", "GET");
    }
    
    public String getDeviceID() throws MalformedURLException, IOException{
        Matcher m = Constants.MY_ID_PATTERN.matcher(getStatus());
        return m.find() ? m.group(1) : null;
    }
    
    public String checkID(String idToCheck) throws IOException{
        String res = requestServer("/rest/svc/deviceid?id=" + idToCheck, "GET");
        Matcher m = Constants.ID_PATTERN.matcher(res);
        return m.find() ? m.group(1) : null;
    }

    public void restart() throws Exception {
        Util.print("restarting");
        requestServer("/rest/system/restart", "POST");

        while (true) {
            Thread.sleep(100);
            try {
                ping();
                Util.println(" done");
                break;
            } catch (Exception e) {
                Util.print(".");
            }
        }

    }

    public String getLog() throws MalformedURLException, IOException {
        return requestServer("/rest/system/log", "GET");
    }


}
