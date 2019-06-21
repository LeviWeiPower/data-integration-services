var CONSTANTS = {
		
	//主体存放数据的Id
	CONTENT_ID : "mainContent",
		
	//响应数据的状态码
	/** 成功 **/
	HRS_SUCCESS : 200,
	
	/** 成功 **/
	HRS_OK : 2000,
	
	/** 认证失败 **/
	HRS_UNAUT : 3001,
	
	/** 参数错误 **/
    HRS_PARAM_ERROR : 6001,

    /** 参数错误 **/
    HRS_UNIQUE_ERROR : 6002,
	
	/** 业务逻辑错误 **/
	HRS_BUSINESS_ERROR : 6000,
		
	//根据状态码对应的提醒信息--------------------------------------------------------
	//默认认证失败提醒
	HRS_UNAUT : "账号认证失败,请重新登录!",
	//默认参数错误提醒
	PARAM_ERROR : "参数错误,请检查参数!",
    //默认参数错误提醒
    UNIQUE_ERROR : "数据唯一性错误,请检查参数!",
	//默认业务错误提醒
 	BUSINESS_ERROR: "业务错误,请联系管理员!",
 	//默认系统内部错误 提醒
 	HRS_SYS_ERROR : "系统内部错误,请联系管理员!",
 	//默认返回失败提醒
    ERROR : "对不起,您的服务出现问题,请联系管理员!",

    //只选择一条记录
 	ONLY_RECORD_MSG:"请选择一条信息操作!",

    //选择多条信息操作
    MORE_RECORD_MSG:"请至少选择一条信息操作!",
    
    //删除确认提示
    COMFIRM_DEL_MSG:"确定要删除吗?",

    //表单验证错误
    ERROR_VALIDATION : "对不起,你填写的表单数据有问题,请检查并重新填写!",

		
}