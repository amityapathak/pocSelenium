package com.selenium.pocclasses.Pages;

import com.selenium.pocclasses.Base.Base;
import com.selenium.pocclasses.UtilityFunctions.BrowserFunctions;
import com.selenium.pocclasses.UtilityFunctions.SeleniumWaits;
import com.selenium.pocclasses.UtilityFunctions.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PageClassTest extends Base {
    WebDriver drivr;
    @FindBy (how = How.XPATH ,using = "//a[contains(@href,'https://demoqa.com')]")
    public WebElement btnJoinUs;

    /***************************************
    Method Name :LaunchPage
     Parameter: webDriver
     Description by: constructor to initializes the element of the page
     ****************************************/
    public PageClassTest(WebDriver driver)
    {
        this.drivr=driver;
        PageFactory.initElements(driver,this);
    }

    /***************************************
     Method Name :joinNow
     Parameter:
     Description by: method to click on join now
     ****************************************/
    public void clickJoinNow()
    {
        /*boolean bReturn=false;
        try
        {*/
            SeleniumWaits.performdelay(5);
             BrowserFunctions.webElementClick(btnJoinUs,"Join Us");
       /* }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bReturn;*/
    }




}

