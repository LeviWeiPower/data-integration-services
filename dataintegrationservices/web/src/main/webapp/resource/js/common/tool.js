 var priceExp = /^([0-9][0-9]*)+(\.[0-9]{1,13})?$/;
var priceExp_2 = /^-?([0-9][0-9]*)+(\.[0-9]{1,13})?$/;
var tool={
		/**
		 * 请求页面数据
		 * url:访问子路径
		 * method:请求方式 - Post Get 
		 */
		ajaxloadPage : function (url,method,mainDivId){
			// 传输
			$.ajax({
				url : WEB_URL + url, //方法地址
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
		ajaxForm : function(url, type, formId, serialize, dataType, callback, cache, async,processData) {
			var data = null;
			// 判断是否序列化处理
			if (serialize == true) {
				 data = $("#" + formId).serializeJSON();
			} 
			return tool.ajax(url, type, data, dataType, callback, cache, async,processData);
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
		ajaxFormArr : function(url, type, formId, serialize, dataType, callback, cache, async,processData) {
			var data = null;
			// 判断是否序列化处理
			if (serialize == true) {
				data = $("#" + formId).serialize();
			} 
			return tool.ajax(url, type, data, dataType, callback, cache, async,processData);
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
				 processData) {
			console.log(data);
			var datas;
			// 处理部分数据
			type = type || 'POST'; //type默认值为'post'
			data = data || null; //data默认值为null
			//var json = JSON.stringify(data); 

			dataType = dataType || null; //dataType默认值为null
			cache = cache || true;//默认为true
			asyncs = asyncs || false;//默认为true
			processData = processData || true;//默认为true
			//var dataJson = {"params" : json};
			console.log(data);
			// 传输
			$.ajax({
				url : WEB_URL + url, //方法地址
				type : type, //传输方法
				data : data, //参数
				dataType : dataType, //参数类型
				cache : cache, //是否缓存
				async : asyncs,//是否异步
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
					//alert(CONSTANTS.ERROR);
				}
			});
			return datas;
		},
		//填充form表单
		fillForm:function(formId,data){
			$("#"+formId+" input").each(function(){
				var name=$(this).attr("name");
				$(this).val(data[name]);
			})
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
		//获取年月日的时间  如2017-06-02
		getDateFormat:function(date){
			var year = date.getFullYear();
            var mon=date.getMonth()+1;
            var day=date.getDate();
            var s = year+"-"+(mon<10?('0'+mon):mon)+"-"+(day<10?('0'+day):day);
            return s;
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
}
