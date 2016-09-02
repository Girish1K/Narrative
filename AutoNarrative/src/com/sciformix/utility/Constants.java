package com.sciformix.utility;

import java.io.FileInputStream;
import java.util.Properties;

public class Constants {
	static String property;

	public Constants(String property) {
		// TODO Auto-generated constructor stub
		Constants.property=property;
	}
	
	
	public  static String getProperty(String propName) {
		String propValue = null;
		try {

			Properties prop = new Properties();
			String osName = System.getProperty("os.name");
			if (osName.toLowerCase().contains(("windows"))) {
				prop.load(new FileInputStream("C:\\Conf\\"+property));
			} else {
				prop.load(new FileInputStream("/var/conf/"+property));
			}
			propValue = prop.getProperty(propName);

		} catch (Exception e) {
			 e.printStackTrace();
			//System.out.println("Util.getProperty()" + e);
		}

		return propValue;

	}
	
}