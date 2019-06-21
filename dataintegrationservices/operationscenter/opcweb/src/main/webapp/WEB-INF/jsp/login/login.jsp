<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="path" value="<%=request.getContextPath() %>"/>
<!-- 设置主路径 -->
<script type="text/javascript">
  var WEB_URL= "${path}";
</script>  
<html>
	<head>
	
		<link rel='icon' href='/resource/img/icon/logo.png ' type='image/x-ico' />
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="description" content="">
	    <meta name="author" content="">
	
	    <title>登录</title>
	
	    <!-- Bootstrap Core CSS -->
	    <link href="${path}/resource/css/plugins/bootstrap/bootstrap.css" rel="stylesheet">
	
	    <!-- MetisMenu CSS -->
	    <link href="${path}/resource/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
	
	    <!-- Custom CSS -->
	    <link href="${path}/resource/css/service/dist/sb-admin-2.css" rel="stylesheet">
	    <link href="${path}/resource/css/plugins/toastr/toastr.css" rel="stylesheet" type="text/css">
	
	    <!-- Custom Fonts -->
	    <link href="${path}/resource/css/plugins/font-awesome/font-awesome.css" rel="stylesheet" type="text/css">
		
		
	</head>
<body>
	<input type="hidden" value="LgJspHiddenTag"/>
	<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">请登录</h3>
                    </div>
                    <div class="panel-body">
                        <form id="loginFormId">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="账号" 
                                    	   name="account"
		                                   data-bv-notempty data-bv-notempty-message="账号不能为空"
			                               data-bv-stringLength="true" data-bv-stringLength-min='6' data-bv-stringLength-max='18'
			                               data-bv-stringLength-message="账号长度必须在6到18位之间"
			                               data-bv-regexp="true" data-bv-regexp-regexp="^[a-zA-Z0-9_]+$"
			                               data-bv-regexp-message="账号只能包含大写、小写、数字和下划线"
			                               data-bv-remote="true"
			                               data-bv-remote-url="${path }/user/isNotExists"
										   data-bv-remote-type="POST"
										   data-bv-remote-message="账号不存在"
										   data-bv-remote-delay ="10"
                                    />
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" type="password"
		                                   name="password"
		                                   placeholder="请输入密码" data-bv-notempty data-bv-notempty-message="密码不能为空"
			                               data-bv-stringLength="true" data-bv-stringLength-min='6' data-bv-stringLength-max='18'
			                               data-bv-stringLength-message="账号长度必须在6到18位之间"
			                               data-bv-regexp="true" data-bv-regexp-regexp="^[a-zA-Z0-9_]+$"
			                               data-bv-regexp-message="密码只能包含大写、小写、数字和下划线"
                                    />
                                </div>
                                <!-- <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div> -->
                                <!-- Change this to a button or input when using this as a form -->
                                <a href="javascript:void(0);" class="btn btn-lg btn-success btn-block" onclick="login.login();">登录</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
	
</body>



<!-- Bootstrap Core JavaScript -->
<script src="${path}/resource/js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${path}/resource/js/common/tool.js"></script>
<script type="text/javascript" src="${path}/resource/js/plugins/jquery/jquery.serializejson.js"></script>

<script src="${path}/resource/js/common/Ewin.js"></script>
<script src="${path}/resource/js/plugins/toastr/toastr.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script type="text/javascript" src="${path}/resource/js/common/constants.js"></script>
<script type="text/javascript" src="${path}/resource/js/common/common.js"></script> 
<!-- Validation -->
<script src="${path}/resource/js/plugins/bootstrap-validator/bootstrapValidator.js"></script>

<script src="${path}/resource/js/service/login/login.js"></script>

</html>