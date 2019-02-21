var content_list = {

	/**
	 * 列表对象
	 */
	listTableObj : null,
	
	//列表重置（可根据事件触发进行）
	tableList : function(){
		var queryParams = {
	        filtrate: {//绝对值匹配
	            is_delete: "N"
	        },
	        orderField: "update_time",
	        orderingRule: "DESC",
	    };
		//列表重新搜索，按照新的参数进行搜索
		content_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
		
	},
	
	/**
	 * 关联数据
	 */
	renderRelevance : function (value, row, index) {
		var queryParams = {
			dataChainUuid : row.dataChainUuid,
            is_delete: "N"
        };
		
		//查找关联的数据
		var result = tool.ajax("/content/data/getDatas", "post", queryParams, "json");
        if(CONSTANTS.HRS_OK == result.status && result){
        	var resultArr = result.result;
        	
        	//拼接结果数据
        	if(null != resultArr && resultArr.length > 0){
        		var returnStr = "<ul>";
        		for (var i = 0; i < resultArr.length; i++) {
					var contentClassify = resultArr[i].contentClassify;
					var content = resultArr[i].content;
					
					returnStr += "<li>" + contentClassify + " --- " + content + "</li>";
				}

        		returnStr += "</ul>";
        		return returnStr;
        	}
        }
        
	},    
	
	
}

$(function(){
	
	//列表查询筛选条件
	var queryParams = {
        filtrate: {//绝对值匹配
        	sequence : creeper_list.nowCrawlerObj.sequence,
            is_delete: "N"
        },
        orderField: "update_time",
        orderingRule: "DESC",
    };
	
	//提取规则查询筛选条件
	var extractQueryParams = {
			sequence : creeper_list.nowCrawlerObj.sequence,
            is_delete: "N"
        };
	
	//设置查找的数据只是查找某一个类型的数据
	var result = tool.ajax("/extract/getDatas", "post", extractQueryParams, "json");
	console.log("1111");
	if(CONSTANTS.HRS_OK == result.status && result){
    	var resultArr = result.result;
    	
    	//拼接结果数据
    	if(null != resultArr && resultArr.length > 0){
    		for (var i = 0; i < resultArr.length; i++) {
				if(null != resultArr[i].contentClassify && "" != resultArr[i].contentClassify){
					queryParams.filtrate.content_classify = resultArr[i].contentClassify;
					break;
				}
			}
    		
    	}
	}
	
    //初始化列表数据
	content_list.listTableObj = common.bootstrapTable('dataListTableId', '/content/data/getPagingList', 'get', queryParams);

	
	
});

