package com.selenium.pocclasses.DataProvider;


import com.selenium.pocclasses.Base.Base;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*****************************************************************************
 Class Name : CSVDataProvider
 parameters :
 Description By : This class is used to verify data in CSV files
 **************************************************************************/
public class DpCSV {
    //CSV file declaration
    public static File fileSrc;

    /*****************************************************************************
     Method Name : getResultDataFromCSVFile
     parameters :dir,csvFileName ,colNum ,fromRowNum ,toRowNum
     Description By : This method is used to fetch result data from CSV result file
     colNum is related to CSV Column number such as coulmn index
     fromRowNum is a first number of sublist and toRowNum is a last number in the sublist
     **************************************************************************/
    public List<String> getResultDataFromCSVFile(String sDir, String SCSVFileName, int iColNum, int iFromRowNum, int iToRowNum) {
        fileSrc = new File(sDir + "\\" + SCSVFileName);
        BufferedReader bufferedReader = null;
        String sLine = "";
        String sCSVSplitBy = ",\"";
        List<String> sGetData = new ArrayList<>();
        List<String> sRetnFinalData = new ArrayList<>(); //declare and initialized to false to assign return value
        String sData = null;
        String[] csvData = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(fileSrc));
            while ((sLine = bufferedReader.readLine()) != null) {
                //use comma as separator
                csvData = sLine.split(sCSVSplitBy);
                sGetData.add(csvData[iColNum]);
            }
            for (int iCount = 0; iCount < sGetData.size(); iCount++) {
                sData = sGetData.get(iCount).replace("\"", "");
                sRetnFinalData.add(sData);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();

                } catch (IOException e) {

                }
            }
        }
        sRetnFinalData = sRetnFinalData.subList(iFromRowNum, iToRowNum);
        return sRetnFinalData;
    }

    /*****************************************************************************
     Method Name : getDataFromCSVWithoutQuotes
     parameters :dir,csvFileName ,colNum ,fromRowNum ,toRowNum
     Description By : This method is used to fetch data from without double quotes from CSV file
     colNum is related to CSV Column number such as coulmn index
     fromRowNum is a first number of sublist and toRowNum is a last number in the sublist
     **************************************************************************/
    public List<String> getDataFromCSVWithoutQuotes(String sDir, String SCSVFileName, int iColNum, int iFromRowNum, int iToRowNum) {
        fileSrc = new File(sDir + "\\" + SCSVFileName);
        BufferedReader bufferedReader = null;
        String sLine = "";
        String sCSVSplitBy = ",";
        List<String> sGetData = new ArrayList<>();
        List<String> sFinalData = new ArrayList<>(); //declare and initialized to false to assign return value
        String sData = null;
        String[] csvData = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileSrc));
            while ((sLine = bufferedReader.readLine()) != null) {
                //use comma as separator
                csvData = sLine.split(sCSVSplitBy);
                sGetData.add(csvData[iColNum]);
            }
            for (int iCount = 0; iCount < sGetData.size(); iCount++) {
                sData = sGetData.get(iCount);
                sFinalData.add(sData);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();

                } catch (IOException e) {

                }
            }
        }

        sFinalData = sFinalData.subList(iFromRowNum, iToRowNum);
        return sFinalData;
    }

    /*****************************************************************************
     Method Name : getInputDataFromCSVFile
     parameters :dir,csvFileName ,colNum ,fromRowNum ,toRowNum
     Description By : This method is used to fetch input data from CSV Input file
     column is related to CSV column Number such as Column index
     **************************************************************************/
    public List<String> getInputDataFromCSVFile(String sCSVFileName, int iColNum) {
        String sPath = Base.objDpConfig.getCurrentDirectory() + Base.objDpConfig.getConfigData("InputFilePath") + sCSVFileName;
        BufferedReader brBufferedReader = null;
        String[] csvData = null;
        String sLine = "";
        String sCSVSplitBy = ",";
        List<String> sGetData = new ArrayList<>();
        try {
            brBufferedReader = new BufferedReader(new FileReader(sPath));
            while ((sLine = brBufferedReader.readLine()) != null) {
                //use comma as separator
                csvData = sLine.split(sCSVSplitBy);
                sGetData.add(csvData[iColNum]);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            if (brBufferedReader != null) {
                try {
                    brBufferedReader.close();

                } catch (IOException e) {

                }
            }
        }
        return sGetData;

    }

    /*****************************************************************************
     Method Name : getCellDataFromInputCSVFile
     parameters :csvFileName ,colNum ,rowNum
     Description By : This method is used to fetch individually column row data from csv input file
     **************************************************************************/
    public String getInputDataFromCSVFile(String sCSVFileName, int iRowNum, int iColNum) {
        String strPath = Base.objDpConfig.getCurrentDirectory() + Base.objDpConfig.getConfigData("InputFilePath") + sCSVFileName;
        BufferedReader bufferedReader = null;
        String[] csvData = null;
        String sLine = "";
        String sCSVSplitBy = ",";
        String sColList = null;
        String sRetnCellValue = null;
        List<String> sMyList = new ArrayList<>();
        int iCount = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader(strPath));
            while ((sLine = bufferedReader.readLine()) != null) {
                if (iRowNum == iCount) {
                    csvData = sLine.split(sCSVSplitBy);
                    sColList = csvData[iColNum];
                    sMyList.add(sColList);
                    break;
                }
                iCount = iCount + 1;
            }
            sRetnCellValue = sMyList.get(0);

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();

                } catch (IOException e) {

                }
            }
        }
        return sRetnCellValue;

    }
}




