var feedback_list = {
		
	//列表对象，用于后续操作
	listTableObj : null,
	
	//列表重置（可根据事件触发进行）
	tableList : function(){
		//搜索框的值
		var nameVal = $("#inputSearchId").val();
		//搜索的参数
		var queryParams = {
				filtrate : {//绝对值匹配
					is_delete : "N"
				},
				likeFiltrate : {//模糊搜索
					theme : nameVal
				},
				orderField : "update_time",
				orderingRule : "DESC",
			};
		
		//列表重新搜索，按照新的参数进行搜索
		feedback_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
		
	},
	renderFeedbackTime : function(value, row, index){
		var html;
		var str = row.feedbackTime;
        if ( str == null || str == "" ){
        	html = "";
        }else {
        	html = new Date(str).toLocaleString();
        }
		return html;
		
	},
	renderType : function(value, row, index){
		var html;
		var type = row.type;
        if ( type == "1" ){
        	html = "bug";
        }else if ( type == "2"){
        	html = "优化意见";
        }
		return html;
		
	},
	renderResponseTime : function(value, row, index){
		var html;
		var str = row.responseTime;
        if ( str == null || str == "" ){
        	html = "";
        }else {
        	html = new Date(str).toLocaleString();
        }
		return html;
		
	},
	//删除
	dels : function(){
		var arrSelections = feedback_list.listTableObj.bootstrapTable('getSelections');
		common.delByIds(arrSelections,"/website/feedback/deletes", "post","JSON",function(data){
			feedback_list.tableList();
		});
	},
		
    updateStatus:function(id,index,status){
        var udapteParam = {
            id : id,
            status : 1-status
        };
        var result =  tool.ajax('/website/feedback/update','post', udapteParam, "json", null, null, null,null);
        if(null != result && undefined != result){

            if(CONSTANTS.HRS_OK == result.status){
                toastr.success(result.msg);
                feedback_list.tableList();
            }else{
                //系统返回错误
                toastr.error(result.msg);
            }
        }else {
            toastr.error(CONSTANTS.ERROR);
        }
    },
    updateBefore : function() {
    	var arrSelections = feedback_list.listTableObj.bootstrapTable('getSelections');
		//只能选择一条
		if (arrSelections.length <= 0 || arrSelections.length > 1) {
            toastr.warning(CONSTANTS.ONLY_RECORD_MSG);
            return;
        }
		 
		var data = arrSelections[0];
		
		//数据回显，如果出现<select>等标签是value和显示的值是不一样则进行手动返回
		common.paddingForm("updateform2feedbacklist","input",data);
		
		 
		
		//显示弹出框
		$('#updatefeedbackFrameId').modal();

    },//更新
    update : function(){
        //调用验证的方法
        common.preSubmitValidator('updateform2feedbacklist','bootstrapValidator',function(){
            //成功的回调方法
            var result = tool.ajaxForm('/website/feedback/update','post','updateform2feedbacklist',true,'json');
            if(null != result && undefined != result){

                //处理成功则刷新列表
                if(CONSTANTS.HRS_OK == result.status){
                    toastr.success(result.msg);
                    feedback_list.tableList();
                    //关闭弹出框
                    $('#updatefeedbackFrameId').modal("hide");
                }else{
                    //系统返回错误
                    toastr.error(result.msg);
                }
            }else {
                //提示错误信息
                toastr.error(CONSTANTS.ERROR);
            }

            //将添加框内的数据清空
            common.clearForm("updateform2feedbacklist","input","");
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
	feedback_list.listTableObj = common.bootstrapTable('websitefeedback', '/website/feedback/getPagingList','get',queryParams);
	
	//初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});
	
	common.pageInitAuthoritry();
	
});
