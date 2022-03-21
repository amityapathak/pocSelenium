package com.selenium.pocclasses.DataProvider;

import com.selenium.pocclasses.UtilityFunctions.Utils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class DpSQLServer {
    /*****************************
    Method Name :DpSQLServer
     Description By : This Constructor Initializes the database variables that requires to connect the oracle DB
     **************************/
    public  DpSQLServer()
    {

    }
    /******************************************
    Method Name : openDBConnection
     *******************************************/
    public void openDBConnection (String sURL ,String sUserName ,String sPassword)
    {

    }
    /******************************************
     Method Name : oracleCloseConnection
     Description By : Method is used to close the DB connection from oracle database
     *******************************************/
    public void oracleCloseConnection()
    {

    }
    /******************************************
     Method Name : pushToSQLServer
     Parameters : sStatus,sTestClassName ,sLocalOrJenkinsOrCICI ,sEnv,durTimeElasped
     Description By : Method is used to login to the database and execute the query
     *******************************************/
    public  void  pushToSQLServer(String sStatus , String sTestClassName , String sEnv , String sLocalOrJenkinsOrCICI , Duration durTimeElasped)
    {
        Connection connConnection =null;
        String sTestType=null;
        Statement stmtStatement=null;
        ResultSet res=null;
        String sQuery=null;
        sTestType="Regression";
        SimpleDateFormat sdfFormat=null;
        String strCurrentDate=null;
        String maxTestID=null;

        try
        {
            sdfFormat=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            strCurrentDate=sdfFormat.format(new Date());
            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            }catch (Exception e)
            {
                Utils.addLog("FAIL",e.getMessage());
            }
            try
            {
                connConnection= DriverManager.getConnection("jdbc:SQLServer;datbaseName=xyz;user=amitya;password=amitya");
                Utils.addLog("INFO","Regression");
                Utils.addLog("INFO",sLocalOrJenkinsOrCICI);
                Utils.addLog("INFO","Wireless");
                Utils.addLog("INFO","TC:"+sTestClassName.substring(25));
                Utils.addLog("INFO","Cdate :"+strCurrentDate);
                Utils.addLog("INFO","Timeelasped :"+durTimeElasped.getSeconds());
                Utils.addLog("INFO","Status :"+sStatus.toString());
                Utils.addLog("INFO","WirelessRegressionTestSuit");
                Utils.addLog("INFO","Environment :"+sEnv);
                Utils.addLog("INFO","Test Type :"+sTestType.toString());

                maxTestID="Select * from DB";
                res=connConnection.createStatement().executeQuery(maxTestID);
                int iIncreaseTestID=0;
                while(res.next())
                {
                    iIncreaseTestID=res.getInt(1);
                }
                iIncreaseTestID=iIncreaseTestID+1;
                sQuery="INSERT INTO ";
                Utils.addLog("INFO","Query :"+sQuery);
                stmtStatement=connConnection.createStatement();
                stmtStatement.executeQuery(sQuery);
                res.close();
                stmtStatement.close();
                connConnection.close();


            }
            catch (Exception e)
            {
                Utils.addLog("FAIL",e.getMessage());
            }
        }
        catch (Exception e)
        {
            Utils.addLog("FAIL",e.getMessage());
        }
    }

    /************************************************
     Method Name : pushToSQLServer
     Parameters : sStatus ,TestClassName ,TestFlow ,Env ,Date ,etc
     Description By : This method is used to login to the database and execute the query to insert results
     *************************************************/
    public  void  pushResultToSQLServer(String sEnvironment , String sStatus , String sFlow_Type, String sPortin, String sICCID ,String IMEI ,String Email)
    {
        Connection connConnection =null;
        //String sTestType=null;
        Statement stmtStatement=null;
       // ResultSet res=null;
        String sQuery=null;
        //sTestType="Regression";
        SimpleDateFormat sdfFormat=null;
        String strCurrentDate=null;
       // String maxTestID=null;
        String sSourceSystem =null;

        try
        {
            sdfFormat=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            strCurrentDate=sdfFormat.format(new Date());
            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            }catch (Exception e)
            {
                Utils.addLog("FAIL",e.getMessage());
            }
            try
            {
                connConnection= DriverManager.getConnection("jdbc:SQLServer://  ;datbaseName=xyz;user=amitya;password=amitya");
                sQuery="INSERT INTO [RQ_DOP].[dbo].[tracking]([Date_time],[Source_System],[Environemnt],[ICCID],[IMEI]) VALUES ('"+
                        strCurrentDate+"','"+sPortin+"','"+sICCID+" ')";
                Utils.addLog("INFO","Query :"+sQuery);
                stmtStatement=connConnection.createStatement();
                stmtStatement.executeQuery(sQuery);
                stmtStatement.close();
                connConnection.close();


            }
            catch (SQLException e)
            {
                Utils.addLog("FAIL","SQLException"+e.getMessage());
            }
        }
        catch (Exception e)
        {
            Utils.addLog("FAIL"," pushResultToSQLServer "+e.getMessage());
        }
    }
}

