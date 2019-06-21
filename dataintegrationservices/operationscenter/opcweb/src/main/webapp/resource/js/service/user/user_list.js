var user_list = {
		
	/**
	 * 列表对象，用于后续操作
	 */
	listTableObj : null,
	
	/**
	 * 列表重置（可根据事件触发进行）
	 */
	tableList : function(){
		//搜索框的值
		var nameVal = $("#inputSearchId").val();
		//搜索的参数
		var queryParams = {
				filtrate : {//绝对值匹配
					is_delete : "N"
				},
				likeFiltrate : {//模糊搜索
					nickname : nameVal,
					account : nameVal
				},
				orderField : "update_time",
				orderingRule : "DESC",
			};
		
		//列表重新搜索，按照新的参数进行搜索
		user_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
		
	},

    /**
	 * 账号
	 */
    renderAccount : function(value, row, index){
		return row.account;
		
	},
	
    /**
	 * 昵称
	 */
	renderNickname : function(value, row, index){
		return row.nickname;
	},
	
	/**
	 * 状态
	 */
	renderStatus : function(value, row, index){
		
		var html = "";
        
        if (row.status == 0) {
        	html += "<font color='#00CC33'>正常</font>";
			return html;
		}
        html += "<font color='red'>禁用</font>";
		return html;
		
	},
	
	/**
	 * 角色名
	 */
	renderRoleName: function(value, row, index){
		var roleId = row.roleId;
		//查询角色
		var result = tool.ajax('/user/role/getData','get',{"id":roleId},'json');
		if(null != result && undefined != result){
        	//处理成功则刷新列表
        	if(CONSTANTS.HRS_OK == result.status){
        		if(null != result.result){

            		return result.result.name;
        		}
        	}else{
        		//系统返回错误
        		toastr.error(result.msg);
        	}
        }else {
        	//提示错误信息
        	toastr.error(CONSTANTS.ERROR);
        }
	},
	
	/**
	 * 状态操作
	 */
	renderOperate: function(value, row, index){
		var html = "";
        if (row.status != 0) {
            html = '<button type="button"  class=" btn btn-default  btn-sm" style="margin-right:10px;"   onclick="user_list.updateStatus(' + row.id + ",0" + ')" value="' + row.id + '">正常</button>'
        }
        if (row.status == 0) {
            html = '<button type="button"  class=" btn btn-default  btn-sm" style="margin-right:10px;"   onclick="user_list.updateStatus(' + row.id + ",1" + ')" value="' + row.id + '">冻结</button>'
        }
        return html;
    }, 

    /**
     * 更新状态
     */
    updateStatus:function(id,status){

        var udapteParam = {
            id : id,
            status : status
        };
        var result =  tool.ajax('/user/update','post', udapteParam, "json", null, null, null,null);
        if(null != result && undefined != result){

            if(CONSTANTS.HRS_OK == result.status){
                toastr.success(result.msg);
                user_list.tableList();
            }else{
                //系统返回错误
                toastr.error(result.msg);
            }
        }else {
            toastr.error(CONSTANTS.ERROR);
        }
    },
    
    /**
     * 删除
     */
	dels : function(){
		var arrSelections = user_list.listTableObj.bootstrapTable('getSelections');
		common.delByIds(arrSelections,"/user/deletes", "post","JSON",function(data){
			user_list.tableList();
		});
	},
	
	/**
	 * 显示添加弹出框
	 */
	addBefore : function(){
		
//		$("#btn_submit1").show();
//		$("#btn_submit2").hide();
//		$("#txt_account").prop("readonly",false);
		
		//权限
		var result = tool.ajax("/user/role/getDatas","get");
		//处理成功则刷新列表
		if(CONSTANTS.HRS_OK == result.status){
			$("#education").html("");
			for (var i = 0; i < result.result.length; i++) {
				$("#education").append("<option value=\""+result.result[i].id+"\">"+result.result[i].name+"</option>");
			}
		}

		//将添加框内的数据清空
		common.clearForm("form2SystemUser","input","");
		
		//显示弹出框
		$('#modal2SystemUser').modal();
		
	},
	
	/**
	 * 添加
	 */
	add : function() {
		$("#idInputId").val("");
		//调用验证的方法
		common.preSubmitValidator('form2SystemUser','bootstrapValidator',function(){
			
			//成功的回调方法
			var result = tool.ajaxForm('/user/save','post','form2SystemUser',true,'json');
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		user_list.tableList();
            		//关闭弹出框
            		$('#modal2SystemUser').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
    			//将添加框内的数据清空
    			common.clearForm("form2SystemUser","input","");
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			
		});
	},
	
	/**
	 * 更新之前操作
	 */
    updateBefore : function() {
    	var arrSelections = user_list.listTableObj.bootstrapTable('getSelections');
		//只能选择一条
		if (arrSelections.length <= 0 || arrSelections.length > 1) {
            toastr.warning(CONSTANTS.ONLY_RECORD_MSG);
            return;
        }
		 
		var data = arrSelections[0];
		
		//数据回显，如果出现<select>等标签是value和显示的值是不一样则进行手动返回
		common.paddingForm("updateform2Userlist","input",data);
		
		$("#txt_confirmPassword2").val(data.password);
		
		//权限
		var result = tool.ajax("/user/role/getDatas","get");
		//处理成功则刷新列表
		if(CONSTANTS.HRS_OK == result.status){
			$("#education2").html("");
			for (var i = 0; i < result.result.length; i++) {
				$("#education2").append("<option value=\""+result.result[i].id+"\">"+result.result[i].name+"</option>");
			}
			$("#education2").val(data.roleId);;
		}
		
		//用户名隐藏
		$("#txt_account2").prop("readonly",true);
		
		//显示弹出框
		$('#updateUserFrameId').modal();

    },
    
    /**
     * 更新
     */
    update : function(){
        //调用验证的方法
        common.preSubmitValidator('updateform2Userlist','bootstrapValidator',function(){
            //成功的回调方法
            var result = tool.ajaxForm('/user/updateUser','post','updateform2Userlist',true,'json');
            if(null != result && undefined != result){

                //处理成功则刷新列表
                if(CONSTANTS.HRS_OK == result.status){
                    toastr.success(result.msg);
                    user_list.tableList();
                    //关闭弹出框
                    $('#updateUserFrameId').modal("hide");
                }else{
                    //系统返回错误
                    toastr.error(result.msg);
                }
                //将添加框内的数据清空
                common.clearForm("updateform2Userlist","input","");
            }else {
                //提示错误信息
                toastr.error(CONSTANTS.ERROR);
            }

        });
    }
}

$(function() {

	var queryParams = {
			filtrate : {//绝对值匹配
				is_delete : "N"
			},
			orderField : "update_time",
			orderingRule : "DESC",
		};
	
	//初始化列表数据
	user_list.listTableObj = common.bootstrapTable('tb2systemUser', '/user/getPagingList','get',queryParams);
	
	//初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});
	
	
	common.pageInitAuthoritry();
});
