package com.selenium.pocclasses.UtilityFunctions;

import com.selenium.pocclasses.Base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/***************************************
Class Name :SeleniumWaits
 Description: This Class is used to write common reusable functions
 **********************************/
public class SeleniumWaits  extends Base {
    static  int timeInSeconds=40;

    /***************************************
     MethodName  : visibilityOfElmnt
     Parameter: WebElement
     Description: This method is used to wait for the element
     **********************************/

    public static boolean visibilityOfElmnt(WebElement eleDesc)
    {
        boolean bElmntVisible=false;
        try
        {
            WebDriverWait wait=new WebDriverWait(drivr, Duration.ofSeconds(timeInSeconds));
            WebElement weObj=wait.until(ExpectedConditions.visibilityOf(eleDesc));
            if(!weObj.getSize().equals(0))
            {
                bElmntVisible=true;
            }

        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return bElmntVisible;
    }

    /***************************************
     MethodName  : visibilityOfAllElmnt
     Parameter: List of WebElement
     Description: This method is used to check whether the given list of elements are present on the Page
     **********************************/
    public static boolean visibilityOfAllElmnt(List<WebElement> eleDesc)
    {
        boolean bElementsStatus=false;
        try
        {
            WebDriverWait wait=new WebDriverWait(drivr,Duration.ofSeconds(timeInSeconds));
            List<WebElement> weObj=wait.until(ExpectedConditions.visibilityOfAllElements(eleDesc));
            if(weObj.size()!=0)
            {
                bElementsStatus=true;
            }
        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bElementsStatus;
    }
    /***************************************
     MethodName  : waitForFluency
     Parameter: webElement
     Description: This method is used to perform fluent wait on a webelement till it is clickable
     **********************************/
    public static void waitForFluency(WebElement element)
    {
        Wait wait=new FluentWait(drivr).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    /***************************************
     MethodName  : elementToBeClickable
     Parameter: webElement
     Description: This method is used to check whether the given element is clickable in the page
     **********************************/
    public static boolean elementToBeClickable(WebElement eleDesc)
    {
        boolean bElementStatus=false;
        try
        {
            WebDriverWait wait=new WebDriverWait(drivr,Duration.ofSeconds(timeInSeconds));
            WebElement weObj=wait.until(ExpectedConditions.elementToBeClickable(eleDesc));
            if(!weObj.getSize().equals(0))
            {
                bElementStatus=true;
            }
        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return bElementStatus;

    }
    /***************************************
     MethodName  :frameToBeAvailableAndSwitchToIt
     Parameter: webElement of the Frame
     Description: method to wait till the frame is available and then switch to frame
     **********************************/
    public static boolean frameToBeAvailableAndSwitchToIt(WebElement eleFrame)
    {
        boolean bElementStatus=false;
        try
        {
            WebDriverWait wait=new WebDriverWait(drivr,Duration.ofSeconds(timeInSeconds)) ;
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(eleFrame));
            bElementStatus=true;
        }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bElementStatus;
    }
    /***************************************
     MethodName  :performdelay
     Parameter: intWait
     Description: this method is used to wait for specific time
     **********************************/
    public static void performdelay(int iDelay)
    {
        try
        {
            switch (iDelay)
            {
                case 0:
                     Thread.sleep(500);
                     break;
                case 1:
                     Thread.sleep(1000);
                     break;
                case 2:
                    Thread.sleep(2000);
                    break;
                case 3:
                    Thread.sleep(3000);
                    break;
                case 4:
                    Thread.sleep(4000);
                    break;
                case 5:
                    Thread.sleep(5000);
                    break;
                case 6:
                    Thread.sleep(6000);
                    break;
                case 7:
                    Thread.sleep(7000);
                    break;
                case 10:
                    Thread.sleep(10000);
                    break;
                case 20:
                    Thread.sleep(20000);
                    break;
                case 60:
                    Thread.sleep(60000);
                    break;
                default:
                    Utils.addLog("FAIL","Wait value is not proper");
            }
        }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }

    }
    /***************************************
     MethodName  :isElementPresent
     Parameter: by ,sElementName ,timeout
     Description: this method is used to wait untill the check the presence of element
     **********************************/
    public static boolean isElementPresent(By by, int timeoutsec ,String elementName)
    {
        boolean isPresent=true;
        if (elementName=="subscription")
        {
            drivr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
        else
        {
            drivr.manage().timeouts().implicitlyWait(timeoutsec,TimeUnit.SECONDS);
        }
        try
        {
            drivr.findElement(by);
        }catch ( Exception e)
        {
            if(elementName.equals("page title"))
            {
                isPresent=false;
                Utils.addLog("INFO","Exception occurred in 'isElementPresent' function for element" +elementName);
                Utils.exceptioHandler(e);
            }
        }
        drivr.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
        return  isPresent;
    }
    /***************************************
     MethodName  :checkVisibilityOfElmnt
     Parameter: by ,sElementName ,timeout
     Description: this method is used to wait untill the element is visible if not return false without printing exception messgae
     **********************************/
    public  static boolean checkVisibilityOfElmnt(By by ,int timeout)
    {
        boolean bElmntVisible=false;
        WebElement weObj=null;
        try
        {
            WebDriverWait wait=new WebDriverWait(drivr,Duration.ofSeconds(timeout));
            weObj=wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            if(!weObj.getSize().equals(0))
            {
                bElmntVisible=true;
            }
        }catch (Exception e)
        {

        }
        return  bElmntVisible;
    }
    /***************************************
     MethodName  :visibilityOfElmnt
     Parameter: by ,sElementName ,timeout
     Description: this method is used to wait untill the element is visible
     **********************************/
    public  static boolean visibilityOfElmnt(By by ,String sElementName,int timeout)
    {
        boolean bElmntVisible=false;
        WebElement weObj=null;
        try
        {
            WebDriverWait wait=new WebDriverWait(drivr,Duration.ofSeconds(timeout));
            weObj=wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            if(!weObj.getSize().equals(0))
            {
                bElmntVisible=true;
            }
        }catch (StaleElementReferenceException e)
        {

        }catch (Exception e)
        {
            Utils.addLog("INFO","Exception occurred in 'visibilityOfElmnt' function for element" +sElementName);
            Utils.exceptioHandler(e);
        }
        return  bElmntVisible;
    }
    /***************************************
     MethodName  :visibilityOfWebElmnt
     Parameter: WebElement
     Description: this method is used to wait for webElement
     **********************************/
    public  static boolean visibilityOfWebElmnt(WebElement eleDesc ,String sElementName)
    {
        boolean bElmntVisible=false;
        int attempts=0;
        WebElement weObj=null;
        try
        {
            drivr.manage().timeouts().implicitlyWait(timeInSeconds,TimeUnit.SECONDS);
            WebDriverWait wait=new WebDriverWait(drivr,Duration.ofSeconds(timeInSeconds));
            weObj=wait.until(ExpectedConditions.visibilityOf(eleDesc));

        }catch (StaleElementReferenceException e)
        {
            try
            {
                SeleniumWaits.performdelay(4);
                SeleniumWaits.performdelay(4);
                WebDriverWait wait=new WebDriverWait(drivr,8);
                weObj=wait.until(ExpectedConditions.visibilityOf(eleDesc));
            }
            catch (Exception e1)
            {
                Utils.addLog("FAIL","Exception occurred while checking the visibilty of element even after some timw" );
                Utils.exceptioHandler(e1);
            }

        }catch (Exception e)
        {
            Utils.addLog("FAIL","Exception occured for the element" +sElementName);
            Utils.exceptioHandler(e);
        }
        if (!weObj.getSize().equals(0))
        {
            bElmntVisible=true;
        }
        drivr.manage().timeouts().implicitlyWait(timeInSeconds,TimeUnit.SECONDS);
        return  bElmntVisible;

    }



}
