package com.company.socket.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil
{
  private String configPath = null;

  private Properties props = null;

  public PropertiesUtil()
    throws IOException
  {
    InputStream in = new FileInputStream(new File(MyPath.getProjectPath() + "\\constants.properties"));
    this.props = new Properties();
    this.props.load(in);

    in.close();
  }

  public String readValue(String key) throws IOException {
    return this.props.getProperty(key);
  }

  public Map<String, String> readAllProperties() throws FileNotFoundException, IOException
  {
    Map map = new HashMap();
    Enumeration en = this.props.propertyNames();
    while (en.hasMoreElements()) {
      String key = (String)en.nextElement();
      String property = this.props.getProperty(key);
      map.put(key, property);
    }
    return map;
  }

  public void setValue(String key, String value) throws IOException {
    Properties prop = new Properties();
    InputStream fis = new FileInputStream(this.configPath);

    prop.load(fis);

    OutputStream fos = new FileOutputStream(this.configPath);
    prop.setProperty(key, value);

    prop.store(fos, "last update");

    fis.close();
    fos.close();
  }
}