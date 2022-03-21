package com.selenium.pocclasses.UtilityFunctions;

import com.relevantcodes.extentreports.LogStatus;
import com.selenium.pocclasses.Base.Base;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/******************************************************************
 Class Name :Utility Function
 Description : This class is used to write common reusable functions
 ********************************************************************/
public class Utils extends Base {
    public  static  final DateTimeFormatter dtfDate=DateTimeFormatter.ofPattern("dd");
    public  static  final DateTimeFormatter dtfMonth=DateTimeFormatter.ofPattern("MMM");
    public static LocalDateTime dltnow=null;

    /******************************************************
    Method Name :resultFilePath
     Parameters :
     Description By: this method is used to return the Result Path
     ***********************************************************/
    public  static  String resultFilePath()
    {
        String sPath=null;
        File filePath=null;
        String strResultPath=null;
        dltnow=LocalDateTime.now();
        try
        {
            if(System.getProperty("os.name").toLowerCase().contains("windows"))
            {
                filePath=new File("." + objDpConfig.getConfigData("ResultDownloadPath"));
                try
                {
                    if (filePath.exists())
                    {
                        strResultPath="."+objDpConfig.getConfigData("ResultDownloadPath");
                        FileUtils.cleanDirectory(new File(strResultPath));
                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
                sPath="." + objDpConfig.getConfigData("ResultDownloadPath") + "\\Result.html";
            }else{
                sPath="." + objDpConfig.getConfigData("ResultDownloadPath") + "/Result.html";
            }
        }catch (Exception e){
            Utils.addLog("FAIL","Exception occurred" +e.getMessage());
        }
        return sPath;
    }

    /***************************************************************
    Method Name : screenShoots
     Parameters: sScreenShotName
     Description By : this method is used to take screenshots to return the result path
     ***************************************************************/
    public  static  String screenShoots (String sScreenShotName)
    {
        File fileSCR=null;
        String sSCRDestination=null;
        try
        {
            fileSCR =((TakesScreenshot) Base.drivr).getScreenshotAs(OutputType.FILE);
            sSCRDestination=System.getProperty("user.dir") + objDpConfig.getConfigData("ResultDownloadPath")+"\\"+sScreenShotName +".png";
            FileUtils.copyFile(fileSCR,new File(sSCRDestination));
        }catch ( Exception e)
        {
            Utils.addLog("FAIL","Exception occurred" +e.getMessage());
        }
        return  sScreenShotName+".png";
    }
    /********************************************************************************
    Method Name: addLog
     Parameters :Status ,Message
     Description By : this method is used to log the result for each step in the log4j and Extent report
     ************************************************************************/
    public  static  void addLog(String sStatus , String sMessage)
    {
        try{
            log4jLogger.info(sMessage);
            switch ( sStatus)
            {
                case "INFO":
                {
                    extLogger.log(LogStatus.INFO ,sMessage);break;
                }
                case"PASS":
                {
                    extLogger.log(LogStatus.PASS ,sMessage);break;
                }
                case"FAIL":
                {
                    extLogger.log(LogStatus.FAIL ,sMessage);break;
                }
                case"ERROR":
                {
                    extLogger.log(LogStatus.ERROR ,sMessage);break;
                }
                case"SKIP":
                {
                    extLogger.log(LogStatus.SKIP ,sMessage);break;
                }
                default:
                    extLogger.log(LogStatus.INFO,"Enter correct status for log method");
                    log4jLogger.info("Enter correct status for log method");
            }
        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
    }
    /***********************************************************************
     Method Name :Kill Process
     Parameters : processName
     Description By : this method is used to kill the given process
     *************************************************************************/
    public  static void  killProcess (String sProcessName)
    {
        try
        {
            Runtime.getRuntime().exec("taskkill /F /IM "+sProcessName+".exe");

        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
    }
    /***********************************************************************
     MethodName : exceptioHandler
     Parameter : exception
     Description : this method is used to log exception

     ********************************************************************/
    public  static void  exceptioHandler(Exception e)
    {
        try
        {
            Utils.addLog("FAIL","Exception Occurred & Message" + e.getMessage());
            Utils.addLog("FAIL","Exception Occurred & Name " + e.getClass().getSimpleName());
            Utils.addLog("FAIL",extLogger.getTest().getName() + "Test failed" +Utils.extLogger.addScreenCapture(Utils.screenShoots(extLogger.getTest().getName())));

        }catch (Exception eH)
        {
            Utils.addLog("FAIL","Exception Occurred" + eH.getMessage());
        }
    }
    /***************************************************
    MethodName: deleteFolder
     Parameter : fileFolder
     Description By : function to perform delete folder
     **************************************************/
    public  static  void deleteFolder(File fileFolder)
    {
        boolean bIsFolderExist=false;
        try
        {
            bIsFolderExist=fileFolder.exists();
            //if folder exist deleting its subfolders ,files
            if (bIsFolderExist)
            {
                //delelting all the sub folders and files under the given folder
                for (File subFileOrDir : fileFolder.listFiles())
                {
                    //checking whether it is a directory and delelting if not
                    if(subFileOrDir.isDirectory())
                    {
                        deleteFolder(subFileOrDir); //deleting the directory
                    }else
                    {
                        subFileOrDir.delete();
                    }
                }
                fileFolder.delete();
            }else
            {
                Utils.addLog("INFO" ,"Deletable file and folder does not exist");
            }
        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
    }
    /************************************************************************************
    Methode Name : randomValues
     Description  :function to generate random values using correct date and time
     ******************************************************************************/
    public static String[] randomValues()
    {
        String[] sRandomValues=null;
        SimpleDateFormat sdfFormat;
        Calendar calCal;
        String sAppender;
        try
        {
            sdfFormat=new SimpleDateFormat("ddMMyymmss");
            sRandomValues = new String[9];
            calCal =Calendar.getInstance();
            sAppender=sdfFormat.format(calCal.getTime());
            sRandomValues[0]="ATestF" + sAppender; //Account Name
            sRandomValues[1]="test1" + sAppender; //Last Name
            sRandomValues[2]=sRandomValues[1] + "@gmail.com"; //Email
            sRandomValues[3]="CTest" + sAppender; //contact Name
            sRandomValues[4]="OprTest" + sAppender; //opportunuty Name Name
            sRandomValues[5]="GrpTest" + sAppender; //group Name
            sRandomValues[6]="LTest" + sAppender; //Lead Name
            sRandomValues[7]="TestMid" + sAppender; //Middle Name
            sRandomValues[8] = sRandomValues[6] +"" +sRandomValues[7]+""+sRandomValues[1];//First name +Middle Name+Last Name
        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
        return  sRandomValues;
    }
    /*****************************************************************
    Method Name : randomPhone
     Description : function to generate random 10 digit phone number
     *****************************************************************/
    public static Long randomPhone()
    {
        try
        {
            return (long)(Math.random()*100000000 + 9000000000L); //return 10 digit phone number

        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
        return null;
    }
    /********************************************************************
    Methode :getDateByFormat
     Description: get a current local DateByFormat "yyyy-MM-dd"
     **************************************************************/
    public  static  String getDateByFormat()
    {
        DateTimeFormatter dtfFormat;
        LocalDate dtLocalDate;
        String sRetValYMD;
        try
        {
            dtfFormat =DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dtLocalDate=LocalDate.now();
            sRetValYMD=dtfFormat.format(dtLocalDate);
            return  sRetValYMD;  //returning value in "yyyy-MM-dd"
        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
        return  null;
    }
    /*************************************************************
    Method Name : randomEmail
     Description :function to generate email using current date and time
     ***********************************************************/
    public  static String randomEmail()
    {
        String sEmail =null;
        SimpleDateFormat sdfFormat;
        String sDate;
        try
        {
            sdfFormat =new SimpleDateFormat("ddMMyyyyHHmmss"); //formating to ddMMyyHHmmss
            sDate=sdfFormat.format(new Date()); //capturing the dtae in ddMMyyyyHHmmss
            sEmail="DOP_" + sDate + "@xyz.com";

        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
        return  sEmail;
    }
    /********************************************************************
    Method Name :randomPIN
     Description: function to generate random PIN using 4 digit number
     *****************************************************************/
    public  static String randomPIN()
    {
        Random rnd =new Random();
        int number =rnd.nextInt(9999);
        String PIN=String.format("%04d",number);
        return  PIN;
    }
    /*******************************************************************
    Method Name :randomPINReminder
     Description: function to generate random PIN reminder using alphabets
     ****************************************************************/
    public  static String randomPINReminder()
    {
        String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder sb=new StringBuilder();
            Random random=new Random();
            int length=7;
            for (int i=0;i<length;i++)
            {
                int index=random.nextInt(alphabet.length());
                char randomChar=alphabet.charAt(index);
                sb.append(randomChar);
            }
            String randomPINReminder=sb.toString();
            return  randomPINReminder;
    }
    /*******************************************************************
     Method Name :cleanResultsFolder
     Description: this method is used to clean the given folder path
     ****************************************************************/
    public static void cleanResultsFolder()
    {
        String sResultPath;
        try
        {
            sResultPath=objDpConfig.getCurrentDirectory()+objDpConfig.getConfigData("ResultDownloadPath");
            FileUtils.cleanDirectory(new File(sResultPath));

        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
    }
    /*******************************************************************
     Method Name :verifyDirectoryExistence
     parameter :path ,folder name
     Description: function to verify LatestCSVFile directory existence or create new directory
     ****************************************************************/
    public static void verifyDirectoryExistence(String sPath ,String sFolderName)
    {
        String sDirectoryPath=objDpConfig.getCurrentDirectory()+"\\" +sPath+"\\" +sFolderName;
        File fileTmpDir;
        boolean bIsExist;
        try
        {
            fileTmpDir =new File(sDirectoryPath);
            bIsExist= fileTmpDir.exists();
            if(bIsExist==true)
            {
                Utils.addLog("FAIL","Directory Exist" + sFolderName);
            }
            else
            {
                fileTmpDir.mkdir();
                Utils.addLog("FAIL","created directory " + sFolderName);
            }
        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
    }
    /*******************************************************************
     Method Name :listFilesForFolder
     parameter :fileFolder
     Description: function to get count of files under a specific directory
     ****************************************************************/
    public  String listFilesForFolder(File fileFolder)
    {
        try
        {
            for (File fileEntry : fileFolder.listFiles())
            {
                return  fileEntry.getName();
            }
        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception Occurred" + e.getMessage());
        }
        return null;
    }
    /*******************************************************************
     Method Name :getData
     Description: function to load data from variable.properties file
     ****************************************************************/
    public static String getData(String variableName) throws IOException {
        FileInputStream intStream;
        Properties properties=new Properties();
        intStream=new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//variables.properties");
        properties.load(intStream);
        String data=properties.getProperty(variableName);
        return  data;
    }

}
