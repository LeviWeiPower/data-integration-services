/**
 * 是否是异步
 */
var is_async = {
	
	/**
	 * 初始化页面数据
	 */
	/*initData : function(){
		
		//填充数据
		$("#websiteNameSpanId").text("名称" + crawler.nowAddCrawlerObj.name);
		$("#websiteUrlSpanId").text("网址：" + crawler.nowAddCrawlerObj.url);

		
	},	*/
	
	/**
	 * 获取到页面填充到浏览框中
	 */
	getTemplatePageHtml:function(){
		//清空
		$("#browser").html("");
		
		//清空列表数据
		//$("#dataTbodyId").html("");
		
	    //后台获取处理后的模板页面源代码
		var param = {
			sequence : crawler.nowAddCrawlerObj.sequence,
			templateUrl : $("#queryTemplateInputId").val()
		}
		
		tool.ajax("/extract/getTemplatePageHtml", "post", param, "JSON", function (result) {
            
            if(CONSTANTS.HRS_OK == result.status){
            	document.getElementById("browser").innerHTML += result.result;
        	}else{
        		//系统返回错误
        		toastr.error(result.msg);
        	}
            
        });
	},
	
	
	/**
	 * 添加请求信息
	 */
	addIsAsync : function (){
		
		var result = tool.ajaxFormArr('/crawler/isAsync','POST','isAsyncFormId',true,'JSON');
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
	//初始化页面数据
	extract_rule.initData();
	
});
