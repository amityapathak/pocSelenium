package com.selenium.pocclasses.DataProvider;


import org.apache.poi.xssf.usermodel.XSSFChartSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*********************************************************************************************
 Class Name  :DpExcelData
 Description  :Excel related data and generic functions
 ***********************************************************************************************/
public class DpExcelData {
    public DpConfiguration objDpConf = new DpConfiguration();
    public static XSSFWorkbook xssWorkbook;
    public static File fileSRC;
    public static FileInputStream fileInputStream;

    /*********************************************************************************************
     Method Name  :DpExcelData
     Description  :Constructor to initialize
     Parameter : Excel Path
     ***********************************************************************************************/

    public DpExcelData(String sXLSFileName) {
        //creation of file object and passing it to excel path
        fileSRC = new File(".//" + objDpConf.getConfigData("ExcelPath") + sXLSFileName + ".xlsx");
        try {
            fileInputStream = new FileInputStream(fileSRC);
            xssWorkbook = new XSSFWorkbook(fileInputStream);

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*********************************************************************************************
     Method Name  :getCellData
     Description  :this method is used to fetch the values based on row and column in the given sheet
     Parameter : SheetName ,row no , Col no
     ***********************************************************************************************/

    public String getCellData(String sSheetName, int iRowNo, int iColNo) {
        String sCellData;
        try {
            sCellData = xssWorkbook.getSheet(sSheetName).getRow(iRowNo).getCell(iColNo).getStringCellValue().toString();
        } catch (Exception e) {
            return null;
        }
        return sCellData;

    }
    /*********************************************************************************************
     Method Name  :getRowCount
     Description  :this method is used to fetch the Row count
     Parameter : SheetName
     ***********************************************************************************************/
    public int getRowCount (String sSheetName)
    {
        int iNum=0;
        try{
            iNum=xssWorkbook.getSheet(sSheetName).getLastRowNum();
        }
        catch (Exception e)
        {
            iNum=0;
        }
        return  iNum;

    }
    /*********************************************************************************************
     Method Name  :getDataList
     Description  :provides product items and qty
     Parameter : SheetName
     ***********************************************************************************************/
    public Map <String ,String> getDataList (String sSheetName)
    {
        Map<String,String> mapItemList=new HashMap<>();
        String sProductName=null;
        String sProductQty=null;
        try
        {
            int iRowCount =xssWorkbook.getSheet(sSheetName).getLastRowNum();
            for (int iCount=1;iCount<=iRowCount;iCount++)
            {
                sProductName=xssWorkbook.getSheet(sSheetName).getRow(iCount).getCell(0).getStringCellValue().toString();
                sProductQty=xssWorkbook.getSheet(sSheetName).getRow(iCount).getCell(1).getStringCellValue().toString();
                mapItemList.put(sProductName,sProductQty);
            }

        }
        catch (Exception e)
        {

        }
        return mapItemList;
    }
    /*********************************************************************************************
     Method Name  :writeDataExcel
     Description  :provides product items and qty
     Parameter : filePath ,SheetName ,rowNumber ,colNumber,value
     ***********************************************************************************************/
    public void writeDataExcel(String sFilePath ,String sSheetName ,int iRowNumber ,int iColNumber ,String sValue) throws IOException
    {
        XSSFWorkbook xssWorkbook;
        XSSFSheet xssSheet;
        XSSFRow xssRow;
        FileInputStream fileInputStream=null;
        FileOutputStream fileOutputStream;
        try
        {
            fileInputStream =new FileInputStream(sFilePath);
            xssWorkbook =new XSSFWorkbook(fileInputStream);
            xssSheet=xssWorkbook.getSheet(sSheetName);
            xssRow=xssSheet.getRow(iRowNumber);
            fileOutputStream=new FileOutputStream(sFilePath);
            xssRow.createCell(iColNumber).setCellValue(sValue);
            xssWorkbook.write((fileOutputStream));

        }
        catch (Exception e)
        {

        }
    }

    }



