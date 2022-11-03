package com.selenium.pocclasses.Base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.pocclasses.DataProvider.DpConfiguration;
import com.selenium.pocclasses.DataProvider.DpSQLServer;
import com.selenium.pocclasses.UtilityFunctions.SeleniumWaits;
import com.selenium.pocclasses.UtilityFunctions.Utils;
import org.apache.logging.log4j.LogManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.selenium.pocclasses.UtilityFunctions.Constants.*;
import static com.selenium.pocclasses.UtilityFunctions.Constants.ApplicationURL_TestCloud_NR;

public class Base {
    public static WebDriver drivr;
    public static ExtentReports extReport;
    public static ExtentTest extLogger;
    public static Logger log4jLogger= LogManager.getLogger(Base.class.getName());
    public static DpConfiguration objDpConfig=new DpConfiguration();
    public static boolean bCIBuild;
    public static Instant instStartTime;
    public static Instant instEndTime;
    public static Duration durTimeElasp;
    public static String sTestClassName;
    public static String sStatus;


    /*************************************************************
     Method Name : initializeBrowser
     Parameters : No Parameter
     Description By : Initialize the extent report ,start browser ,and to check whether "data directory exit or not.
     If not it will create new "Data" directory
     ****************************************************************/
    public static void  initializeBrowser()
    {
        startBrowser(objDpConfig.getConfigData("Browser"));
        Utils.addLog("INFO","Browser Information:" +objDpConfig.getConfigData("Browser"));

    }
    /*************************************************************
     Method Name : startBrowser
     Parameters : Browser Name
     Description By : Method to declare the driver ,maximize the browser window ,delete all cookies ,to set page load timeout
     and implicit wait time
     ****************************************************************/

    public static void startBrowser(String sBrowserName) {
        try{
            switch (sBrowserName){
                case "Chrome":{
                    ChromeOptions cOptions=new ChromeOptions();
                    cOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("load-extension"));
                    cOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("--disable-extensions"));
                    cOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                    cOptions.setExperimentalOption("excludeSwitches", Arrays.asList("--disable-extensions"));
                    Map<String,Object> prefs=new HashMap<String,Object>();
                    prefs.put("download.default_directory",objDpConfig.getCurrentDirectory() + objDpConfig.getConfigData("LatestResultDownloadPath"));
                    prefs.put("profile.default_content_settings_values.notifications",2);
                    if(System.getProperty("os.name").toLowerCase().contains("windows"))
                    {
                        drivr=WebDriverManager.chromedriver().capabilities(cOptions).create();
                        //System.setProperty("webdriver.chrome.driver",objDpConfig.getCurrentDirectory() + objDpConfig.getConfigData("ChromeDriverPath"));
                    }
                    else
                    {
                        System.setProperty("webdriver.chrome.driver",objDpConfig.getConfigData("ChromeDriverPath"));
                       // cOptions.addArguments("--headless");
                        cOptions.addArguments("--no-sandbox");
                        cOptions.addArguments("--incognito");
                        cOptions.addArguments("--disable-dev-shm-usage");
                    }
                    cOptions.addArguments("--screenshot-all");
                    cOptions.addArguments("--disable-extensions");
                    cOptions.addArguments("--disable-infobars");
                    cOptions.setExperimentalOption("prefs",prefs);
                    cOptions.addArguments("--proxy-bypass-list=*.dish.com;*cfdish.io");
                    if(!(System.getenv().get("COMPUTERNAME").toUpperCase().contains("VDIOSVNPX024")))
                    {
                        cOptions.addArguments("--proxy-server=http://serverproxy-np.dish.com:8080");
                        cOptions.addArguments("--no-proxy-server");
                    }
                    cOptions.setExperimentalOption("prefs",prefs);
                    cOptions.setExperimentalOption("useAutomationExtension", false);
                    drivr=new ChromeDriver(cOptions);
                    drivr.manage().window().maximize();
                    drivr.manage().deleteAllCookies();
                    drivr.manage().timeouts().pageLoadTimeout(80 , TimeUnit.SECONDS);
                    drivr.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
                    Utils.addLog("PASS",sBrowserName +"Browser Invoked");
                    break;

                }
                case "IE":{
                    //drivr=new InternetExplorerDriver();
                    drivr=WebDriverManager.iedriver().create();
                    break;
                }
                default:
                    Utils.addLog("FAIL","entered message is"+sBrowserName+", Not avaialable");
            }

        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
    }
    /************************************************************************
     Method Name  :enterURL
     Parameters  :sURL
     Description By : Method to enter the URL and wait for label "username" to load
     *****************************************************************************/
    public static void enterURL(String sURL)
    {
        boolean bVisibleLogIn =false;
        try
        {
            drivr.get(sURL);
            SeleniumWaits.performdelay(1);
            Utils.addLog("PASS","Entered URL"+sURL);
            String sTitle=drivr.getTitle();
            Utils.addLog("INFO","Title Found as:" +sTitle);
        }catch (Exception e)
        {
            closeAndQuitDriver();
            endTestAndFlush();
            Utils.exceptioHandler(e);
        }
    }
    /************************************************************************
     Method Name  : initializeExtentReport
     Parameters  : No parameter
     Description By : initialize the extent report and passing the path where to create the extent report
     *****************************************************************************/
    public static void initializeExtentReport()
    {
        try
        {
            extReport=new ExtentReports(Utils.resultFilePath());
            log4jLogger.info("Initialize the extent report");
        }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
    }
    /************************************************************************
     Method Name  : endTestAndFlush
     Parameters  : No parameter
     Description By : Method to end test in extent report and Flush
     *****************************************************************************/

    public static void endTestAndFlush()
    {
        try{ Utils.addLog("PASS","End and Flush");
            extReport.endTest(extLogger);
            extReport.flush();
            log4jLogger.info("End and Flush ExtentReport");

        }
        catch (Exception e)
        {
            log4jLogger.info(e.getMessage());
            Utils.exceptioHandler(e);
        }
    }
    /************************************************************************
     Method Name  : closeAndQuitDriver
     Parameters  : No parameter
     Description By : Method to quit driver
     *****************************************************************************/
    public static void closeAndQuitDriver()
    {
        try
        {
            Utils.addLog("PASS","Clsoing and quitting the driver object");
            drivr.close();
            drivr.quit();
        }
        catch (Exception e)
        {
            extLogger.log(LogStatus.FAIL ,e.getMessage());
            Utils.exceptioHandler(e);
        }
    }
    /************************************************************************
     Method Name  : before suit
     Parameters  : No parameter
     Description By : This method is used to fetch value of variable "CIBUILd"using system getproperty
     *****************************************************************************/
