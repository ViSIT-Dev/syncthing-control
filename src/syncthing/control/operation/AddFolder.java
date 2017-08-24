package syncthing.control.operation;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static syncthing.control.arguments.Arguments.*;
import syncthing.control.RestControl;
import syncthing.stuff.Util;

/**
 *
 * @author Kris
 */
public class AddFolder extends AbstractOperation {

    /*
        <folder id="r23mk-hxuqn" label="test12" path="C:\Users\raichkrispin.KUFSTEIN\test12" type="readwrite" rescanIntervalS="60" ignorePerms="false" autoNormalize="true">
        <device id="2VHWMIE-NQ22FXN-2BLLXSV-6DFLEPG-HMYZ2IZ-EHCXLSB-UQ5RF3K-6X2LOAV" introducedBy=""></device>
        <minDiskFree unit="%">1</minDiskFree>
        <versioning></versioning>
        <copiers>0</copiers>
        <pullers>0</pullers>
        <hashers>0</hashers>
        <order>random</order>
        <ignoreDelete>false</ignoreDelete>
        <scanProgressIntervalS>0</scanProgressIntervalS>
        <pullerSleepS>0</pullerSleepS>
        <pullerPauseS>0</pullerPauseS>
        <maxConflicts>10</maxConflicts>
        <disableSparseFiles>false</disableSparseFiles>
        <disableTempIndexes>false</disableTempIndexes>
        <fsync>true</fsync>
        <paused>false</paused>
        <weakHashThresholdPct>25</weakHashThresholdPct>
        </folder>
     */
    @Override
    public void execute(Map<String, String> arguments, Document document) throws Exception {

        checkArguments(arguments, P_FOLDER_NAME, P_FOLDER_PATH);

        String folderName = arguments.get(P_FOLDER_NAME);
        String folderPath = arguments.get(P_FOLDER_PATH);
        String folderID = arguments.containsKey(P_FOLDER_ID) ? arguments.get(P_FOLDER_ID) : getRandomFolderID();

        File folderFile = new File(folderPath);
        
        if(folderFile.exists() && folderFile.isFile()){
            throw new IOException("Expected folder.. found File");
        }
        
        if(!folderFile.isAbsolute()){
            throw new IOException("Path must be absolut");
        }
        
        if(! folderFile.exists()){
            Util.println("Folder doesn't exist. creating.");
            folderFile.mkdirs();
        }
        
        
        //Folder Tag
        Element newFolder = document.createElement("folder");
        newFolder.setAttribute("autoNormalize", "true");
        newFolder.setAttribute("id", folderID);
        newFolder.setAttribute("ignorePerms", "false");
        newFolder.setAttribute("label", folderName);
        newFolder.setAttribute("path", folderPath);
        newFolder.setAttribute("rescanIntervalS", "60");
        newFolder.setAttribute("type", "readwrite");

        //device child
        Element device = document.createElement("device");
        device.setAttribute("id", RestControl.getInstance().getDeviceID());
        device.setAttribute("introducedBy", "");
        newFolder.appendChild(device);

        //minDiskFree
        Element minDiskFree = document.createElement("minDiskFree");
        minDiskFree.setAttribute("unit", "%");
        minDiskFree.setTextContent("1");
        newFolder.appendChild(minDiskFree);

        //versioning
        Element versioning = document.createElement("versioning");
        newFolder.appendChild(versioning);

        //copiers
        Element copiers = document.createElement("copiers");
        copiers.setTextContent("0");
        newFolder.appendChild(copiers);

        //pullers 8===o
        Element pullers = document.createElement("pullers");
        pullers.setTextContent("0");
        newFolder.appendChild(pullers);

        //hashers
        Element hashers = document.createElement("hashers");
        hashers.setTextContent("0");
        newFolder.appendChild(hashers);

        //order
        Element order = document.createElement("order");
        order.setTextContent("random");
        newFolder.appendChild(order);

        //ignoreDelete
        Element ignoreDelete = document.createElement("ignoreDelete");
        ignoreDelete.setTextContent("false");
        newFolder.appendChild(ignoreDelete);

        //scanProgressIntervalS
        Element scanProgressIntervalS = document.createElement("scanProgressIntervalS");
        scanProgressIntervalS.setTextContent("0");
        newFolder.appendChild(scanProgressIntervalS);

        //pullerSleepS
        Element pullerSleepS = document.createElement("pullerSleepS");
        pullerSleepS.setTextContent("0");
        newFolder.appendChild(pullerSleepS);

        //pullerPauseS
        Element pullerPauseS = document.createElement("pullerPauseS");
        pullerPauseS.setTextContent("0");
        newFolder.appendChild(pullerPauseS);

        //maxConflicts
        Element maxConflicts = document.createElement("maxConflicts");
        maxConflicts.setTextContent("10");
        newFolder.appendChild(maxConflicts);

        //disableSparseFiles
        Element disableSparseFiles = document.createElement("disableSparseFiles");
        disableSparseFiles.setTextContent("false");
        newFolder.appendChild(disableSparseFiles);

        //disableTempIndexes
        Element disableTempIndexes = document.createElement("disableTempIndexes");
        disableTempIndexes.setTextContent("false");
        newFolder.appendChild(disableTempIndexes);

        //fsync
        Element fsync = document.createElement("fsync");
        fsync.setTextContent("true");
        newFolder.appendChild(fsync);

        //paused
        Element paused = document.createElement("paused");
        paused.setTextContent("false");
        newFolder.appendChild(paused);

        //weakHashThresholdPct
        Element weakHashThresholdPct = document.createElement("weakHashThresholdPct");
        weakHashThresholdPct.setTextContent("25");
        newFolder.appendChild(weakHashThresholdPct);

        Node reffolder = document.getElementsByTagName("folder").item(0);
        reffolder.getParentNode().insertBefore(newFolder, reffolder);

    }

    private final static String CHARS = "abcdefghijklmnopqrstuvwxyz1234567890";

    /**
     * Format: r23mk-hxuqn
     *
     * @return
     */
    private String getRandomFolderID() {
        Random r = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(
                    CHARS.charAt(
                            r.nextInt(CHARS.length())
                    )
            );
            if (i == 4) {
                sb.append('-');
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Adding folder";
    }

    
    
}
