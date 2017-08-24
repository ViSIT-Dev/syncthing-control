package syncthing.stuff;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Kris
 */
public class Util {
    
    public static String inputStreamToString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        result.close();
        inputStream.close();
        return result.toString("UTF-8");
    }
    
    
    private static boolean isQuiet = false;
    
    public static void setOutputQuiet(){
        isQuiet = true;
    }
    public static void print(Object s){
        if(!isQuiet)
            System.out.print(s);
    }
    public static void println(Object s){
        if(!isQuiet)
            System.out.println(s);
    }
    
}
