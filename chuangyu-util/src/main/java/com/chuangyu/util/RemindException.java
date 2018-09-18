package com.chuangyu.util;

/**
 * 提醒异常
 *   
 * @author: rqwang     
 * @date: 2018年6月20日 上午11:53:18   
 *
 */
public class RemindException extends RuntimeException {

    public String desc = RestCode.KINDLY_REMINDER[1];
    public String code = RestCode.KINDLY_REMINDER[0];

    public RemindException(String desc) {
        super(desc);
        this.desc = desc;
    }

    public RemindException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }

}
