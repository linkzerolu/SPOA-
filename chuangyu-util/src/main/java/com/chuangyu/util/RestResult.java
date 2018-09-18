package com.chuangyu.util;

/**
 * 返回前端结果集
 *   
 * @author: rqwang     
 * @date: 2018年6月20日 上午11:54:37   
 *   
 * @param <T>
 */
public class RestResult<T> {

    public static RestResult success(){
        return new RestResult(RestCode.CD200[0],RestCode.CD200[1]);
    }
    public static RestResult error(String code, String desc){
        return new RestResult(code,desc);
    }
    public static <T> RestResult success(T data){
        return new RestResult(RestCode.CD200[0],RestCode.CD200[1],data);
    }
    public RestResult() {
    }

    public RestResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public RestResult(String code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public String code = RestCode.CD200[0];
    public String desc = RestCode.CD200[1];

    public T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", list=" + data +
                '}';
    }
}
