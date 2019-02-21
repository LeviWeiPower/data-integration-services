
var request_packet = {
	
	/**
	 * 页面添加Cook的key和value输入框
	 */
	cookieLenCount : 1,
	
	/**
	 * 页面添加请求参数的key和value输入框
	 */
	reqParamLenCount : 1,
	
	/**
	 * 页面添加请求包头的key和value输入框
	 */
	reqHeaderLenCount : 1,
	
	/**
	 * 初始化页面数据
	 */
	initData : function(){
		
		//填充数据
		$("#websiteNameSpanId").text("名称：" + crawler.nowAddCrawlerObj.name);
		$("#websiteUrlSpanId").text("网址：" + crawler.nowAddCrawlerObj.url);
		
		/*$("#urlInputId").val(crawler.nowAddCrawlerObj.url);
		$("#hierarchyInputId").val(crawler.nowAddCrawlerObj.hierarchy);
		$("#nameInputId").val(crawler.nowAddCrawlerObj.name);
		$("#dataClassifyInputId").val(crawler.nowAddCrawlerObj.dataClassify);*/
		
		//请求包头信息
		$("#reqHeaderSequenceInputId").val(crawler.nowAddCrawlerObj.sequence);

		//Cookie信息
		$("#cookieSequenceInputId").val(crawler.nowAddCrawlerObj.sequence);
		
		//请求过滤参数信息
		$("#requestParamSequenceInputId").val(crawler.nowAddCrawlerObj.sequence);

		
	},

	/**
	 * 添加请求包头信息Html信息
	 */
	addReqHeaderInputHtml : function () {
		$("#reqHeaderMsgDivId").append('<div>'
	    		+ '<input type="hidden" name="requestHeaderBeans[' + request_packet.reqHeaderLenCount + '].sequence" value="'+crawler.nowAddCrawlerObj.sequence+'"/>'
		    	+ '<label class="col-sm-2 control-label"><span class="glyphicon glyphicon-remove" aria-hidden="true" onclick="request_packet.removeThisElement(this);"></span> Key：</label>'
		    	+ '<div class="col-sm-10">'
		    	+ '<input type="text" class="form-control" name="requestHeaderBeans[' + request_packet.reqHeaderLenCount + '].headerKey" placeholder="请输入Key">'
		    	+ '</div>'
		    	+ '<label class="col-sm-2 control-label">Value：</label>'
		    	+ '<div class="col-sm-10">'
		    	+ '<input type="text" class="form-control" name="requestHeaderBeans[' + request_packet.reqHeaderLenCount + '].headerValue" placeholder="请输入Value">'
		    	+ '</div>'
		    	+ '</div>');

		request_packet.reqHeaderLenCount++;
		
	},

	/**
	 * 添加请求过滤参数信息Html信息
	 */
	addReqParamInputHtml : function () {
		$("#reqParamMsgDivId").append('<div>'
				+ '<input type="hidden" id="requestParamSequenceInputId" name="requestParamBeans[' + request_packet.reqParamLenCount + '].sequence" value="'+crawler.nowAddCrawlerObj.sequence+'"/>'
				+ '<label class="col-sm-2 control-label"><span class="glyphicon glyphicon-remove" aria-hidden="true" onclick="request_packet.removeThisElement(this);"></span> Key：</label>'
				+ '<div class="col-sm-10">'
				+ '<input type="text" class="form-control" name="requestParamBeans[' + request_packet.reqParamLenCount + '].paramKey" placeholder="请输入Key" >'
				+ '</div>'
				+ '<label class="col-sm-2 control-label">Value：</label>'
				+ '<div class="col-sm-10">'
				+ '<input type="text" class="form-control" name="requestParamBeans[' + request_packet.reqParamLenCount + '].paramValue" placeholder="请输入Value">'
				+ '</div>'
				+ '</div>');

		request_packet.reqParamLenCount++;
		
	},
	
	/**
	 * 添加Cookie的html信息
	 */
	addCookieInputHtml : function () {
	    $("#cookieDivId").append('<div>'
	    		+ '<input type="hidden" name="cookieBeans[' + request_packet.cookieLenCount + '].sequence" value="'+crawler.nowAddCrawlerObj.sequence+'"/>'
	    		+ '<label class="col-sm-2 control-label"><span class="glyphicon glyphicon-remove" aria-hidden="true" onclick="request_packet.removeThisElement(this);"></span> Key：</label>'
	    		+ '<div class="col-sm-10">'
	    		+ '<input type="text" class="form-control" name="cookieBeans[' + request_packet.cookieLenCount + '].cookieKey" placeholder="请输入Key" >'
	    		+ '</div>'
	    		+ '<label class="col-sm-2 control-label">Value：</label>'
	    		+ '<div class="col-sm-10">'
	    		+ '<input type="text" class="form-control" name="cookieBeans[' + request_packet.cookieLenCount + '].cookieValue" placeholder="请输入Value">'
	    		+ '</div>'
	    		+ ' </div>');
	    request_packet.cookieLenCount++;
	},
	
	/**
	 * 删除当前这个元素
	 */
	removeThisElement : function(obj){
		$(obj).parent().parent().remove();
	},
	
	/**
	 * 添加请求信息
	 * @param obj
	 * @returns
	 */
	addReqMsg : function (obj){
		
		var result = tool.ajaxFormArr('/requestPacket/addRequestPacketMsg','POST','reqPacketFormId',true,'JSON');
		if(null != result && undefined != result){
        	
        	//处理成功则刷新列表
        	if(CONSTANTS.HRS_OK == result.status){
        		tool.ajaxloadPage("/extract/addExtractPage","get",CONSTANTS.CONTENT_ID);
        	}else{
        		//系统返回错误
        		toastr.error(result.msg);
        	}
        }else {
        	//提示错误信息
        	toastr.error(CONSTANTS.ERROR);
        }
		
	},
		
	
}

$(function(){
	//初始化数据
	request_packet.initData();
	

    
    
	
	
});