@BeforeSuite
    public void beforeSuite()
    {
        try
        {
          if ((!(System.getProperty("CIBuild")==null)) && System.getProperty("CIBuild").equals("True"))
          {
              bCIBuild=true;
          }
          Utils.killProcess("chromedriver");
          initializeExtentReport();
          extLogger=extReport.startTest("CRM Sandbox StartUp Activities");
        }
        catch (Exception e)
        {
            extLogger.log(LogStatus.FAIL,e.getMessage());
            Utils.exceptioHandler(e);
        }
    }
    /**************************************************************************
    Method Name :beforeClass
     Description By : This Method is used in order to Initializes the extent report ,start browser
     **************************************************************************/
    @BeforeClass
    public void beforeClass()
    {
        try
        {
            initializeBrowser();
        }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
    }
    /**************************************************************************
     Method Name :beforeMethod
     Description By : This Method is used in order to delete all cookies ,to call the enter URL function
     **************************************************************************/
    @BeforeMethod
    public void beforeMethod()
    {
        try
        {
            enterURL(objDpConfig.getApplicationURL());
            instStartTime=Instant.now();
        }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
    }
    /**************************************************************************
     Method Name :afterMethod
     Description By : This Method is used to logout from application
     **************************************************************************/
    @AfterMethod
    public void afterMethod(ITestResult testResult)
    {
        DpSQLServer dpPushToSQL=new DpSQLServer();
        String sCurrentDirectory=null;
        //need to make it configurable
        String sEnvData="test";
        String sJenkinDailyJob=null;
        //need to make it configurable
        String sDialyJobYesorNo="Yes";
        String sJobType=null;
        boolean bLogout=true;

        instEndTime=Instant.now();
        //System.out.println(instEndTime);
        instStartTime=Instant.now();
       // System.out.println(instStartTime);
        durTimeElasp=Duration.between(instStartTime,instEndTime);
        try
        {
            sTestClassName=testResult.getTestClass().getName();
            if(!bLogout)
                Utils.addLog("FAIL","Logout Failed");
            sCurrentDirectory=System.getProperty("user.dir");
            sEnvData=new String(Files.readAllBytes(Paths.get(sCurrentDirectory + "\\src\\test\\resources\\TestData\\environment_Data.txt")));
            sJenkinDailyJob=new String(Files.readAllBytes(Paths.get(sCurrentDirectory +"\\" + "Jenkinsdailyjob.txt")));
            if(sJenkinDailyJob.contains("DailyJob"))
                sDialyJobYesorNo="yes";
            else
                sDialyJobYesorNo="No";
        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        if(sEnvData.contains("test"))
        {
            if(bCIBuild)
            {
                if(testResult.getStatus()==ITestResult.SUCCESS)
                {
                    sStatus="PASSED";
                    try
                    {
                       //dpPushToSQL.pushToSQLServer(strStatus ,strTestClassName .sEnvData ,durTimeElasp);
                    }
                    catch (Exception e)
                    {
                        Utils.exceptioHandler(e);
                    }
                }
                else if(testResult.getStatus()==ITestResult.FAILURE)
                {
                    sStatus="FAILED";
                    try
                    {
                        //dpPushToSQL.pushToSQLServer(strStatus ,strTestClassName .sEnvData ,durTimeElasp);
                    }
                    catch (Exception e)
                    {
                        Utils.exceptioHandler(e);
                    }

                }
            } else if (sDialyJobYesorNo.equals("Yes"))
            {
                Utils.addLog("PASS","DailyJobYesorNo:" +sDialyJobYesorNo);
                sJobType="Jenkins";
            }
            else
            {
                Utils.addLog("PASS","DailyJobYesorNo:" +sDialyJobYesorNo);
                sJobType="Local";
            }
            if (testResult.getStatus()==ITestResult.SUCCESS)
            {
                sStatus="PASSED";
                try
                {
                    //dpPushToSQL.pushToSQLServer(strStatus ,strTestClassName .sEnvData ,durTimeElasp);
                }
                catch (Exception e)
                {
                    Utils.exceptioHandler(e);
                }
            }else if (testResult.getStatus()==ITestResult.FAILURE)
            {
                sStatus="FAILED";
                try
                {
                    //dpPushToSQL.pushToSQLServer(strStatus ,strTestClassName .sEnvData ,durTimeElasp);
                }
                catch (Exception e)
                {
                    Utils.exceptioHandler(e);
                }

            }
        }

    }
    /*********************************************************
     Method Name:afterClass
     Description By :This Method is used to close and quite driver
     **********************************************************/
    @AfterClass
    public void afterClass()
    {
        try
        {
            closeAndQuitDriver();
        }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
    }
    /*********************************************************
     Method Name: tear down
     Description By :This Method(After Test )is used to end test in the extent report and flush ,kill chromedriver and to delete the build directory
     **********************************************************/
    @AfterSuite(alwaysRun = true)
    public  void  tearDown()
    {
        String sFilePath=null;
        File file=null;
        extLogger=extReport.startTest(" sandbox closure activities");
        try
        {
            sFilePath=System.getProperty("user.dir") + "\\build";
            file= new File(sFilePath);
            SeleniumWaits.performdelay(2);
            Utils.deleteFolder(file);
            Utils.addLog("INFO","Deleted build content");
            endTestAndFlush();
        }
        catch (Exception e){
            Utils.exceptioHandler(e);
        }
    }

    /*********************************************************
     Method Name: enterURL
     Parameters :sAppURL
     Description By : Method to enter URl
     **********************************************************/
    public  static void  enterApplicationURL(String sAppName)
    {
      String sAppURL="" ;
      if (sAppName.toLowerCase().contains("Guest"))
      {
          sAppURL=ApplicationURL_TestCloud_Guest;
      }
      else if (sAppName.toLowerCase().contains("NR"))
      {
          sAppURL= ApplicationURL_TestCloud_NR;
          //We can enter multiple else if for different URL and fetch the value from Constatnt file (Utility Functions)

      }
      else{
            Utils.addLog("FAIL","need to enter APP URL");
        }
      enterURL(sAppURL);
      instStartTime=Instant.now();
    }

}
