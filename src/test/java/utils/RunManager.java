package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RunManager {

    private static String runPath;

    static {

        //Create timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        //Folder path
        runPath = "test-output/Run_" + timeStamp;

        //Create folders
        new File(runPath).mkdirs();
        new File(runPath + "/screenshots").mkdirs();
        System.out.println("Run folder created: " + runPath);
    }

    public static String getRunPath() {
        return runPath;
    }
}