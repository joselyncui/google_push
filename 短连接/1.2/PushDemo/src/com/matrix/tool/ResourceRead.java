package com.matrix.tool;

import java.util.ResourceBundle;

public class ResourceRead {
	public static String getResource(String fileName, String key) {
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		return rb.getString(key);
	}
}
