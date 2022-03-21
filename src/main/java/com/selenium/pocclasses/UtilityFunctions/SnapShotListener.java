package com.selenium.pocclasses.UtilityFunctions;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class SnapShotListener extends  Utils implements ITestListener {
    private  static  String fileSeperator =System.getProperty("file.separator");

    /*************************************************
     Method Name :onTestFailure
     Parameters : result
     Description :this method is used capture screenshot on failure
     **************************************************/
    @Override
    public  void  onTestFailure(ITestResult result)
    {
        try
        {
          addLog("FAIL",result.getMethod().getMethodName() +"Test Failed");
          extLogger.addScreenCapture(screenShoots(result.getName()));
        }catch (Exception e)
        {
            exceptioHandler(e);
        }
    }

    /******************************************************
    Method Name :onTestStart
     parameter :result
     *********************************************/
   @Override
    public  void  onTestStart(ITestResult result){

    }

    /******************************************************
     Method Name :onTestSuccess
     parameter :result
     *********************************************/
    @Override
    public  void  onTestSuccess(ITestResult result){

    }
    /******************************************************
     Method Name :onTestSkipped
     parameter :result
     *********************************************/
    @Override
    public  void  onTestSkipped(ITestResult result){

    }
    /******************************************************
     Method Name :onTestFailedButWithinSuccessPercentage
     parameter :result
     *********************************************/
    @Override
    public  void  onTestFailedButWithinSuccessPercentage(ITestResult result){

    }
    /******************************************************
     Method Name :onStart
     parameter :context
     *********************************************/
    @Override
    public  void  onStart(ITestContext context){

    }
    /******************************************************
     Method Name :onFinish
     parameter :context
     *********************************************/
    @Override
    public  void  onFinish(ITestContext context){

    }

}

