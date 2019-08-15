package com.cf.uploadingfiles.until;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ：fengchen
 * @date ：Created in 2019/8/15 9:33
 * @description：
 * @version:
 */
public class CommUtils {

    public static boolean isNull(Object obj) {
        boolean result = false;
        if (obj != null) {
            if (obj instanceof String) {
                if (((String)obj).trim().isEmpty()) {
                    result = true;
                }
            } else if (obj instanceof Collection) {
                if (((Collection)obj).isEmpty()) {
                    result = true;
                }
            } else if (obj instanceof Map) {
                if (((Map)obj).isEmpty()) {
                    result = true;
                }
            } else if (obj.getClass().isArray() && Array.getLength(obj) <= 0) {
                return true;
            }
        } else {
            result = true;
        }

        return result;
    }

    public static String convertDateToString(Date date, String pattern) {
        String dateString = null;
        if (pattern == null || pattern.trim().length() == 0) {
            pattern = "yyyy-MM-dd";
        }

        try {
            dateString = DateFormatUtils.format(date, pattern);
        } catch (Exception var4) {
            Logger.getLogger(CommUtils.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

        return dateString;
    }
}
