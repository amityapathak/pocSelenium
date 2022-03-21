package com.selenium.pocclasses.UtilityFunctions;

import com.selenium.pocclasses.Base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.EOFException;
import java.time.Duration;

/********************************************
Class Name: Browser function related
 Description : This Class is used to implement the browser related generic function
 ***********************************************/

public class BrowserFunctions  extends Base {
    /**********************************************************
     Method Name: isElementPresent
     Parameters : ElementDesc ,timeinseconds
     Description :This method is used to check whether the given element is present on the page
     Return value: Boolean Flag as True if the web element is present
     ************************************************************/
    public static boolean isElementPresent(WebElement eleDesc ,int iTimeOutInSeconds)
    {
        boolean bElmentPresence=false;  //Declared and Initialized to false to assign return value
        try
        {
           //Declaration of Webdriver wait
            WebDriverWait wait=new WebDriverWait(drivr, Duration.ofSeconds(iTimeOutInSeconds)) ;
            WebElement weObj=wait.until(ExpectedConditions.visibilityOf(eleDesc));
            if (!weObj.getSize().equals(0))
            {
                bElmentPresence=true;
            }
        }
        catch (Exception e)
        {
           Utils.exceptioHandler(e);
        }
        return  bElmentPresence;
    }
    /**********************************************************
     Method Name: webElementEdit
     Parameters : eleEditElement ,strValueInTxtField ,strElementName
     Description :This method is used to send the text in text box
     Return value: Boolean Flag as True if the web element is present
     ************************************************************/
 public static  boolean webElementEdit(WebElement eleEditElement ,String sValueInTxtField ,String sElementName)
 {
     boolean bIsEditBtnExist=false;
     try{
         if(bIsEditBtnExist)
         {
             eleEditElement.clear();
             eleEditElement.sendKeys(sValueInTxtField);
             Utils.addLog("PASS","Entered Value:"+sValueInTxtField +"in text field:"+sElementName);
         }else
         {
             Utils.addLog("FAIL",sElementName+"Element do not exist");
         }
     }catch (Exception e)
     {
         Utils.exceptioHandler(e);
     }
     return  bIsEditBtnExist;
 }
    /**********************************************************
     Method Name: webElementClick
     Parameters : eleEditElement ,strElementName
     Description :This method is used to click on webElement
     Return value: Boolean Flag as True if the web element is present
     ************************************************************/
    public static boolean webElementClick(WebElement eleEditElement ,String sElementName)
    {
        boolean bIsEditExist=false;
        try
        {
            if(bIsEditExist)
            {
                WebDriverWait wait=new WebDriverWait(drivr,50);
                WebElement weObj=wait.until(ExpectedConditions.elementToBeClickable(eleEditElement));
                if(!weObj.getSize().equals(0))
                    {
                        try
                        {
                            Actions actions=new Actions(drivr) ;
                            actions.moveToElement(eleEditElement).perform();
                            eleEditElement.click();
                        }catch (Exception e)
                        {
                            JavascriptExecutor executor=(JavascriptExecutor) drivr;
                            executor.executeScript("arguments[0].click;",eleEditElement);
                        }
                        Utils.addLog("PASS","Clicked on Web Element:"+sElementName);
                    }else{
                    Utils.addLog("FAIL",sElementName+"Element is not clickable");
                }
            }
            else{
                Utils.addLog("FAIL",sElementName+"Element do not Exist");
            }
        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bIsEditExist;
    }
    /**********************************************************
     Method Name: webElementDropDown
     Parameters : eleDropDown,strValueInTxtField ,strElementName
     Description :This method is used to click on value of dropdown
     ************************************************************/
    public static boolean webElementDropDown(WebElement eleDropDown, String sValueInTxtField,String sElementName)
    {
        boolean bIsDropDownExist=false;
        try
        {
            if(bIsDropDownExist)
            {
                eleDropDown.sendKeys(sValueInTxtField);
                SeleniumWaits.performdelay(3);
                Utils.addLog("PASS","Choosen"+sValueInTxtField+"from dropdown list box" +sElementName);
            }
            else
            {
                Utils.addLog("FAIL",sElementName+ "Element do not exist");
            }
        }catch ( Exception e)
        {
            Utils.exceptioHandler(e);
        }return  bIsDropDownExist;
    }
    /**********************************************************
     Method Name: switchBackFromFrame
     ************************************************************/
    public static boolean switchBackFromFrame(){
        boolean bframeStatus=false;
        try
        {
            drivr.switchTo().defaultContent();
            bframeStatus=true;
        }
        catch ( Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bframeStatus;
    }
    /**********************************************************
     Method Name: scrollIntoViewElement
     Description :scrollIntoView the Element
     ************************************************************/
    public static boolean scrollIntoViewElement(WebElement eleListAcessType ,String sElementName)
    {
        boolean bReturnValue=false;
        try
        {
            JavascriptExecutor js=(JavascriptExecutor)drivr;
            js.executeScript("arguments[0].scrollIntoView;",eleListAcessType);
            Utils.addLog("PASS","ScrollIntoView done for:"+sElementName);
            bReturnValue=true;
        }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bReturnValue;
    }
    /**********************************************************
     Method Name: getWebElementByString
     Parameters :sPathValue ,sLocatorType
     Description Function to identify the element with the help of locator type,path and to return the webelement
     ************************************************************/
    public static WebElement getWebElementByString(String sPathValue ,String sLocatorType )
    {
        WebElement eleReturn=null;
        try
        {
            switch (sLocatorType)
            {
                case "XPATH":
                    eleReturn=drivr.findElement(By.xpath(sPathValue));
                    break;

                case "CSS":
                    eleReturn=drivr.findElement(By.cssSelector(sPathValue));
                    break;
                case "PartialLinkText":
                    eleReturn=drivr.findElement(By.partialLinkText(sPathValue));
                    break;
                case "LinkText":
                    eleReturn=drivr.findElement(By.linkText(sPathValue));
                    break;
                default:
                    Utils.addLog("INFO","Kindly pass the valida data");
            }
        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return eleReturn;
    }
    /**********************************************************
     Method Name: performSendKey
     Parameters :eleObj ,sKeyType
     Description :Function to perform keyboard action like SHIFT ,ENTER etc
     ************************************************************/
    public static boolean performSendKey(WebElement eleObj , String sKeyType)
    {
        boolean bReturnSendKey=false;
        try
        {
            switch (sKeyType)
            {
                case "ENTER":
                    eleObj.sendKeys(Keys.ENTER);
                    break;

                case "SHIFT":
                    eleObj.sendKeys(Keys.SHIFT);
                    break;
                case "CLEAR" :
                    eleObj.sendKeys(Keys.CLEAR);
                    break;
                default:
                    Utils.addLog("INFO","Kindly pass the valid ketType");
            }
            SeleniumWaits.performdelay(0);
            bReturnSendKey=true;
            Utils.addLog("INFO",sKeyType+" SendKey performed");
        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bReturnSendKey;
    }
    /**********************************************************
     Method Name: getElementText
     Parameters :eleEditElement ,strValueInTxtField ,strElementName
     Description :This method is used to send keys in the text box
     ************************************************************/
    public  static String getElementText(WebElement eleObj ,String sElementName)
    {
        String sReturnTextVal =null;
        boolean bElementPrsnt=false;
        try
        {
            if(bElementPrsnt)
            {
                sReturnTextVal=eleObj.getText();
            }else
            {
                Utils.addLog("FAIL",sElementName+"Element do not Exist");
            }
        }catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  sReturnTextVal;
    }
    /**********************************************************
     Method Name: safeJavaScriptClick
     Parameters :WebElement element
     Description :This method is used to perform click operation using JavaScript Executor
     ************************************************************/
    public void safeJavaScriptClick (WebElement element)
    {
        if(element.isEnabled() && element.isDisplayed())
        {
            System.out.println("Clicking on element with using Java Script click");

            ((JavascriptExecutor) drivr).executeScript("arguments[0].click();",element);
        }
        else
        {
            System.out.println("Unable to click on element");
        }
    }
    /**********************************************************
     Method Name: safeJavaScriptEnterText
     Parameters :WebElement element ,String textValue
     Description :This method is used to perform click operation using JavaScript Executor
     ************************************************************/
    public void safeJavaScriptEnterText (WebElement element ,String textValue)
    {
        if(element.isEnabled() && element.isDisplayed())
        {
            System.out.println("Enter txt using JavaScriptExecutor");

            ((JavascriptExecutor) drivr).executeScript("arguments[0].value='"+textValue+"';",element);
        }
        else
        {
            System.out.println("Unable to Enter the txt in the txt field");
        }
    }


}
