var role_list = {
		
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
		role_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
	},
	
	/**
	 * 删除
	 */
	dels : function(){
		var arrSelections = role_list.listTableObj.bootstrapTable('getSelections');
		common.delByIds(arrSelections,"/user/role/deletes", "post","JSON",function(data){
			role_list.tableList();
		});
	},
	
	/**
	 * 显示添加弹出框
	 */
	addBefore : function(){
		
		//初始化树结构
		role_ztree.initLoadZtree("authorityTreeAddId");
		
		//将添加框内的数据清空
		common.clearForm("form2Rolelist","input","");
		
		//显示弹出框
		$('#addRoleFrameId').modal();
		
	},
	
	/**
	 * 添加
	 */
	add : function() {
		
		//调用验证的方法
		common.preSubmitValidator('form2Rolelist','bootstrapValidator',function(){
			//获取到ID，并填充
			role_list.getZTreeIdsToLabel("authorityTreeAddId","txt_authorityIds");
			
			//成功的回调方法
			var result = tool.ajaxForm('/user/role/save','post','form2Rolelist',true,'json');
            // var result = tool.ajax('/system/role/save', 'post', data, 'json', null, null, null,null);
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		role_list.tableList();
            		//关闭弹出框
            		$('#addRoleFrameId').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			
			//将添加框内的数据清空
			common.clearForm("form2Rolelist","input","");
            var treeObj = $.fn.zTree.getZTreeObj('authorityTreeAddId');
            treeObj.checkAllNodes(false);
            $("#txt_authorityLevel").empty();
        });
	},
	
	/**
	 * 获取到Ztree的Ids，并拼装放到对应的标签内
	 */
	getZTreeIdsToLabel : function (treeId,labelId){
		//ID数据获取并组装
        var ids = '';
        
        //获取到选中的节点数据
        var treeObj=$.fn.zTree.getZTreeObj(treeId);
        var nodes = treeObj.getCheckedNodes(true);
        for(var i = 0; i < nodes.length; i++){
        	//获取到当前节点
        	var node = nodes[i];
        	//获取到子节点，并判断是否有子节点已选中，有的话，则当前这个节点则不加入，只记录子节点的数据。
        	var childrenNodes = node.children;
        	if(undefined == childrenNodes || null == childrenNodes || 0 == childrenNodes.length){
                ids += node.id+",";
        	}
        }
        
        if(ids.length > 0){
            ids = ids.substring(0,ids.length-1);
        }
        
        $("#" + labelId).val(ids);
		
	},
	
	/**
	 * 更新之前
	 */
	updateBefore : function() {
		
		var arrSelections = role_list.listTableObj.bootstrapTable('getSelections');
		//只能选择一条
		if (arrSelections.length <= 0 || arrSelections.length > 1) {
            toastr.warning(CONSTANTS.ONLY_RECORD_MSG);
            return;
        }
		
		var data = arrSelections[0];
		
		//数据回显，如果出现<select>等标签是value和显示的值是不一样则进行手动返回
		common.paddingForm("updateform2Rolelist","input",data);
		
		//treeDemo界面中加载ztree的div
		var treeObj = $.fn.zTree.getZTreeObj("authorityTreeUpdateId");
		treeObj.cancelSelectedNode();//先取消所有的选中状态
		
		//已选中的Ids
		var idsStr = data.authorityIds;
        var idList = idsStr.split(",");
        for(var i = 0; i < idList.length; i++){
            var id = idList[i];
            var nodes = treeObj.getNodesByParam("id",id,null);
            var node = treeObj.getNodeByParam("id",id);
            //treeObj.cancelSelectedNode();//先取消所有的选中状态
            treeObj.selectNode(node,true);//将指定ID的节点选中
            treeObj.expandNode(node, true, false);//将指定ID节点展开
            treeObj.checkNode(nodes[0], true, true);
        }
        
		//显示弹出框
		$('#updateRoleFrameId').modal();
	},
	
	/**
	 * 更新
	 */
	update : function(){
		//调用验证的方法
		common.preSubmitValidator('updateform2Rolelist','bootstrapValidator',function(){
			//获取到ID，并填充
			role_list.getZTreeIdsToLabel("authorityTreeUpdateId","txt_authorityUpdateIds");
			
			//成功的回调方法
			var result = tool.ajaxForm('/user/role/update','post','updateform2Rolelist',true,'json');
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		role_list.tableList();
            		//关闭弹出框
            		$('#updateRoleFrameId').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			//将添加框内的数据清空
			common.clearForm("updateform2Rolelist","input","");
		});
	}
	
	
	
};

$(function () {
	common.pageInitAuthoritry();
	var queryParams = {
			filtrate : {//绝对值匹配
				is_delete : "N"
			},
			orderField : "update_time",
			orderingRule : "DESC",
		};
	
	//初始化列表数据
	role_list.listTableObj = common.bootstrapTable('tb2RoleList', '/user/role/getPagingList','get',queryParams);
	
	//初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});
    

	//初始化编辑框的树结构
	role_ztree.initLoadZtree("authorityTreeUpdateId");

});

