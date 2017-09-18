package com.pr_manager.common.utils;

import javax.print.attribute.standard.RequestingUserName;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by XiaoJiang01 on 2017/5/11.
 */
public class ParamUtil {

    public static String stringify(Map<String, String> obj) {
        if (obj == null) return null;
        Set<String> keys = obj.keySet();
        if (keys.size() == 0) return "{}";
        StringBuffer strParam = new StringBuffer("{");
        int i = 0;
        for (String key : keys) {
            String value = obj.get(key);
            //如果值为数组形式,则不加""
            if ('[' == value.charAt(0) && ']' == value.charAt(value.length() - 1)) {
                strParam.append("\"" + key + "\"" + ":" + value);
            } else {
                strParam.append("\"" + key + "\"" + ":" + "\"" + value + "\"");
            }
            if (i == keys.size() - 1) return strParam.append('}').toString();
            i++;
            strParam.append(',');
        }
        return null;
    }

    public static String arrayToString(Object[] arrayParam) {
        if (arrayParam == null) return "null";
        int iMax = arrayParam.length - 1;
        if (iMax == -1) return "[]";
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.valueOf(arrayParam[i]));
            if (i == iMax) return b.append(']').toString();
            b.append(",");
        }
    }

    public static Map<String, String> objToMap(Object model) {
        Map<String, String> map = new HashMap<String, String>();
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); // 获取属性的类型

                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model); // 调用getter方法获取属性值
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, String.class);
                        m.invoke(model, "");
                    }
                    map.put(name, value);
                }

                if (type.equals("int")) {
                    Method m = model.getClass().getMethod("get" + name);
                    int value = Integer.parseInt(String.valueOf(m.invoke(model)));
                    map.put(name, String.valueOf(value));
                }

                if (type.equals("class java.lang.Integer")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null) {
                        return null;
                    }
                    map.put(name, String.valueOf(value));
                }

                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Boolean.class);
                        m.invoke(model, false);
                    }
                    map.put(name, String.valueOf(value));
                }

                if (type.equals("class java.util.Date")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(model);
                    if (value == null) {
                        return null;
                    }
                    long time = value.getTime();
                    map.put(name, String.valueOf(time));
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;
    }
}
