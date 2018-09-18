package com.chuangyu.util;

/**
 * 检查异常
 *   
 * @author: rqwang     
 * @date: 2018年6月20日 上午10:54:39   
 *
 */
public class CheckException extends RuntimeException {

    public String desc = RestCode.PARAMETER_ERROR[1];
    public String code = RestCode.PARAMETER_ERROR[0];

    public CheckException(String desc) {
        super(desc);
        this.desc = desc;
    }

}