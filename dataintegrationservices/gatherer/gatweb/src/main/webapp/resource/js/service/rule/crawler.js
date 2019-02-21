
var crawler = {
		
	/**
	 * 正在添加的Crawler成功之后返回的对象
	 */
	nowAddCrawlerObj : null,

	/**
	 * 页面添加搜索参数的key和value输入框
	 */
	searchLenCount : 0,
	
	/**
	 * 添加基础规则信息
	 */
    add: function () {
    	//调用验证的方法
		common.preSubmitValidator('crawlerFormId','bootstrapValidator',function(){
			
			/*tool.ajaxForm("/creeper/baserule/saveBaseRule", "post", "mainAddFormId", true, "JSON", function (data) {
	            tool.ajaxloadPage("/reqMsg/addReqView?name="+data.data.name+"&url="+data.data.url+"&dataClassify="+data.data.dataClassify+"&hierarchy="+data.data.hierarchy, "get", CONSTANTS.CONTENT_ID);
	        });*/
			
			//成功的回调方法
			var result = tool.ajaxForm('/crawler/save','post','crawlerFormId',true,'json');
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		crawler.nowAddCrawlerObj = result.result;
            		tool.ajaxloadPage("/requestPacket/addRequestPacketMsgPage","get",CONSTANTS.CONTENT_ID);
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
    
    /**
     * 取消添加采集任务，则需要将以添加的信息进行删除
     */
    cancelAddCrawler : function() {
    	//未添加的则直接返回首页
    	if(undefined == crawler.nowAddCrawlerObj || null == crawler.nowAddCrawlerObj){
    		tool.ajaxloadPage("/main","get",CONSTANTS.CONTENT_ID);
    	}else{
    		var result = tool.ajaxForm('/crawler/cancelAddCrawler/' + crawler.nowAddCrawlerObj.id,'post','crawlerFormId',true,'json');
    		if(CONSTANTS.HRS_OK == result.status){
    			tool.ajaxloadPage("/main","get",CONSTANTS.CONTENT_ID);
    		}
    	}
    	
    },
    
    
    
   
	
}

$(function(){

	//定向搜索参数输入框
	$("#isSearchDivContent").hide();
	
    //初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});
	
});

