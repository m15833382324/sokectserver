package com.company.socket.util;

import java.io.IOException;
import java.util.ResourceBundle;

public class ProUtil
{
  public static String readParam(String paramName)
  {
    String paramValue = null;
    try {
      PropertiesUtil pro = new PropertiesUtil();
      paramValue = pro.readValue(paramName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return paramValue;
  }
  public static String readPro(String paramName){
	 // ResourceBundle rb = ResourceBundle.getBundle("constants");
	  
//	  return rb.getString(paramName).trim();
	  return readParam(paramName);
  }
}