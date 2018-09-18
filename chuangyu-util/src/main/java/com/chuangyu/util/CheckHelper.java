package com.chuangyu.util;

import com.google.common.base.Strings;
import org.slf4j.Logger;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @ClassName: CheckHelper 
* @Description: 数据校验工具
* @author rqwang 
* @date 2018年5月30日
* @version 0.0.1
 */
public class CheckHelper {
    public static void checkNotNull(Object fieldValue, String desc) {
        if (null == fieldValue) {
            throw new CheckException(desc);
        }
    }

    public static <T> void checkArrayNotNull(T[] t , String desc) {
        if (null == t || t.length==0) {
            throw new CheckException(desc);
        }
    }

    public static void checkNotBlank(String field, String desc){
        if (Strings.isNullOrEmpty(field)){
            throw new CheckException(desc);
        }
    }

    public static void checkNotLessZero(Object fieldValue, String desc) {
    	if (null == fieldValue) {
    		throw new CheckException(desc);
		}else if (null != fieldValue && (long)fieldValue < 0) {
			throw new CheckException(desc);
		}
    	
    }

    public static void remindNotNul(Object fieldValue, String desc) {
        if (null == fieldValue) {
            throw new RemindException(desc);
        }
    }

    public static void remindShouldBeNul(Object fieldValue, String desc) {
        if (fieldValue != null) {
            throw new RemindException(desc);
        }
    }

    public static void remindNotBlank(String field, String desc) {
        if (Strings.isNullOrEmpty(field)) {
            throw new RemindException(desc);
        }
    }

    public static void remindNotNul(Object fieldValue, String code, String desc) {
        if (null == fieldValue) {
            throw new RemindException(code, desc);
        }
    }

    public static void remindException(String code,String desc) {
        throw new RemindException(code,desc);
    }

    public static void checkRegex(String str, String regex, String desc) {
        if(Strings.isNullOrEmpty(str)){
            throw new CheckException(desc);
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.find()){
            throw new CheckException(desc);
        }
    }

    public static <T> void checkPropertyIsNullOrEmpty(T t,String desc, String ...args) {
        Map<String, Object> map = null;
        if(t instanceof Map){
            map = (Map)t;
        }else {
            map = TypeConvertUtils.beanToMap(t);
        }
        for (String arg : args) {
            if(map.get(arg)== null || Strings.isNullOrEmpty(map.get(arg).toString())){
                throw new CheckException(desc+arg);
            }
        }
    }

    public static void checkSQLUpdateCount(Number actual, Number expect, Logger log) {
        if(actual.intValue() != expect.intValue()){
            if(log.isInfoEnabled()){
                log.info("actually update {} ,expected update {}",actual.intValue(),expect.intValue());
            }
            throw new RemindException(RestCode.CD500[0],RestCode.CD500[1]);
        }
    }
}
