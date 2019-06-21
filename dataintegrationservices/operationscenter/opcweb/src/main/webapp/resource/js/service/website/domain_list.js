var domain_list = {
		
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
		domain_list.listTableObj.bootstrapTable('refresh',{query : queryParams});
		
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
		var arrSelections = domain_list.listTableObj.bootstrapTable('getSelections');
		common.delByIds(arrSelections,"/website/domain/deletes", "post","JSON",function(data){
			domain_list.tableList();
		});
	},
	
	//显示添加弹出框
	addBefore : function(){
		$("#btn_submit1").show();
		$("#btn_submit2").hide();

		//将添加框内的数据清空
		common.clearForm("form2WebsiteDomain","input","");
		
		//显示弹出框
		$('#modal2WebsiteDomain').modal();
		
	},
	
	//添加
	add : function() {
		$("#idInputId").val("");
		//调用验证的方法
		
		common.preSubmitValidator('form2WebsiteDomain','bootstrapValidator',function(){
			
			//成功的回调方法
			domain_list.readFile();
			
			//将添加框内的数据清空
			common.clearForm("form2Systemdomain","input","");
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
    	var arrSelections = domain_list.listTableObj.bootstrapTable('getSelections');
		//只能选择一条
		if (arrSelections.length <= 0 || arrSelections.length > 1) {
            toastr.warning(CONSTANTS.ONLY_RECORD_MSG);
            return;
        }
		 
		var data = arrSelections[0];
		
		//数据回显，如果出现<select>等标签是value和显示的值是不一样则进行手动返回
		common.paddingForm("updateform2DomainList","input",data);
		var logo = $("#update_logo").val();
    	if ( !(logo == null || logo == "") ){
        	var params = {};
        	 params["imgName"] = logo;
        	var result = tool.ajax('/website/domain/getPicture','post',params);
        	$("#updateView").attr("src",result.result);
    	}
		
		
		 
		
		//显示弹出框
		$('#updateDomainFrameId').modal();

    },//更新
    update : function(){
        //调用验证的方法
        common.preSubmitValidator('updateform2DomainList','bootstrapValidator',function(){
            //成功的回调方法
        	var params = {};
        	var logo = $("#update_logo").val();
        	if ( logo == null ) logo = "";
        	params["logo"] = logo;
        	params["id"] = $("#update_id").val();
        	params["img"]="";
        	params["domainName"] = $("#update_domainName").val();
            params["websiteName"] = $("#update_websiteName").val();
            params["remarks"] = $("#update_remarks").val();
        	var files = document.getElementById('up2').files;
            // 如果没有选中文件，直接返回
            if (files.length != 0) {
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
                    params["img"] = image_base64;
                    var result = tool.ajax('/website/domain/updateDomain','post',params);
                    if(null != result && undefined != result){

                        //处理成功则刷新列表
                        if(CONSTANTS.HRS_OK == result.status){
                            toastr.success(result.msg);
                            domain_list.tableList();
                            //关闭弹出框
                            $('#updateDomainFrameId').modal("hide");
                        }else{
                            //系统返回错误
                            toastr.error(result.msg);
                        }
                    }else {
                        //提示错误信息
                        toastr.error(CONSTANTS.ERROR);
                    }

                    //将添加框内的数据清空
                    common.clearForm("updateform2DomainList","input","");
                  }
            }else{
            	var result = tool.ajax('/website/domain/updateDomain','post',params);
            if(null != result && undefined != result){

                //处理成功则刷新列表
                if(CONSTANTS.HRS_OK == result.status){
                    toastr.success(result.msg);
                    domain_list.tableList();
                    //关闭弹出框
                    $('#updateDomainFrameId').modal("hide");
                }else{
                    //系统返回错误
                    toastr.error(result.msg);
                }
            }else {
                //提示错误信息
                toastr.error(CONSTANTS.ERROR);
            }

            //将添加框内的数据清空
            common.clearForm("updateform2DomainList","input","");
            }
            
        });
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
          		domain_list.tableList();
          		//关闭弹出框
          		$('#modal2WebsiteDomain').modal("hide");
          	}else{
          		//系统返回错误
          		toastr.error(result.msg);
          	}
          }else {
          	//提示错误信息
          	toastr.error(CONSTANTS.ERROR);
          }
        }
    }
}

$(function() {

	var queryParams = {
			filtrate : {//绝对值匹配
				is_delete : "N"
			},
			orderField : "update_time",
			orderingRule : "DESC",
		};
	//初始化列表数据
	domain_list.listTableObj = common.bootstrapTable('websitedomain', '/website/domain/getPagingList','get',queryParams);
	
	//初始化验证
	$('form').bootstrapValidator({feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    }});

	domain_list.operate();
	common.pageInitAuthoritry();
	
	
});
