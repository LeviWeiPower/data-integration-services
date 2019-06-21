/**
 * 提取规则
 */
var extract_rule = {
		
	/**
	 * id标识的前缀
	 */
	idPrefix : "aimSpeed",
	
	/**
	 * Id值只增长
	 */
	idValCount : 0,	
	
	/**
	 * 现编辑的这个标签的属性的Key
	 */
	nowEditTagAttrKey : "nowEditTag",

	/**
	 * 现编辑的这个标签的数据属性的Key
	 */
	nowEditTagDataTagAttrKey : "nowEditTagDataTag",
	
	/**
	 * 是否是整体
	 */
	parentTruing : false,
	
	/**
	 * 初始化页面数据
	 */
	initData : function(){
		
		//填充数据
		$("#websiteNameSpanId").text("名称" + crawler.nowAddCrawlerObj.name);
		$("#websiteUrlSpanId").text("网址：" + crawler.nowAddCrawlerObj.url);

		
	},	
	
	/**
	 * 获取到页面填充到浏览框中
	 */
	getTemplatePageHtml:function(){
		//清空
		$("#browser").html("");
		
		//清空列表数据
		$("#dataTbodyId").html("");
		
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
	 * 获取标签规则
	 */
	getTagRule : function(obj,e){
		//判断事件是否需要进行冒泡，阻止事件冒泡
		common.stopBubble(e);
		
		//id增长
		extract_rule.idValCount++;
		
		//给标签添加上Id
		$(obj).prop("id",extract_rule.idPrefix + extract_rule.idValCount);
		
		//要抓取的数据的文本信息
		var eleText = $("#" + extract_rule.idPrefix + extract_rule.idValCount).text();
		
		//显示数据添加的标识的输入框区域
		document.getElementById("addDataTagId").style.display="";
		$("#dataTagInputId").val("");
		
		//添加按钮添加一个属性，这个属性的值就是当前这个要抓取的标签的id
		$("#addDataTagButId").attr(extract_rule.nowEditTagAttrKey,extract_rule.idPrefix + extract_rule.idValCount);
		
		//将数据展示到数据列表
		$("#dataTbodyId").append("<tr>"
									+ "<td>" + obj.tagName.toLowerCase() + "</td>"
									+ "<td>" + eleText + "</td>"
									+ "<td id='" + extract_rule.idValCount + "'></td>"
									+ '<td><label class="col-sm-2 control-label"><span class="glyphicon glyphicon-remove" aria-hidden="true" onclick="extract_rule.removeThisTr(this,\''+extract_rule.idPrefix + extract_rule.idValCount+'\');"></span></label></td>'
								+"</tr>");
		
	},
	
	/**
	 * 添加数据标识，将数据标识放到列表中
	 */
	addDataTag : function (obj){
		console.log(111);
		//获取到当前，数据定位的Id
		var tagId = $("#addDataTagButId").attr(extract_rule.nowEditTagAttrKey);
		
		//将填写的数据标识，填充到列表中
		$("#" + extract_rule.idValCount).text($("#dataTagInputId").val());//补充到列表中
		
		//给当前抓取到的数据标识，添加一个数据标识的属性值
		$("#" + tagId).attr(extract_rule.nowEditTagDataTagAttrKey,$("#dataTagInputId").val());//数据标识
	},
	
	/**
	 * 删除这个表格的这个tr，并删除这个要抓取的标签所添加的id属性和标识属性
	 */
	removeThisTr : function(obj,tagId){

		$("#" + extract_rule.idPrefix + extract_rule.idValCount).removeAttr(extract_rule.nowEditTagDataTagAttrKey);
		$("#" + extract_rule.idPrefix + extract_rule.idValCount).removeAttr("id");
		
		//将表标签进行删除
		$(obj).parent().parent().parent().remove();
	},
	
	/**
	 * 添加提取规则
	 */
	addExtractRule : function(){
		//如果没有添加任何的数据规则，那么则直接回到首页
		if(0 == extract_rule.idValCount){
			tool.ajaxloadPage("/main","get",CONSTANTS.CONTENT_ID);
			return;
		}
		
		//存放所有的id
		var ids = new Array();
		var arrCount = 0;
		
		//存放所有的父级层级数量
		var pLens = new Array();
		
		//查找特定Id的标签
		for (var i = 1; i < (extract_rule.idValCount + 1); i++){
			//获取到的对象是不为空的
			var objTemp = $("#" + extract_rule.idPrefix + i);
			if(null != objTemp && objTemp != undefined){
				ids[arrCount] = extract_rule.idPrefix + i;//获取所有Id
				//获取到所有父级层级数量
				pLens[arrCount] = $("#" + extract_rule.idPrefix + i).parents().length;
				arrCount++;
			}
		}

		//如果没有添加任何的数据规则，那么则直接回到首页
		if(0 >= pLens.length){
			tool.ajaxloadPage("/main","get",CONSTANTS.CONTENT_ID);
			return;
		}
		
		//找出最小的父级层级值
		var minPLen = 1000;//第一次绝对小于
		for (var i = 0; i < pLens.length; i++){
			var pLen = pLens[i];
			if(pLen < minPLen){
				minPLen = pLen;
			}
		}
		
		
		console.log("最小的父级层级值：" + minPLen);
		
		var commonObj = null;
		
		//因为可能出现都在一个tr里面的，所有查找不到公共的父级
		if(minPLen > 0){
			//查找所有标签的层级，层级等于minPLen，那么则取父级， 则是公共标签
			for (var i = 1; i < (extract_rule.idValCount + 1); i++){
				//获取到的对象是不为空的
				var objTemp = $("#" + extract_rule.idPrefix + i);
				
				if(null != objTemp && objTemp != undefined){
					//获取当前这个
					var len = $("#" + extract_rule.idPrefix + i).parents().length;
					if(len == minPLen){
						//按照最小层级找公共父级
						commonObj = $("#" + extract_rule.idPrefix + i).parent();
					}
				}
			}
		}else {
			commonObj = $("#" + extract_rule.idPrefix + 1).parent();
		}

		//拼公共代码的JSON
		var commonJson = extract_rule.assemblyCommonJson(commonObj);
		console.log("公共标签：" + commonObj[0].tagName.toLowerCase());
		console.log("公共标签Json：" + JSON.stringify(commonJson));
		
		var commons = $($(commonObj[0]).parent()).find(commonObj[0].tagName);
		console.log(commons);
		//extractRules
		//+document.getElementById("name_next").value +"&url="+document.getElementById("url_next").value +"&dataClassify="+document.getElementById("dataClassify_next").value+"&hierarchy="+document.getElementById("hierarchy_next").value
		var resultData="";
		//获取到位于父级下的位置
		for (var i = 0; i < ids.length; i++) {
			var tagJson = extract_rule.getTagHierarchyCheck(commonObj,$("#" + ids[i]),null,ids[i]);
			
			console.log("获取到的JSON：" + "Id：" + ids[i] + "   JSON：" + JSON.stringify(tagJson));

			var tagDataTag = $("#" + ids[i]).attr(extract_rule.nowEditTagDataTagAttrKey);
			console.log("数据标识：" + tagDataTag);
			
			//拼凑结果数据
			if(0 == i){
				resultData += "extractRules[" + i + "].sequence=" + crawler.nowAddCrawlerObj.sequence + "&";
			}else{
				resultData += "&" + "extractRules[" + i + "].sequence=" + crawler.nowAddCrawlerObj.sequence + "&";
			}
			
			//resultData += "extractRules[" + i + "].pageClassify=" + document.getElementById("pageDataInputId").value + "&";
			
			if(null != tagDataTag && tagDataTag != undefined){
				resultData += "extractRules[" + i + "].contentClassify=" + tagDataTag + "&";
			}
			resultData += "extractRules[" + i + "].rule=" + JSON.stringify(tagJson);
			
		}
		
		if(extract_rule.parentTruing){
			//基本三层就够了
			commonObj = commonObj.parent().parent().parent();
			commonJson = extract_rule.assemblyCommonJson(commonObj);
		}
		
		//公共div
		resultData += "&" + "ruleCommon.sequence=" + crawler.nowAddCrawlerObj.sequence + "&";
		resultData += "ruleCommon.rule=" + JSON.stringify(commonJson) + "&";
		resultData += "templateUrl=" + $("#queryTemplateInputId").val();
		
		console.log("结果数据：" + resultData);
		
		//提交数据
		var result = tool.ajax("/extract/saveExtractRule","POST",resultData,"JSON");
		if(CONSTANTS.HRS_OK == result.status){
			tool.ajaxloadPage("/main","get",CONSTANTS.CONTENT_ID);
    	}else{
    		//系统返回错误
    		toastr.error(result.msg);
    	}
		
	},
	
	/**
	 * 拼接公共标签JSON
	 */
	assemblyCommonJson : function(commonTag){
		var params = {
			"tag" : commonTag[0].tagName.toLowerCase()
		}
		return params;
	},
	
	/**
	 * 获取到节点位于父节点的位置 td 0，1
	 */
	getTagIndex : function (obj) {
		var objParent = $(obj).parent();//获取到父节点
		var childTags = objParent.children(obj.tagName);//获取到所有的子节点
		
		for (var i = 0; i < childTags.length; i++) {
			var childTag = $(childTags[i]).attr("id");
			//通过判断自定义的id
			if(childTag == $(obj).attr("id")){
				console.log("位置：" + i + "标签：" + obj.tagName);
				return i;
			}
			
		}
		
		
	},
	
	/**
	 * 进行校验公共标签下是否包含有所有子标签
	 */
	getTagHierarchyCheck : function (commonTag,obj,pTagJson,cObjId){
		/*var tagJson = extract_rule.getTagHierarchyJson(commonTag,obj,null,cObjId);
		if(null == tagJson || tagJson == undefined){
			//进行校验公共标签下是否包含有所有子标签，如果不是，那么则继续向上取
			var newCommonTag = commonTag.parent();
			extract_rule.parentTruing = true;
			console.log(newCommonTag[0].tagName + "==AAAAAA==" + commonTag);
			return extract_rule.getTagHierarchyCheck(newCommonTag,obj,null,cObjId);
		}
		return tagJson;*/
		
		//先进行查找这个公共标签下是否有这个子标签标签
		var findResult = commonTag.find("#" + cObjId);
		
		//没有的话则将公共标签进行提级
		if(undefined == findResult || null == findResult || undefined == findResult[0]){
			var newCommonTag = commonTag.parent();
			extract_rule.parentTruing = true;
			return extract_rule.getTagHierarchyCheck(newCommonTag,obj,null,cObjId);
		}
		//是有进行升级查找的，那么则要记录下父级
		if(extract_rule.parentTruing){
			//要先记录下父级的
			var result = {
				"tag" : $(findResult[0]).parent()[0].tagName.toLowerCase(),
				"index":extract_rule.getTagIndex($(findResult[0]).parent()[0])	
			}
			result.subTag = {
				"tag" : findResult[0].tagName.toLowerCase(),
				"index":extract_rule.getTagIndex(findResult[0])		
			}
			return result;
		}
		
		//是没有升级查找的，那么则不需要记录下父级
		return {
				"tag" : findResult[0].tagName.toLowerCase(),
				"index":extract_rule.getTagIndex(findResult[0])		
				}
		console.log(findResult);
		console.log(findResult[0]);
		console.log(findResult.length);
	},
	
	

	
}

$(function(){
	//初始化页面数据
	extract_rule.initData();
	
});
