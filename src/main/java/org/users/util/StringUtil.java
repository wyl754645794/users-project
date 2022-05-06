package org.users.util;

import java.util.HashMap;
import java.util.Random;

/**
 * StringUtil
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 10:15 PM:24
 */
public class StringUtil {

    private static final Random random = new Random();

    public static boolean isEmpty(String target){
        if (target == null){
            return true;
        }
        if ("".equals(target)){
            return true;
        }
        return target.length() == 0;
    }

    public static boolean isNotEmpty(String target){
        return !isEmpty(target);
    }

    public static HashMap<String, String> paramsAnalysis(String paramString){
        if (isEmpty(paramString)){
            return null;
        }
        String[] split = paramString.split(",");
        HashMap<String, String> params = new HashMap<>(split.length);
        for (String param : split) {
            String[] split1 = param.split(":");
            params.put(split1[0].trim(),split1[1].trim());
        }
        return params;
    }

    public static String getRandomString(int length){
        String str = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
