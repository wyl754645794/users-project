package org.users.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ModelUtil
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 10:05 AM:35
 */
public class ModelUtil {

    public static Object createModelByParams(HashMap<String, String> params,String className){
        try {
            Class<?> aClass = Class.forName(className);
            Object target = aClass.newInstance();
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                Field field = aClass.getDeclaredField(next.getKey());
                field.setAccessible(true);
                field.set(target,next.getValue());
            }
            return target;
        } catch (Exception e) {
            System.err.println("field set value is fail");
        }
        return null;
    }
}
