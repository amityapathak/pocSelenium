package com.selenium.pocclasses.DataProvider;

import com.selenium.pocclasses.UtilityFunctions.Constants;
import com.selenium.pocclasses.UtilityFunctions.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/***********************************************************************************
 Class Name : DpConfiguration
 Description : Read data from configuration properties
 ***********************************************************************************/
public class DpConfiguration {
    Properties properties;
    String sCurrentDirectory = "";

    /***************************************************************************
     Method Name : DpConfiguration
     Parameters :
     Description By : Constructor to create File ,FileInputStream and Prop object and to load the properties file
     *****************************************************************************/
    public  DpConfiguration ()
    {
        //Creation of file object
        File fileSRC;
        FileInputStream fileInputStream;
        if(System.getProperty("os.name").toLowerCase().contains("windows"))
        {
            fileSRC=new File(".\\src\\test\\resources\\TestData\\config.properties");
        }
        else
        {
            fileSRC=new File("./src/test/resources/linux_config.properties");
        }
        try
        {
            sCurrentDirectory =System.getProperty("user.dir");
            fileInputStream=new FileInputStream(fileSRC);
            properties=new Properties();
            //load the properties file
            properties.load(fileInputStream);
        }
        catch (Exception e)
        {

        }

    }
    /****************************************************************************************
    Method Name : getConfigData
     Parameters : sDataName
     Description : this is used to return value of a given data name from config file
     ****************************************************************************/
    public  String getConfigData(String sDataName)
    {
        String sRtnPropertyVal =null;
        try
        {
            sRtnPropertyVal=properties.getProperty(sDataName);
        }
        catch(Exception e)
        {
         Utils.addLog("FAIL",e.getMessage());
        }
        return sRtnPropertyVal;
    }
    /****************************************************************************************
     Method Name : getCurrentDirectory
     Parameters :
     Description : this method is used to return current directory
     ****************************************************************************/
    public String getCurrentDirectory()
    {
        return sCurrentDirectory;
    }
    /****************************************************************************************
     Method Name : getApplicationURL
     Parameters :
     Description : this method is used to return Application URL
     ****************************************************************************/
    public String getApplicationURL() throws IOException {
        File fileSRC;
        String sURL = null;
        String sData= "";
        if (System.getProperty("os.name").toLowerCase().contains("windows"))
        {

        }
        else
        {

        }

        sData = new String(Files.readAllBytes(Paths.get(sCurrentDirectory + "\\src\\test\\resources\\TestData\\environment_Data.txt")));
       Utils.addLog("INFO","Environment :"+sData);
        if (sData.contains("test"))
        {
            sURL = Constants.ApplicationURL_TestCloud_Guest;
        }
       else
       {
           Utils.addLog("INFO","Need to mention expected URL:"+sURL);
       }
       return  sURL;
    }





}
