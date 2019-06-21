var authority_list = {
	
	/**
	 * 添加
	 */
	add : function() {
		//调用验证的方法
		common.preSubmitValidator('form2SystemPower','bootstrapValidator',function(){
			
			var validate = tool.ajaxForm('/user/authority/validateBeforeAdd','post','form2SystemPower',true,'json');
			
			if(null != validate && undefined != validate){
				//验证不通过则提示错误
	        	if(CONSTANTS.HRS_OK != validate.status){
	        		toastr.error(validate.msg);
	        		return;
	        	}
				
			}else{
				toastr.error(CONSTANTS.ERROR);
				return;
			}
			//成功的回调方法
			var result = tool.ajaxForm('/user/authority/saveUserAuthority','post','form2SystemPower',true,'json');
			if(null != result && undefined != result){
	        	
	        	//处理成功则刷新列表
	        	if(CONSTANTS.HRS_OK == result.status){
	        		toastr.success(result.msg);
	        		authority_ztree.initLoadZtree();
	        		//关闭弹出框
	        		$('#modal2SystemModel').modal("hide");
	        	}else{
	        		//系统返回错误
	        		toastr.error(result.msg);
	        	}
	        }else {
	        	//提示错误信息
	        	toastr.error(CONSTANTS.ERROR);
	        }
			
			//将添加框内的数据清空
			common.clearForm("form2SystemPower","input","");
		});
	},
	
	/**
	 * 更新
	 */
	update : function(){
		//调用验证的方法
		common.preSubmitValidator('updateform2SystemPower','bootstrapValidator',function(){
			
			//组装数据
			$("#updateform2SystemPower").append(
				'<input type="hidden" name="pageId" value="'+$("#txt_update_systemModuleUrl").val()+'"/>'
			);
			 
			//成功的回调方法
			var result = tool.ajaxForm('/user/authority/updateUserAuthority','post','updateform2SystemPower',true,'json');
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		authority_ztree.initLoadZtree();
            		//关闭弹出框
            		$('#updateModal2SystemModel').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			//将添加框内的数据清空
			common.clearForm("updateform2SystemPower","input","");
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
	
	//初始化树结构
	authority_ztree.initLoadZtree();
	common.pageInitAuthoritry();
});



