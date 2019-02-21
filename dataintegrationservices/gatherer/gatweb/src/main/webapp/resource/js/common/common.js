//公共的js代码
var common = {

    /**
     * 分页查找
     *
     tableId  列表Id
     url    //请求后台的URL（*）
     method          请求方式（*）
     queryParams    传递参数（*）
     toolbarId       工具按钮用哪个容器
     striped         是否显示行间隔色
     cache            是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
     pagination      是否显示分页（*）
     sortable        是否启用排序
     sortOrder        排序方式
     sidePagination  分页方式：client客户端分页，server服务端分页（*）
     pageNumber:     初始化加载第一页，默认第一页
     pageSize:       每页的记录行数（*）
     pageList:        可供选择的每页的行数（*）
     search:        是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
     strictSearch:    设置为 true启用 全匹配搜索，否则为模糊搜索
     showColumns:    是否显示所有的列
     showRefresh:    是否显示刷新按钮
     minimumCountColumns:    最少允许的列数
     clickToSelect    是否启用点击选中行
     height:        行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
     uniqueId:        每一行的唯一标识，一般为主键列
     showToggle:    是否显示详细视图和列表视图的切换按钮
     cardView:        是否显示详细视图
     detailView:    是否显示父子表
     */
    bootstrapTable: function (tableId, url, method, queryParams, toolbarId, pageList, striped, cache, pagination, sortable, sortOrder,
                              sidePagination, pageNumber, pageSize, pageListArr, search, strictSearch,
                              showColumns, showRefresh, minimumCountColumns, clickToSelect, height, uniqueId,
                              showToggle, detailView, cardView) {
        cardView = cardView || false;
        method = method || "POST";
        toolbarId = toolbarId || "";
        striped = striped || true;
        cache = cache || false;
        pagination = pagination || true;
        sortable = sortable || false;
        sortOrder = sortOrder || "asc";
        sidePagination = sidePagination || "server";
        pageNumber = pageNumber || 1;
        pageSize = pageSize || 10;
        pageListArr = pageListArr || [10];
        search = search || false;
        strictSearch = strictSearch || true;
        showColumns = showColumns || true;
        showRefresh = showRefresh || true;
        minimumCountColumns = minimumCountColumns || 0;
        clickToSelect = clickToSelect || true;
        height = height || 500;
        uniqueId = uniqueId || "id";
        showToggle = showToggle || false;
        detailView = detailView || false;
        pageList = pageList || [10];

        return $('#' + tableId).bootstrapTable({
            // url: '/main/GetDepartment',       //请求后台的URL（*）
            url: WEB_URL + url,       //请求后台的URL（*）
            method: method,                      //请求方式（*）
            //toolbar: '#' + toolbarId,                //工具按钮用哪个容器
            striped: striped,                      //是否显示行间隔色
            cache: cache,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: pagination,                   //是否显示分页（*）
            sortable: sortable,                     //是否启用排序
            sortOrder: sortOrder,                   //排序方式
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
                                 // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            queryParams: function (params) {


                if (null == queryParams) {
                    queryParams = {};
                }
                queryParams.pageNumber = params.pageNumber;
                queryParams.pageSize = params.pageSize;
                console.log(queryParams);
                return queryParams;
            },
            sidePagination: sidePagination,           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: pageNumber,                       //初始化加载第一页，默认第一页
            pageSize: pageSize,                       //每页的记录行数（*）
            pageList: pageList,        //可供选择的每页的行数（*）
            search: search,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: strictSearch,					//设置为 true启用 全匹配搜索，否则为模糊搜索
            showColumns: showColumns,                  //是否显示所有的列
            showRefresh: showRefresh,                  //是否显示刷新按钮
            minimumCountColumns: minimumCountColumns,             //最少允许的列数
            clickToSelect: clickToSelect,                //是否启用点击选中行
            height: height,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: uniqueId,                     //每一行的唯一标识，一般为主键列
            showToggle: showToggle,                    //是否显示详细视图和列表视图的切换按钮
            cardView: cardView,                    //是否显示详细视图
            detailView: detailView                   //是否显示父子表
        });

    },

    /**
     * 根据多个Id进行删除
     * arrSelections 选择的Id数组
     * url 地址
     * type 请求类型
     * data 数据
     * dataType 请求方式
     * callback 回调方法
     */
    delByIds: function (arrSelections, url, type, dataType, successCallback, errorCallback) {
        //获取到里面的所有选中的id
        var idVals = common.getObjFieldVals(arrSelections, "id");

        if (idVals.length <= 0) {
            toastr.warning(CONSTANTS.MORE_RECORD_MSG);
            return;
        }

        //将数组转为字符串
        var data = {ids: idVals.join(",")};

        Ewin.confirm({message: CONSTANTS.COMFIRM_DEL_MSG}).on(function (e) {
            if (!e) {
                return;
            }
            //请求后台删除数据
            var result = tool.ajax(url, type, data, dataType);

            if (null != result && undefined != result) {

                //处理成功则刷新列表
                if (CONSTANTS.HRS_OK == result.status) {
                    toastr.success(result.msg);
                    //回调方法
                    successCallback(result);
                } else {
                    //系统返回错误
                    toastr.error(result.msg);
                    errorCallback(result);
                }
            } else {
                //提示错误信息
                toastr.error(CONSTANTS.ERROR);
            }
        });
    },

    /**
     * 获取到多个对象中的属性某个属性值，以数组返回
     */
    getObjFieldVals: function (objs, field) {
        return $.map(objs, function (row) {
            return row[field];
        });

    },

    /**
     * event 触发的事件，必填
     * keyCode 触发的键盘事件是哪个键触发的  13 = Enter键
     * callback 触发后的回调方法
     *
     * 例子：common.keyupEvent(event,13,test());
     *
     */
    keyupEvent: function (event, keyCode, callback) {
        if (event.keyCode == 13) {
            callback(event);
        }
    },

    /**
     *
     * formId 表单Id
     * validateData 验证数据
     * successCallback 成功的回调方法
     * errorCallback 失败的回调方法
     */
    preSubmitValidator: function (formId, validateData, successCallback, errorCallback) {
        //再次进行验证
        var bootstrapValidator = $("#" + formId).data(validateData);
        bootstrapValidator.validate();
        
        if (bootstrapValidator.isValid()) {
            //验证成功则调用成功的回调方法
            successCallback();
            
            //验证成功后重新初始化
            /*$("#" + formId).data('bootstrapValidator').destroy();
            $("#" + formId).data('bootstrapValidator', null);
            $('form').bootstrapValidator({feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }});*/
            
            
        } else {
            //验证失败则调用失败的回调方法
        	if(undefined != errorCallback && null != errorCallback){
                errorCallback();
        	}
        }
    },

    /**
     * 指定标签form表单数据填充完整
     * formId 表单Id
     * labelName 要填充的数据的表单内的标签名(input，textarea)
     * data 要填充的数据
     */
    paddingForm: function (formId, labelName, data) {
        $("#" + formId + " " + labelName).each(function () {
            var name = $(this).attr("name");
            $(this).val(data[name]);
        })
    },

    /**
     * 替换表单内的指定标签数据
     * formId 表单Id
     * labelName 要清空的数据的表单内的标签名(input，textarea)
     * data 指定的数据
     */
    clearForm: function (formId, labelName, data) {
        $("#" + formId + " " + labelName).each(function () {
            $(this).val(data);
        })
    },

    //获取年月日的时间  如2017-06-02
    getDateFormat: function (date) {
        var year = date.getFullYear();
        var mon = date.getMonth() + 1;
        var day = date.getDate();
        var s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
        return s;
    },

    //获取年月日时分秒的时间  如2017-06-02 12:12:12
    getDateFormatAll: function (date) {
        var year = date.getFullYear();
        var mon = date.getMonth() + 1;
        var day = date.getDate();
        var hours = date.getHours(); //获取系统时，
        var minutes = date.getMinutes(); //分
        var seconds = date.getSeconds(); //秒
        var s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day) + " " + hours + ":" + minutes + ":" + seconds;
        return s;
    },
    /**
     * 根据传入日期 判断当前用户星座
     * data 指定的数据
     */
    getConstellationByBitrhday: function (data) {
        debugger;
        var m = data.getMonth() + 1;
        var d = data.getDate();
        return "魔羯座水瓶座双鱼座白羊座金牛座双子座巨蟹座狮子座处女座天秤座天蝎座射手座魔羯座".substr(m * 3 - (d < "102223444433".charAt(m - 1) - -19) * 3, 3);
    },
    /**
     * 根据传入日期 判断当前用户年龄
     * data 指定的数据
     */
    getCurrentAgeByBitrhday: function (data) {
        var y = data.getFullYear();
        return new Date().getFullYear() - y + 1;
    },

    getZodiacByBitrhday: function (data) {
        var arr = new Array("猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗")
        var zodiac = arr[(parseInt(data.getFullYear()) + 9) % 12];
        return zodiac;
    },
    
    /**
     * 将input - file的图片显示到对应的Img标签中
     * 
     * 例子：
     * <img src="def-head.png" width="80px" height="80px" id="img1">
     * <input type="file" onchange="common.changeInputFileToImg(this,'img1');"/>
     * 
     * 
     * inputFileObj input文件输入框的对象 = this
     * imgId 显示的img标签的id
     */
    changeInputFileToImg : function(inputFileObj,imgId){
    	var file = inputFileObj.files[0];
    	
    	var url = null;  
        if (window.createObjcectURL != undefined) {  
            url = window.createOjcectURL(file);  
        } else if (window.URL != undefined) {  
            url = window.URL.createObjectURL(file);  
        } else if (window.webkitURL != undefined) {  
            url = window.webkitURL.createObjectURL(file);  
        }  
        
    	$("#" + imgId).prop("src", url);
    },
    
    //阻止事件冒泡函数 
	stopBubble:function(e) { 
		if (e && e.stopPropagation) {
			e.stopPropagation()
		} else {
			window.event.cancelBubble=true 
		}
	},
    
    
}