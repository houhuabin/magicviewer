/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.*;
import java.io.*;

/**
 * <p>Title: 读取系统配置文件</p>
 * <p>Description: 读取WEB-INF/classes目录下的.properties文件</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Gu Yuanyan
 * @version 1.0
 */
public class UserProperties {

    private String configFile = "property" + System.getProperties().getProperty("file.separator") + "config.properties";
    public Properties userProper;

    public UserProperties() {
        read(configFile);
    }

    public UserProperties(String fileName) {
        read(fileName);
    }

    public String getProperty(String proName) {
        if (userProper != null) {
            return userProper.getProperty(proName);
        }
        return null;
    }

    /**
     * 返回配置文件中的属性值
     * @param fileName .properties文件名，如：/sql.properties
     */
    private void read(String fileName) {
        if (fileName == null || fileName.trim().equals("")) {
            fileName = configFile;
        }
        InputStream in = null;
         userProper = new Properties();
        try {
            //in = getClass().getResourceAsStream(fileName);
             in = new FileInputStream(fileName);
            userProper.load(in);

        } catch (Exception e) {
            ReportInfo.reportError("读取.properties文件出错.fileName=" + fileName + ",", e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }

    }

    //写入中文字符串
    public void writeConfig() {
        String fileName = null;
        if (fileName == null || fileName.trim().equals("")) {
            fileName = configFile;
        }
        InputStream in = null;
        OutputStream out = null;
        Properties props = new Properties();
        try {
            in = getClass().getResourceAsStream(fileName);
            props.load(in);
            if (props != null) {
                props.setProperty("project_name", "钢铁流通企业销售系统");
                props.setProperty("project_username", "诚信钢铁");

                //英文的配置，用文件中的原值，会写入config1.properties文件
                String[] items = {
                    "project_url", "project_firstpage", "db_driver", "db_url",
                    "db_user", "db_password", "project_sa", "project_sa_pw"};
                String value = "";
                for (int i = 0; i < items.length; i++) {
                    System.out.println(items[i]);
                    value = props.getProperty(items[i]);
                    System.out.println(value);
                    props.setProperty(items[i], value);
                }
                out = new FileOutputStream(
                        "E:/HzicCbgc/jboss-3.2.6/server/default/deploy/HzicSale.war/WEB-INF/classes/config1.properties");
                props.store(out, "Config for zzs System");
               // props.store(out, value);
            }
        } catch (Exception e) {
            ReportInfo.reportError("读取.properties文件出错.fileName=" + fileName + ",",
                    e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }

    }
}
