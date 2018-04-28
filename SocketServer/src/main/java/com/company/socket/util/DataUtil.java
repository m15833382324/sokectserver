package com.company.socket.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	/**  
	    * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序
	    */
	public static int bytesToInt(byte[] src, int offset) {  
	    int value;    
	    value = (int) ((src[offset] & 0xFF)   
	            | ((src[offset+1] & 0xFF)<<8)   
	            | ((src[offset+2] & 0xFF)<<16)   
	            | ((src[offset+3] & 0xFF)<<24));  
	    return value;  
	}
	
	/**  
	    * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用 
	    */    
	public static byte[] intToBytesHL(int value)   
	{   
	    byte[] src = new byte[4];  
	    src[0] = (byte) ((value>>24) & 0xFF);  
	    src[1] = (byte) ((value>>16)& 0xFF);  
	    src[2] = (byte) ((value>>8)&0xFF);    
	    src[3] = (byte) (value & 0xFF);       
	    return src;  
	} 
	
	
	public static Integer fomratWindLevel(double b){
		if(b>=0&b<=0.2){
			return 0;
		}else if(b>0.2&b<=1.5){
			return 1;
		}else if(b>1.5&b<=3.3){
			return 2;
		}else if(b>3.3&b<=5.4){
			return 3;
		}else if(b>5.5&b<=7.9){
			return 4;
		}else if(b>7.9&b<=10.7){
			return 5;
		}else if(b>10.7&b<=13.8){
			return 6;
		}else if(b>13.8&b<=17.1){
			return 7;
		}else if(b>17.1&b<=20.7){
			return 8;
		}else if(b>20.7&b<=24.4){
			return 9;
		}else if(b>24.4&b<=28.4){
			return 10;
		}else if(b>28.4&b<=32.6){
			return 11;
		}else if(b>32.6&b<=36.9){
			return 12;
		}else if(b>36.9&b<=41.4){
			return 13;
		}else if(b>41.4&b<=46.1){
			return 14;
		}else if(b>46.1&b<=50.9){
			return 15;
		}else if(b>50.9&b<=56.0){
			return 16;
		}else if(b>56.0&b<=61.2){
			return 17;
		}else{
			return null;
		}
	}
	public static String formatTimetoMS(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date= new Date();
		String str = sdf.format(date);
		return str;
	}
	public static String formatTimetoSecond(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		Date date= new Date();
		String str = sdf.format(date);
		return str;
	}
}
