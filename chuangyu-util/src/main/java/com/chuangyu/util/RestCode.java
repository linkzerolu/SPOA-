package com.chuangyu.util;

/**
 * 请求状态类：该类设定后传至前台从而响应给客户端
 *   
 * @author: rqwang     
 * @date: 2018年6月20日 上午11:53:43   
 *
 */
public interface RestCode {

	/**
	 * http常用状态码
	 */
    String[] CD200 = {"200", "操作成功"};
    String[] CD400 = {"400", "操作失败"};
    String[] CD404 = {"404","资源，服务未找到"};
    String[] CD500 = {"500","系统内部错误"};
    String[] CD514 = {"514","重新登录"};

    
    /**
     * 自定义状态编码
     */
    String[] REQUEST_METHOD_TYPE_DOES_NOT_SUPPORT = {"Request method type does not support","请求方法类型不支持"};
    String[] PARAMETER_ERROR = {"Parameter error", "参数错误:"};
    String[] KINDLY_REMINDER = {"kindly reminder", "温馨提示:"};
    String[] APARTMENT_CAN_NOT_BE_EMPTY = {"The name of the apartment can not be empty", "公寓名称不能为空"};
    String[] BUILDING_CODE_CAN_NOT_BE_EMPTY = {"Building code can not be empty", "楼宇编码不能为空"};
    String[] ROOM_TEMPLATE_CODING_CANNOT_BE_EMPTY = {"Room template coding cannot be empty", "房间模板编码不能为空"};
    String[] LEASE_CODE_CANNOT_BE_EMPTY = {"lease code cannot be empty", "租约编码不能为空"};
    String[] QUERY_ERROR = {"Query error", "查询出错"};
    String[] QUERY_DATA_IS_EMPTY = {"Query data is empty", "查询数据为空"};
    String[] ID_CAN_NOT_BE_EMPTY = {"ID can't be empty", "id不能为空"};
    String[] ENTRY_ERROR = {"Entry error", "录入出错"};
    String[] DELETE_ERROR = {"Delete error", "删除出错"};
    String[] ROOM_HAVE_TEMPLATE = {"Delete error", "房间有模板，不可删除"};
    String[] HAVE_BEEN_DELETED = {"Delete error", "数据不存在或者已经被删除"};
    String[] DATE_FORMAT_ERROR = {"date format error", "日期格式有误"};
    String[] LENGTH_TOO_LONG = {"length too long", "长度过长"};
    String[] QUANTITATIVE_LIMITATION = {"quantitative limitation", "数量限制"};
    String[] ERROR_GENERATING_FILE = {"Error generating file", "生成Excel文件出错"};
    String[] ERROR_SEND_MESSAGE = {"Error send message", "短信发送失败"};
    String[] ERROR_UPLOAD_MESSAGE = {"Error upload message", "图片上传失败"};



    
    

}
