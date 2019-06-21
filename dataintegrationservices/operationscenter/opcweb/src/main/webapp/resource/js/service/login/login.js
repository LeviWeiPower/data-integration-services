var login = {
		
	/**
	 * 登录方法
	 */
	login : function(){
		
		//调用验证的方法
		common.preSubmitValidator('loginFormId','bootstrapValidator',function(){
			
			//成功的回调方法
			var result = tool.ajaxForm('/main/login','post','loginFormId',true,'json');
			if(null != result && undefined != result){
            	//处理成功则进入主页
            	if(CONSTANTS.HRS_OK == result.status){
            		window.location.href = WEB_URL + "/main";

        			//将添加框内的数据清空
        			common.clearForm("loginFormId","input","");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
		});
		
		
	},
		
		
}

$(function(){
	
	//初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});
	
});

