package com.selenium.pocclasses.DataProvider;

import com.selenium.pocclasses.UtilityFunctions.Utils;

import java.sql.*;

public class DpDatabase {
    private Connection conConnection;
    private String sHostName;
    private  String sPortNumber;
    private  String sSid;
    private  String sURL;
    private Statement stmtStatement;
    private ResultSet rsetResultSet;
    private Connection sSqlConnection;

    /***************************************************
     Method Name : DpDatabase
     Description By : This constructor initializes the database variables that requires to connect to Oracle DB
     *************************************************/
    public  DpDatabase()
    {
        conConnection=null;
        sHostName=null;
        sPortNumber=null;
        sSid=null;
        String sdbURL ="jdbc:oracle:thin:@";
        sURL=sdbURL +sHostName +":"+sPortNumber+":"+sSid;
        //sUserName="tqa_user";
        //sPassword ="tqa$d1sh";
    }
    /***************************************************
     Method Name : openDBConnection
     Parameters : Url ,UserName ,Password
     *************************************************/

    public  void openDBConnection(String sURL ,String sUserName ,String sPassword)
    {
        try
        {
            String sDriverName ="com.microsoft.";
            Class.forName(sDriverName);
            conConnection= DriverManager.getConnection(sURL,sUserName,sPassword);
        }
        catch (ClassNotFoundException e)
        {
            Utils.addLog("FAIL",e.getMessage());
        }
        catch (SQLException e1)
        {
            Utils.addLog("FAIL",e1.getMessage());
        }
    }
    /***************************************************
     Method Name : runQuery
     Parameters : Query
     Description : Method to the execute the query and Store the values in the recordset
     *************************************************/
    public ResultSet runQuery(String sQuery)
    {
        try
        {
            stmtStatement=conConnection.createStatement();
            rsetResultSet=stmtStatement.executeQuery(sQuery);
        }
        catch (SQLException e)
        {
            Utils.addLog("FAIL",e.getMessage());
        }
        return  rsetResultSet;
    }
    /***************************************************
     Method Name : oracleCloseConnection
     Parameters :
     Description : Method to close the database connection from the oracle database
     *************************************************/
    public void oracleCloseConnection()
    {
        try{
            conConnection.close();
        }
        catch (SQLException e)
        {
            Utils.addLog("FAIL",e.getMessage());
        }

    }
    /***************************************************
     Method Name : getOneTimePassword
     Parameters : sEmailID
     Description : Method to generate One Time Password from the oracle database
     *************************************************/
    public  String getOneTimePassword(String sEmailID)
    {
        String sIsOneTimePassword=null;
        Utils.addLog("INFO","Started activities for One Time Password");
        String sQuery="select MESSAGE from Table Where type='Customer' abd date_cretaed>=sysdate -3";
        String sSQLURL="jdbc:oracle:thin:" +"tqa_user"+"/"+"tqa4d1sh"+"@"+"tbdiab"+":"+"1534"+":"+"TBDIA";
        String sMESSAGE="";
        try
        {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            //DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            sSqlConnection=DriverManager.getConnection(sSQLURL);
            stmtStatement=sSqlConnection.createStatement();
            stmtStatement.executeQuery(sQuery);
            rsetResultSet=stmtStatement.getResultSet();

            while(rsetResultSet.next())
            {
                sMESSAGE=rsetResultSet.getString("Message");
            }
            if (sMESSAGE.contains("OneTimePassword="))
            {
                String[] aArrayMessage=sMESSAGE.split("OneTimePassword");
                String[] aArrayMessage2=aArrayMessage[1].split("&Flag");
                sIsOneTimePassword=aArrayMessage2[0].trim();
                Utils.addLog("INFO","sIsOneTimePassword:"+sIsOneTimePassword);
            }
            else
            {
                Utils.addLog("FAIL","oneTimePassword Not Found:"+sMESSAGE);
            }
            sSqlConnection.close();


        }catch (SQLException e)
        {
            Utils.addLog("FAIL","SQL Exception:" +e.getMessage());
        }
        return  sIsOneTimePassword;
    }


}
