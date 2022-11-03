package com.selenium.test;

import com.selenium.pocclasses.Base.Base;
import com.selenium.pocclasses.Pages.PageClassTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/*******************************************************
Class Name :src/test/java/com.selenium.test/TestClass
 Description :This class is used to test Join Us
 *********************************************/
public class TestClass extends Base {

    /*********************************************
    Method Name:testJoinUs
     Parameters :No Parameter
     Description: This Method is used to test Join Us
     ********************************************/
    @Test(priority = 1)
    public void testJoinUs()
    {
        PageClassTest objPageClassTes=new PageClassTest(drivr);
        extLogger=extReport.startTest("Customer Front End");
        Assert.assertTrue(objPageClassTes.navigateToCustomerFrontEndPage(),"Testing Customer login Page navigation");
        //System.out.println("Testing testing");
    }
}
