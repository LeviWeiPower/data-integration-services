var member_list = {
		
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
					name : nameVal
				},
				orderField : "update_time",
				orderingRule : "DESC",
			};
		
		//列表重新搜索，按照新的参数进行搜索
		member_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
		
	},

	/**
	 * 显示地址
	 */
	renderAddress : function(value, row, index){
		return row.province + row.city;
		
	},

	/**
	 * 加入状态
	 */
	renderStatus : function(value, row, index){
		//0-已加入、1-确认加入、2已离开
		var joinStatus = row.joinStatus;
		var html = "<ul>";
		if (0 == joinStatus){
			var date = row.joinDate;//加入日期
			html += "<li style='color: #00CC33;'>已加入</li>";
			html += "<li>" + tool.getDateStrFormat(date) + "</li>";
			
		}else if(1 == joinStatus){
			var date = row.sureDate;//确认日期
			html += "<li style='color: red;'>确认加入</li>";
			html += "<li>" + tool.getDateStrFormat(date) + "</li>";
			
		}else {
			var date = row.leaveDate;//已离开日期
			html += "<li style='color: #6b6b6b;'>已离开</li>";
			html += "<li>" + tool.getDateStrFormat(date) + "</li>";
			
		}
		html += "</ul>";
		return html;
		
	},
	
	/**
	 * 删除
	 */
	dels : function(){
		var arrSelections = member_list.listTableObj.bootstrapTable('getSelections');
		common.delByIds(arrSelections,"/team/member/deletes", "post","JSON",function(data){
			member_list.tableList();
		});
	},
	
	/**
	 * 显示添加弹出框
	 */
	addBefore : function(){
		
		$("#areaDivId").citySelect({
			nodata:"none",
			required:true,
			prov:"北京",
			city:"东城区"	
		}); 
		
		//用户
		var result = tool.ajax("/user/getDatas","get");
		//处理成功则刷新列表
		if(CONSTANTS.HRS_OK == result.status){
			$("#accountSelectId").html("");
			for (var i = 0; i < result.result.length; i++) {
				$("#accountSelectId").append("<option value=\""+result.result[i].account+"\">"+result.result[i].nickname+"</option>");
			}
		}
		
		//日期
        $('#statusByTimeId').datetimepicker({
            format: 'yyyy/mm/dd',
            minView: "month",// 设置仅选择到日
            language: "zh-CN",
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true,
            startDate: new Date(),
            clearBtn: true,//清除按钮
        });
		
		//将添加框内的数据清空
		common.clearForm("form2Member","input","");
		common.clearForm("form2Member","textarea","");
		
		//显示弹出框
		$('#modal2Member').modal();
		
	},
	
	/**
	 * 添加
	 */
	add : function() {
		//调用验证的方法
		common.preSubmitValidator('form2Member','bootstrapValidator',function(){
			
			//成功的回调方法
			var result = tool.ajaxForm('/team/member/saveMember','post','form2Member',true,'json');
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		member_list.tableList();
            		//关闭弹出框
            		$('#modal2Member').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			
			//将添加框内的数据清空
			common.clearForm("form2Member","input","");
		});
	},

	/**
	 * 更新之前
	 */
    updateBefore : function() {
    	var arrSelections = member_list.listTableObj.bootstrapTable('getSelections');
		//只能选择一条
		if (arrSelections.length <= 0 || arrSelections.length > 1) {
            toastr.warning(CONSTANTS.ONLY_RECORD_MSG);
            return;
        }

		var data = arrSelections[0];

		//地区
		$("#updateAreaDivId").citySelect({
			nodata:"none",
			required:true,
			prov:data.province,
			city:data.city	
		});
		
		//日期
        $('#updateStatusByTimeId').datetimepicker({
            format: 'yyyy/mm/dd',
            minView: "month",// 设置仅选择到日
            language: "zh-CN",
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true,
//            startDate: new Date(),
            clearBtn: true,//清除按钮
        });
		
        //用户
		var result = tool.ajax("/user/getDatas","get");
		//处理成功则刷新列表
		if(CONSTANTS.HRS_OK == result.status){
			$("#updateAccountSelectId").html("");
			for (var i = 0; i < result.result.length; i++) {
				$("#updateAccountSelectId").append("<option value=\""+result.result[i].account+"\">"+result.result[i].nickname+"</option>");
			}
		}
        
		//数据回显，如果出现<select>等标签是value和显示的值是不一样则进行手动返回
		common.paddingForm("updateForm2Member","input",data,"updateStatusByTimeId");
		common.paddingForm("updateForm2Member","select",data);
		common.paddingForm("updateForm2Member","textarea",data);
		
		//重置加入状态节点
		$("#updateJoinStatusId").html("");
		$("#updateJoinStatusId").html('<option value="0">已加入</option>'
										+ '<option value="1">确认加入</option>'
										+ '<option value="2">已离开</option>');
		
		//加入状态
		if(0 == data.joinStatus){//已加入
			console.log(tool.getDateStrFormat(data.joinDate));
			//日期
	        $('#updateStatusByTimeId').val(tool.getDateStrFormat(data.joinDate));
	        $("#updateStatusByTimeId").attr("name","joinDate");
			$("#updateTextNameOfStatus").text("加入日期");
			
		}else if(1 == data.joinStatus){//确认加入
			//删除节点
			$("#updateJoinStatusId option[value='0']").remove();
			
			//日期
	        $('#updateStatusByTimeId').val(tool.getDateStrFormat(data.sureDate));
	        $("#updateStatusByTimeId").attr("name","sureDate");
			$("#updateTextNameOfStatus").text("确认日期");
			
		}else {//已离开
			//删除节点
			$("#updateJoinStatusId option[value='0']").remove();
			$("#updateJoinStatusId option[value='1']").remove();
			
			//日期
	        $('#updateStatusByTimeId').val(tool.getDateStrFormat(data.leaveDate));
	        $("#updateStatusByTimeId").attr("name","leaveDate");
			$("#updateTextNameOfStatus").text("离开日期");
		}
		
		//显示弹出框
		$('#updateModal2Member').modal();

    },
    
	/**
	 * 更新
	 */
    update : function(){
    	//调用验证的方法
		common.preSubmitValidator('updateForm2Member','bootstrapValidator',function(){
			
			//成功的回调方法
			var result = tool.ajaxForm('/team/member/update','post','updateForm2Member',true,'json');
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		member_list.tableList();
            		//关闭弹出框
            		$('#updateModal2Member').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			//将添加框内的数据清空
			$("#updateJoinStatusId").val("0");
			common.clearForm("updateForm2Member","input","");
			common.clearForm("updateForm2Member","textarea","");
		});
    },
    
    /**
     * 根据选择的，改变时间
     */
    changeDateOfJoinStatus : function (){
    	var selectVal = $("#updateJoinStatusId").val();
    	//加入状态
		if(0 == selectVal){//已加入
			//日期
//	        $('#updateStatusByTimeId').val(tool.getDateStrFormat(data.joinDate));
	        $("#updateStatusByTimeId").attr("name","joinDate");
			$("#updateTextNameOfStatus").text("加入日期");
			
		}else if(1 == selectVal){//确认加入
			//日期
//	        $('#updateStatusByTimeId').val(tool.getDateStrFormat(data.sureDate));
	        $("#updateStatusByTimeId").attr("name","sureDate");
			$("#updateTextNameOfStatus").text("确认日期");
			
		}else {//已离开
			//日期
//	        $('#updateStatusByTimeId').val(tool.getDateStrFormat(new Date()));
	        $("#updateStatusByTimeId").attr("name","leaveDate");
			$("#updateTextNameOfStatus").text("离开日期");
		}
    },
    
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
	member_list.listTableObj = common.bootstrapTable('memberListTableId', '/team/member/getPagingList','get',queryParams);
	
	//初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});

	
	common.pageInitAuthoritry();
});
