var creeper_list = {
	
	/**
	 * 列表对象
	 */
	listTableObj : null,

	/**
	 * 正在操作相关采集者的对象
	 */
	nowCrawlerObj : null,
	
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
		creeper_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
		
	},
	
	/**
	 * 状态
	 */
	renderIsStart : function (value, row, index) {
		if('N' == value){
			return '<a href="javascript:void(0);" onclick="creeper_list.run(\''+row.id+'\',\'Y\',this);"><span class="glyphicon glyphicon-play" aria-hidden="true" style="color: red;"></span></a>';
		}
		return '<a href="javascript:void(0);" onclick="creeper_list.run(\''+row.id+'\',\'N\',this);"><span class="glyphicon glyphicon-pause" aria-hidden="true" style="color: #02ff00;"></span></a>';
	},	
	
    
	/**
	 * 采集的数据量
	 */
	renderDataCount : function (value, row, index) {
		var queryParams = {
			sequence : row.sequence,
            is_delete: "N"
        };
		var result = tool.ajax("/content/data/getCountSize", "post", queryParams, "json");
        if(CONSTANTS.HRS_OK == result.status && result){
        	return '<a href="javascript:void(0);" onclick=\'creeper_list.showCreeperContentData('+JSON.stringify(row)+')\'>'+result.result+'</a>';
        }
        
	},    
	
	/**
	 * 删除
	 */
	dels : function(){
		var arrSelections = creeper_list.listTableObj.bootstrapTable('getSelections');
		common.delByIds(arrSelections,"/crawler/deletes", "post","JSON",function(data){
			creeper_list.tableList();
		});
	},
	
	/**
	 * 运行
	 */
	run : function(id,flag,obj){
		var queryParams = {
				id : id,
				flag : flag
        };
		var result = tool.ajax("/crawler/run", "post", queryParams, "json");
        if(CONSTANTS.HRS_OK == result.status && result){
        	creeper_list.tableList();
        }
		
	},
	
	/**
	 * 显示采集者，采集到的内容数据列表
	 */
	showCreeperContentData : function (currentObj){
		//当前这个采集者的数据对象
		creeper_list.nowCrawlerObj = currentObj;
		
		//加载数据页面
		tool.ajaxloadPage("/content/data/getListPage", "get", CONSTANTS.CONTENT_ID);
	}
	
}

$(function(){
	
	var queryParams = {
        filtrate: {//绝对值匹配
            is_delete: "N"
        },
        orderField: "update_time",
        orderingRule: "DESC",
    };
    //初始化列表数据
	creeper_list.listTableObj = common.bootstrapTable('crawlerTableListId', '/crawler/getPagingList', 'get', queryParams);

	
	
});

