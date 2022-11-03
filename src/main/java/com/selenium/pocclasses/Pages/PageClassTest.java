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
    @FindBy (how = How.XPATH ,using = "(//small[text()='http://www.phptravels.net/login'])[1]")
    public WebElement linkCustomerFrontEnd;

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
     Method Name :navigateToCustomerFrontEndPage
     Parameter:
     Description by: method to click on navigateToCustomerFrontEndPage and navigate to Customer Front end login Page
     ****************************************/
    public boolean navigateToCustomerFrontEndPage()
    {
        boolean bReturn=false;
        try
        {
            SeleniumWaits.performdelay(5);
             BrowserFunctions.webElementClick(linkCustomerFrontEnd,"navigated to the page Successfully");
       }
        catch (Exception e)
        {
            Utils.exceptioHandler(e);
        }
        return  bReturn;
    }




}

