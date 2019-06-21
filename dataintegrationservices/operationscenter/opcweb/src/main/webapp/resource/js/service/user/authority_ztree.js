var authority_ztree = {
		
	/**
	 * 初始化加载
	 */
	initLoadZtree : function(){
		var setting = {
			edit: {
				enable:true,
				showRemoveBtn:true,
				showRenameBtn:true,
				removeTitle:"remove",
				renameTitle : "rename"
			},
	        view: {
	        	addHoverDom:authority_ztree.addHoverDom, //添加
	        	removeHoverDom:authority_ztree.removeHoverDom, //删除
	            selectedMulti: false
	        },
	        check: {
	            enable: false
	        },
	        data: {
	            simpleData: {
	                enable: true,
	                idKey: "id"
	            }
	        },
	        callback: {
	    		beforeRemove: authority_ztree.zTreeBeforeRemove,
	    		onRename: authority_ztree.zTreeOnRename,
	    		beforeEditName:authority_ztree.zTreebeforeEditName
	    	}

	    };
		
		var zNodes =[];
	    $.ajax({
	        url : "/user/authority/getAuthorityInfo",
	        type : "GET",
	        dataType:'json',
	        data:{},
	        success : function(result) {
//	            console.log(retMsg);
//	            zNodes = retMsg.result;
	        	
	        	//处理成功
            	if(CONSTANTS.HRS_OK == result.status){
            		zNodes = result.result;
    	            $.fn.zTree.init($("#treeUlId"), setting, zNodes);
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
	        	
	        }
	    });
	},
		
	/**
	 * 添加按钮
	 */
	addHoverDom:function(treeId,treeNode){
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span authority='userAuthoritySaveuserauthority_22_3' class='button add' id='addBtn_" + treeNode.tId
		+ "' title='添加子节点' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		
		//添加弹出框事件
		if (btn) btn.bind("click", function(){
			$("#modal2SystemModel_pid").val(treeNode.id);
			$("#modal2SystemModel").modal();
			return false;
		});
	},
	
	/**
	 * 页面删除节点
	 */
	removeHoverDom:function(treeId, treeNode) { 
		$("#addBtn_"+treeNode.tId).unbind().remove(); 
	},
	
	/**
	 * 删除之前的操作
	 */
	zTreeBeforeRemove:function(treeId, treeNode) {
		var tips="删除节点将同时删除其子节点，确认删除吗？";
		Ewin.confirm({message:tips}).on(function (e) {
			if (!e) {
                return false;
            }
			var flag=true;
			$.ajax({
				url : "/user/authority/removeAuthorityInfo",
				type : "GET",
				dataType:'json',
				async:false, 
				data:{"id":treeNode.id},
				success : function(retMsg) {
					if(retMsg.result=="fail"){
						console.log(retMsg);
						flag= false;
					}
				}
			});
			return flag;
		});
	},
	
	/**
	 * 节点重命名
	 */
	zTreeOnRename:function(event, treeId, treeNode, isCancel) {
//		console.log(treeNode.id+","+treeNode.name+","+treeNode.uri);
		$("#update_systemModule_id").val(treeNode.id);
		$("#txt_systemModule").val(treeNode.name);
		$("#txt_update_systemModuleUrl").val(treeNode.uri);
		$("#updateModal2SystemModel").modal();
	},
	
	/**
	 * 添加主节点
	 */
	addRootNode:function(){
		$("#modal2SystemModel_pid").val(0);
		$("#modal2SystemModel").modal();
	},
	
	/**
	 * 节点编辑弹出框
	 */
	zTreebeforeEditName:function(treeId,treeNode){
//		console.log(treeNode.id+","+treeNode.name+","+treeNode.uri);
		$("#update_systemModule_id").val(treeNode.id);
		$("#txt_systemModule").val(treeNode.name);
		$("#txt_update_systemModuleUrl").val(treeNode.uri);
		$("#updateModal2SystemModel").modal();
		return false;
	}	
		
		
}

$(function(){
	
	
	
});





