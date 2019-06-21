var role_ztree = {
		
	/**
	 * 初始化加载
	 */
	initLoadZtree : function(treeNodeId){
		
		var setting = {
	        view: {

	            selectedMulti: false
	        },
	        check: {
	            enable: true
	        },
	        data: {
	            simpleData: {
	                enable: true
	            }
	        },

	    };

	    var zNodes =[];
	    $.ajax({
	        url : "/user/authority/getAuthorityInfo",
	        type : "GET",
	        dataType:'json',
	        data:{},
	        success : function(result) {
	            /*console.log(retMsg);
	            zNodes = retMsg.result;
	            $.fn.zTree.init($("#" + treeNodeId), setting, zNodes);*/
	            
	        	//处理成功
            	if(CONSTANTS.HRS_OK == result.status){
            		zNodes = result.result;
    	            $.fn.zTree.init($("#" + treeNodeId), setting, zNodes);
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
	            
	        }
	    });
		
		
		
	},
		
	
		
}

$(function(){
	
	
	
});





