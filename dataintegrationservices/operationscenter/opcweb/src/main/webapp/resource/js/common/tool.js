 var priceExp = /^([0-9][0-9]*)+(\.[0-9]{1,13})?$/;
var priceExp_2 = /^-?([0-9][0-9]*)+(\.[0-9]{1,13})?$/;
var tool={
	/**
	 * 请求页面数据
	 * url:访问子路径
	 * method:请求方式 - Post Get 
	 */
	ajaxloadPage : function (url,method,mainDivId){
		
		
		var sendUrl = url;
		
		//访问本身的服务器资源，如果携带了http则是访问别的服务器资源
		if(sendUrl.indexOf("http://") == -1){
			sendUrl = WEB_URL + url
		}
		
		// 传输
		$.ajax({
			url : sendUrl, //方法地址
			type : method, //传输方法
			success : function(data) {
				$("#" + mainDivId).html("");
				$("#" + mainDivId).html(data);
			},
			error : function(e) {
				console.log(e);
				alert(CONSTANTS.ERROR);
			}
		});
	},
	
	// 以formdata的形式传输数据/文件的表单方法.
	/**
	 * url是方法路径 默认空
	 * type是传输方式 默认post
	 * formId是表单id 默认空
	 * dataType是数据类型 默认空
	 * callback是成功的返回方法 可以等于null,默认为弹出结果,可以等于return为返回参数 
	 * cache是缓存
	 * async是异步 
	 * contentType是编码类型 
	 * processData是是否转换为对象 
	 * serialize是是否序列化
	 */
	ajaxForm : function(url, type, formId, serialize, dataType, callback, cache, async,contentType,processData) {
		var data = null;
		// 判断是否序列化处理
		if (serialize == true) {
			 data = $("#" + formId).serializeJSON();
		} else {
			data = new FormData($("#" + formId)[0]);
		}
		return tool.ajax( url, type, data, dataType, callback, cache, async,contentType,processData);
	},
	
	/**
	 * 获取到序列数据
	 */
	getDataSerialize : function(formId){
        var data = null;
		data = $("#" + formId).serializeJSON();
        return data;
    },
	
	/**
	 * url是方法路径 默认空
	 * type是传输方式 默认post
	 * formId是表单id 默认空
	 * dataType是数据类型 默认空
	 * callback是成功的返回方法 可以等于null,默认为弹出结果,可以等于return为返回参数 
	 * cache是缓存
	 * async是异步 
	 * contentType是编码类型 
	 * processData是是否转换为对象 
	 * serialize是是否序列化
	 */
	ajaxFormArr : function(url, type, formId, serialize, dataType, callback, cache, async,contentType,processData) {
		var data = null;
		// 判断是否序列化处理
		if (serialize == true) {
			data = $("#" + formId).serialize();
		} 
		return tool.ajax(url, type, data, dataType, callback, cache, async,contentType,processData);
	},
	
	/** 
	 * url是方法路径 
	 * type是传输方式 默认post
	 * data是数据 默认空
	 * dataType是数据类型 默认空
	 * callback是成功的返回方法 可以等于null,默认为弹出结果,可以等于return为返回参数
	 * cache是缓存
	 * asyncs是异步 
	 * processData是否转换为对象 
	 */
	ajax : function(url, type, data, dataType, callback, cache, asyncs,
			contentType,processData) {
		var datas;
//		debugger;
		// 处理部分数据
		type = type || 'POST'; //type默认值为'post'
		data = data || null; //data默认值为null
		//var json = JSON.stringify(data);
		dataType = dataType || "JSON"; //dataType默认值为null
//		cache = cache || true;//默认为true
//		asyncs = asyncs || false;//默认为true
//		contentType = contentType || true;//默认为true
		cache = null == cache || undefined == cache ? true : cache;//默认为true
		asyncs = null == asyncs || undefined == asyncs ? false : asyncs;//默认为true
		contentType = null == contentType || undefined == contentType ? "application/x-www-form-urlencoded; charset=utf-8" : contentType;//默认为true
		processData = null == processData || undefined == processData ? true : processData;//默认为true
		//var dataJson = {"params" : json};
		//debugger;
		// 传输
		$.ajax({
			url : WEB_URL + url, //方法地址
			type : type, //传输方法
			data : data, //参数
			dataType : dataType, //参数类型
			cache : cache, //是否缓存
			async : asyncs,//是否异步
			contentType : contentType,
			processData : processData, //是否转换为对象 
			success : function(data) {
				if (data.status == CONSTANTS.HRS_OK) {
					if(callback != null && callback!=undefined){
						//判断如果需要二次处理,则返回参数给callback方法.
						callback(data);
					}else{
						//如果需要返回参数
						datas = data;
					}
				//以下为失败弹窗	
				}else if(data.status == CONSTANTS.HRS_UNAUT_STATUS){
					alert(CONSTANTS.HRS_UNAUT);
				}else if(data.status == CONSTANTS.HRS_PARAM_ERROR){
					alert(CONSTANTS.PARAM_ERROR);
				}else if(data.status == CONSTANTS.HRS_BUSINESS_ERROR){
					alert(CONSTANTS.BUSINESS_ERROR);
				}else if(data.status == CONSTANTS.HRS_SYS_ERROR_STATUS){
					alert(CONSTANTS.HRS_SYS_ERROR);
				}else{
					datas=data;
				}

			},
			error : function(e) {
				console.log(e);
				alert(CONSTANTS.ERROR);
			}
		});
		return datas;
	},
	//填充form表单
	fillForm:function(formId,data){
		$("#"+formId+" input").each(function(){
			var name=$(this).attr("name");
			$(this).val(data[name]);
		});
	},
	/**
	 * 是否为空
	 */
	isEmpty:function(str){
		if(str==null||str==""||str==undefined){
			return true
		}
		return false;
	},
	//获取选中数据
	getSelectData:function(grid,id){
		var results=grid.getResults();
		if(null!=results){
			for(var i=0;i<results.length;i++){
				var data=results[i];
				if(null!=data){
					var cid=data["id"]
					if(id==cid){
						return data;
					}
				}
			}
		}
	},
	/**
	 * 截取字符串+'...'
	 * @param str 截取字符串对象
	 * @param startIndex 开始截取位置，默认从0开始
	 * @param length 截取长度，默认10位字符
	 * @returns
	 */
	subString:function(str,startIndex,length){
		if(str==''||str==null||str==undefined){
			return '';
		}
		if(startIndex==null){
			startIndex=0;
		}
		if(length==null){
			length=10;
		}
		if(str.length>length){
			
			str=str.substring(startIndex,startIndex+length)+"...";
		}
		return str;
	},
	/**
	 * startDate 开始算的时间
	 * d 往前或往后退几天
	 */
	getDateDiff:function(startDate,d){
		    var year = startDate.getFullYear();
		    var mon=startDate.getMonth()+1;
		    var day=startDate.getDate();
		    if(day <= d){
            if(mon>1) {
               mon=mon-1;
            }
           else {
             year = year-1;
             mon = 12;
             }
           }
          startDate.setDate(startDate.getDate()+d);
          year = startDate.getFullYear();
          mon=startDate.getMonth()+1;
          day=startDate.getDate();
          s = year+"-"+(mon<10?('0'+mon):mon)+"-"+(day<10?('0'+day):day);
          return s;
	},
	
	bootDateFormate(value, row, index) {
	    var dateFormate = new Date(value).toLocaleDateString();
	    var html = '<span>' + dateFormate + '</span>';
	    return html;
	},
	
	//获取年月日的时间  如2017-06-02
	getDateFormat:function(date){
		var year = date.getFullYear();
        var mon=date.getMonth()+1;
        var day=date.getDate();
        var s = year+"-"+(mon<10?('0'+mon):mon)+"-"+(day<10?('0'+day):day);
        return s;
	},

	//获取年月日的时间  如2017/06/02
	getDateStrFormat:function(dateStr){
		var date = new Date(dateStr);
		var year = date.getFullYear();
		var mon=date.getMonth()+1;
		var day=date.getDate();
		var s = year+"/"+(mon<10?('0'+mon):mon)+"/"+(day<10?('0'+day):day);
		return s;
	},
	
	//获取年月日的时间  如2017-06-02 12:12:12
	getDateTimeStrFormat:function(dateTimeStr){
		var date = new Date(dateTimeStr); //实例一个时间对象；
		var year = date.getFullYear();   //获取系统的年；
		var mon = date.getMonth()+1;   //获取系统月份，由于月份是从0开始计算，所以要加1
		var day = date.getDate(); // 获取系统日，
		var hours = date.getHours(); //获取系统时，
		var minutes = date.getMinutes(); //分
		var seconds = date.getSeconds(); //秒
		return year + "-" + mon + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
	},
	
	//数组去重
	unique:function(arr) {
		  var ret = []
		  var hash = {}

		  for (var i = 0; i < arr.length; i++) {
		    var item = arr[i]
		    var key = typeof(item) + item
		    if (hash[key] !== 1) {
		      ret.push(item)
		      hash[key] = 1
		    }
		  }
		  return ret
	},
	
	/**
	 * 如：下拉框1  下拉框2
	 * 
	 * selectIdArr = [下拉框1Id,下拉框Id2]
	 * data = [{id:1,pid:0,name=aa},{id:2,pid:1,name=bb}]
	 * fieldName = id
	 * pFieldName = pid
	 * rootFieldValue = 0
	 * selectValField = id
	 * selectTextField = name
	 * 
	 * 动态下拉框
	 * selectIdArr 下拉的Id数组，按照这个进行排序
	 * data 数据
	 * fieldName 字段名称
	 * pFieldName 父字段名称
	 * rootPFieldValue 主父字段的值
	 * selectValField 下拉的值(value)
	 * selectTextField 下拉的显示值
	 */
	initSelectDynamic : function(selectIdArr,data,fieldName,pFieldName,rootPFieldValue,selectValField,selectTextField){
		var len = selectIdArr.length;
		var dataLen = data.length;
		
		for (var i = 0; i < len; i++) {
			
			for (var j = 0; j < dataLen; j++) {
				//主
				if(i == 0){
					if(rootPFieldValue == data[j][pFieldName]){
						$("#" + selectIdArr[i]).append("<option value='"+data[j][selectValField]+"'>"+data[j][selectTextField]+"</option>");
					}
					
				}else{
					//是这个类型的子
					if($("#" + selectIdArr[i - 1]).val() == data[j][pFieldName]){
						$("#" + selectIdArr[i]).append("<option value='"+data[j][selectValField]+"'>"+data[j][selectTextField]+"</option>");
					}
				}
			}
		}
	},
	
	/**
	 * 如：下拉框1  下拉框2 下拉框3
	 * changeSelectId = 下拉框2Id
	 * data = [{id:1,pid:0,name=aa},{id:2,pid:1,name=bb}]
	 * pFeildName = pid
	 * selectValField = id
	 * selectTextField = name
	 * objId = 下拉框1Id
	 * nextId = 下拉框3Id
	 * 动态的进行数据切换(通过每次请求)
	 * changeSelectId 需要变化数据的下拉框Id
	 * data 数据
	 * pFeildName 数据的字段名称
	 * selectValField 下拉的值(value)
	 * selectTextField 下拉的显示值
	 * objId 当前这个选中的id
	 * nextId 下一个变化的id
	 */
	selectDynamicChange : function (changeSelectId,dataUrl,pFeildName,selectValField,selectTextField,objId,nextId,callback) {
    	
		
		var data = tool.ajax(dataUrl,'get').result;
		
		//拿到当前这个值
		var val = $("#" + objId).val();
        var leng = data.length;
        //清空
        $("#" + changeSelectId).html("");
        for (var i = 0; i < leng; i++) {
    		if(data[i][pFeildName] == val){
    			$("#" + changeSelectId).append("<option value='"+data[i][selectValField]+"'>"+data[i][selectTextField]+"</option>");
    		}
    	}
        
        if(null != nextId){ 
        	tool.selectDynamicChange(nextId,dataUrl,pFeildName,selectValField,selectTextField,changeSelectId,callback)
        }else{
        	if(null != callback && undefined != callback){
        		callback();
        	}
        }
        
    },
	
	/**
	 * 通过远程获取服务器数据，填充下拉框
	 * selectId 下拉框Id
	 * dataUrl = 地址URL
	 * selectValField = 下拉框的值显示的字段数据
	 * selectTextField = 下拉框文本显示的字段数据
	 * callback 下一个变化的id
	 */
	fillSelectDataOfGet : function (selectTagId,params,dataUrl,selectValField,selectTextField,callback) {
    	
		//获取数据
		var result = tool.ajax(dataUrl,'get',params);
		console.log(result);
		//处理成功则刷新列表
		if(CONSTANTS.HRS_OK == result.status){
			var data = result.result;
			var leng = data.length;
			
			//清空
	        $("#" + selectTagId).html("");
	        for (var i = 0; i < leng; i++) {
	    		$("#" + selectTagId).append("<option value='"+data[i][selectValField]+"'>"+data[i][selectTextField]+"</option>");
	    	}
		}
        
    },
    
    /**
     * 自有数据，填充下拉框
     * selectId 下拉框Id
     * dataUrl = 地址URL
     * selectValField = 下拉框的值显示的字段数据
     * selectTextField = 下拉框文本显示的字段数据
     * callback 下一个变化的id
     */
    fillSelectDataOfOwn : function (selectTagId,data,selectValField,selectTextField,first) {
    	
		var leng = data.length;
		
		//清空
		$("#" + selectTagId).html("");
		
		//第一条
		if(first){
			$("#" + selectTagId).append("<option value=''>-- 请选择 --</option>");
		}
		
		for (var i = 0; i < leng; i++) {
			$("#" + selectTagId).append("<option value='"+data[i][selectValField]+"'>"+data[i][selectTextField]+"</option>");
		}
    	
    },
    
    
	    
}
