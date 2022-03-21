package com.selenium.pocclasses.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DpProperties {
    public  DpConfiguration objDpConf=new DpConfiguration();

    public void writeDataToPropertyFile(String fileName ,String key ,String value)
    {
        try
        {
            String path =System.getProperty("user.dir");
            String filePath="//src//test//resources//TestData//"+fileName;
            File file=new File(path +"//"+filePath);
            Properties prop=new Properties();
            FileOutputStream fos=new FileOutputStream(file,true);
            prop.setProperty(key ,value);
            prop.store(fos,value);
        }
        catch(Exception e)
        {
            System.out.println("unable to write data to property file");
        }
    }
    public void clearPropertyData(String fileName ,String key)
    {
        try
        {
            String path =System.getProperty("user.dir");
            String filePath="//src//test//resources//TestData//"+fileName;
            File file=new File(path +"//"+filePath);
            //FileInputStream fis=new FileInputStream(file);
            Properties prop=new Properties();
            //prop.load(fis);
            //String value= prop.getProperty(key);
            //return value;
            prop.remove(key);
        }
        catch(Exception e)
        {
            System.out.println("unable to clear data to property file");
        }

    }

    public String readPropertyData(String fileName ,String key)throws IOException
    {
        try
        {
            String path =System.getProperty("user.dir");
            String filePath="//src//test//resources//TestData//"+fileName;
            File file=new File(path +"//"+filePath);
            FileInputStream fis=new FileInputStream(file);
            Properties prop=new Properties();
            prop.load(fis);
            String value= prop.getProperty(key);
            return value;
        }
        catch(Exception e)
        {
            System.out.println("unable to read data to property file");
        }
        return fileName;
    }

}
