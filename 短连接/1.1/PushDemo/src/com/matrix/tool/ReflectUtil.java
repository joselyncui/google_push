package com.matrix.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {
	public static Field[] getFields(String className){
		Class<?> demo = null;
        try {
            demo = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Field> tmp = new ArrayList<Field>();
        Field[] fields = demo.getDeclaredFields();
        int i = 0;
        for(Field field : fields){
        	if(!Modifier.isStatic(field.getModifiers())){
        		tmp.add(field);
        	}
        }
        Field[] result = new Field[tmp.size()];
        for(Field field : tmp){
        	result[i] = field;
        	i++;
        }
        return result;
	}
	
	public static String convertFieldName(String fieldName){
		String start = fieldName.substring(0,1);
		String end = fieldName.substring(1);
		return start.toUpperCase() + end;
	}
	
	public static List<String> getFieldNames(String className){
		List<String> names = new ArrayList<>();
		Field[] fields = getFields(className);
		for(int i=0; i<fields.length; i++){
			names.add(convertFieldName(fields[i].getName()));
		}
		return names;
	}
	
	public static Object getClassInstance(String className){
		Class<?> demo = null;
        Object obj=null;
        try {
            demo = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
        	obj=demo.newInstance();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
	}
	
	public static String getClassName(String className){
		return className.substring(6);
	}
	
	public static Object getter(Object obj, String att) {
		Object returnObj = null;
        try {
            Method method = obj.getClass().getMethod("get" + att);
            returnObj = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnObj;
    }
	
    public static void setter(Object obj, String att, Object value, Class<?> type) {
        try {
        	Method method = obj.getClass().getMethod("set" + att, type);
            method.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Object> getListByObject(Object obj){
		List<Object> params = new ArrayList<>();
		String className =obj.getClass().toString().substring(6);
		List<String> names = ReflectUtil.getFieldNames(className);
		for(int i=1; i<names.size(); i++){
			params.add(ReflectUtil.getter(obj, names.get(i)));
		}
		if(!ReflectUtil.getter(obj, names.get(0)).toString().equals("0")){
			params.add(ReflectUtil.getter(obj, names.get(0)));
		}
		return params;
	}
    
    public static List<Object> getListByObjects(Object... values){
		List<Object> params = new ArrayList<Object>();
		for(int i=0; i<values.length; i++){
			params.add(values[i]);
		}
		return params;
	}
}
