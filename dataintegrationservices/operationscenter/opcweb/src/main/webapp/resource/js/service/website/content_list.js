
var type = "1";
var editType = true;
var content_list = {
	
	//列表对象，用于后续操作
	listTableObj : null,
	
	//列表重置（可根据事件触发进行）
	tableList : function(){
		//搜索框的值
		var nameVal = $("#inputSearchId").val();
		//搜索的参数
		var queryParams = {
				filtrate : {//绝对值匹配
					is_delete : "N"
				},
				likeFiltrate : {//模糊搜索
					domain_name : nameVal
				},
				orderField : "update_time",
				orderingRule : "DESC",
			};
		
		//列表重新搜索，按照新的参数进行搜索
		content_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
		
	},
	renderContentType : function(value, row, index){
		var html;
		var type = row.contentType;
		if ( type == "1" ){
			html = "文字";
		}else if ( type == "2" ){
			html = "图片";
		}else {
			html = "富文本";
		}
		return html;
		
	},
	renderContent : function(value, row, index){
		var html;
		var type = row.contentType;
		var content = row.content;
		if ( type == "1" ){
			html = content;
		}else if ( type == "2" ){
			html = "图片";
		}else {
			html = content;
		}
		return html;
		
	},
	renderLogo : function(value, row, index){
		var html;
		var logo = row.logo;
        if ( logo == null || logo == "" ){
        	html ='<img src = "${path}/resource/img/service/website/domain/noPicture.png" style="width:100px;height:100px;margin-left:80px;display:block"  />';
        }else {
        	var params = {};
        	 params["imgName"] = logo;
        	var result = tool.ajax('/website/domain/getPicture','post',params);
        	html = '<img src = "'+result.result+'" style="width:100px;height:100px;margin-left:80px;display:block"  />';
        }
		return html;
		
	},
	
	//删除
	dels : function(){
		var arrSelections = content_list.listTableObj.bootstrapTable('getSelections');
		common.delByIds(arrSelections,"/website/content/deletes", "post","JSON",function(data){
			content_list.tableList();
		});
	},
	
	//显示添加弹出框
	addBefore : function(){
		$("#btn_submit1").show();
		$("#btn_submit2").hide();

		//将添加框内的数据清空
		content_list.selectType('1');
		common.clearForm("form2WebsiteContent","input","");
		editType = true;
		//显示弹出框
		$('#modal2WebsiteContent').modal();
		
	},
	
	//添加
	add : function() {
		var editId = $("#idInputId").val();
		var arr = [];
        arr.push(UE.getEditor('editor').getContent());
		$("#idInputId").val("");
		//调用验证的方法
		common.preSubmitValidator('form2WebsiteContent','bootstrapValidator',function(){
			var formData = new FormData($("#form2WebsiteContent")[0]);
			formData.append("content3",arr.join("\n"));
			formData.append("type",type);
			formData.append("editId",editId);
			var result = null;
			if ( editType ){
				result = tool.ajax('/website/content/addContent','POST',formData,'JSON',undefined,false,false,false,false);
			}else {
				result = tool.ajax('/website/content/updateContent','POST',formData,'JSON',undefined,false,false,false,false);
			}
			
			if(null != result && undefined != result){
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		content_list.tableList();
            		//关闭弹出框
            		$('#modal2WebsiteContent').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
    			//将添加框内的数据清空
    			common.clearForm("form2SystemContent","input","");
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			
			//成功的回调方法
			//content_list.readFile();
			
		});
	},
	/*//更新之前
	updateModelBefore : function() {
		$("#btn_submit2").show();
		$("#btn_submit1").hide();
		var arrSelections = domain_list.listTableObj.bootstrapTable('getSelections');
		//只能选择一条
		if (arrSelections.length <= 0 || arrSelections.length > 1) {
            toastr.warning(CONSTANTS.ONLY_RECORD_MSG);
            return;
        }
		 
		var data = arrSelections[0];
		
		//数据回显，如果出现<select>等标签是value和显示的值是不一样则进行手动返回
		common.paddingForm("form2Systemdomain","input",data);
		
		$("#txt_confirmPassword").val(data.password);
		
		//权限
		var result = tool.ajax("/system/role/getDatas","get");
		//处理成功则刷新列表
		if(CONSTANTS.HRS_OK == result.status){
			$("#education").html("");
			for (var i = 0; i < result.result.length; i++) {
				$("#education").append("<option value=\""+result.result[i].id+"\">"+result.result[i].name+"</option>");
			}
			$("#education").val(data.roleId);;
		}
		
		//用户名隐藏
		$("#txt_account").prop("readonly",true);
		
		
		//显示弹出框
		$('#modal2Systemdomain').modal();
		
	},
	
	//更新主模型
	updateModel : function(){
		//调用验证的方法
		common.preSubmitValidator('form2Systemdomain','bootstrapValidator',function(){
			
			//成功的回调方法
			var result = tool.ajaxForm('/system/domain/update','post','form2Systemdomain',true,'json');
			if(null != result && undefined != result){
            	
            	//处理成功则刷新列表
            	if(CONSTANTS.HRS_OK == result.status){
            		toastr.success(result.msg);
            		domain_list.tableList();
            		//关闭弹出框
            		$('#modal2Systemdomain').modal("hide");
            	}else{
            		//系统返回错误
            		toastr.error(result.msg);
            	}
            }else {
            	//提示错误信息
            	toastr.error(CONSTANTS.ERROR);
            }
			//将添加框内的数据清空
			common.clearForm("form2Systemdomain","input","");
		});
		
	},*/
    updateBefore : function() {
    	var arrSelections = content_list.listTableObj.bootstrapTable('getSelections');
		//只能选择一条
		if (arrSelections.length <= 0 || arrSelections.length > 1) {
            toastr.warning(CONSTANTS.ONLY_RECORD_MSG);
            return;
        }
		common.clearForm("form2WebsiteContent","input","");
		var data = arrSelections[0];
		if ( data.contentType == 3 ){
			UE.getEditor('editor').execCommand('insertHtml', data.content)
		}else if (data.contentType == 1){
			$("#add_contentName1").text(data.content);
		}
        
		
		editType = false;
		//数据回显，如果出现<select>等标签是value和显示的值是不一样则进行手动返回
		common.paddingForm("form2WebsiteContent","input",data);
		
		
		 
		
		//显示弹出框
		$('#modal2WebsiteContent').modal();

    },
    viewImage: function(file,viewName){
        var preview = document.getElementById(viewName);
        if(file.files && file.files[0]){
            //火狐下
            preview.style.display = "block";
            preview.style.width = "200px";
            preview.style.height = "200px";
            preview.src = window.URL.createObjectURL(file.files[0]);
        }else{
            //ie下，使用滤镜
            file.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = document.getElementById("localImag"); 
            //必须设置初始大小 
            localImagId.style.width = "200px"; 
            localImagId.style.height = "200px"; 
            try{ 
            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            locem("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc; 
            }catch(e){ 
            alert("您上传的图片格式不正确，请重新选择!"); 
            return false; 
            } 
            preview.style.display = 'none'; 
            document.selection.empty(); 
            } 
            return true; 
    },
    readFile:function() {
        var files = document.getElementById('up').files;
        // 如果没有选中文件，直接返回
        if (files.length === 0) {
            return;
        }
        var file = files[0];
      //判断类型是不是图片    
        if(!/image\/\w+/.test(file.type)){       
                alert("请确保文件为图像类型");     
                return false;     
        }     
        var reader = new FileReader();     
        reader.readAsDataURL(file);     
        reader.onload = function(e){  
          var image_base64=this.result.split(",")[1]; 
          var params = {};
          params["img"] = image_base64;
          params["domainName"] = $("#add_domainName").val();
          params["websiteName"] = $("#add_websiteName").val();
          params["remarks"] = $("#add_remarks").val();
          var result = tool.ajax('/website/domain/addDomain','post',params);
          if(null != result && undefined != result){
          	
          	//处理成功则刷新列表
          	if(CONSTANTS.HRS_OK == result.status){
          		toastr.success(result.msg);
          		content_list.tableList();
          		//关闭弹出框
          		$('#modal2WebsiteContent').modal("hide");
          	}else{
          		//系统返回错误
          		toastr.error(result.msg);
          	}
          }else {
          	//提示错误信息
          	toastr.error(CONSTANTS.ERROR);
          }
        }
    },
    //选择类型
    selectType : function(value){
    	if ( value == "1" ){
    		type = 1;
    		$("#type1").show();
    		$("#type2").hide();
    		$("#type3").hide();
    	}else if ( value == "2" ){
    		type = 2;
    		$("#type1").hide();
    		$("#type2").show();
    		$("#type3").hide();
    	}else {
    		type = 3;
    		$("#type1").hide();
    		$("#type2").hide();
    		$("#type3").show();
    	}
    }
}

$(function() {
	UE.getEditor('editor', {
        toolbars: [
            [
       'bold', 'italic', 'underline',  '|', 'forecolor', 'fontfamily', 'fontsize', '|',
       'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
        'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
       'simpleupload'
            ]
        ]
        ,elementPathEnabled: false//元素路径
        ,wordCount: false//字数统计
        ,enableAutoSave: false //自动保存
        ,autoSyncData: false//自动同步编辑器要提交的数据
        ,autoFloatEnabled:false//工具栏悬浮
        ,enableContextMenu:false //右键菜单
        ,lineheight:['1', '1.5','1.75','2', '3', '4', '5']//行高
        ,pasteplain:true //是否默认为纯文本粘贴
        ,catchRemoteImageEnable:false//远程图片抓取关闭
        ,imagePopup:false//图片操作的浮层开关，默认打开

    });
	var queryParams = {
			filtrate : {//绝对值匹配
				is_delete : "N"
			},
			orderField : "update_time",
			orderingRule : "DESC",
		};
	//初始化列表数据
	content_list.listTableObj = common.bootstrapTable('websiteContent', '/website/content/getPagingList','get',queryParams);
	
	//初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});

	
	common.pageInitAuthoritry();
});
