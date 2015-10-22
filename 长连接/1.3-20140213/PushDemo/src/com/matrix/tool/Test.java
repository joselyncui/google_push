package com.matrix.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String args[]) {
		Map<String, String> map =new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		map.put("6", "6");
		List<String> list = new ArrayList<>();
		for (String key : map.keySet()) {
			System.out.println(key +"-->"+map.get(key));
			if ("1".equals(key)) {
				list.add(key);
			}
		}
		map.remove(list.get(0));
		System.out.println("#########################");
		for (String key : map.keySet()) {
			System.out.println(key +"-->"+map.get(key));
			if ("1".equals(key)) {
				list.add(key);
			}
		}
	}
}
