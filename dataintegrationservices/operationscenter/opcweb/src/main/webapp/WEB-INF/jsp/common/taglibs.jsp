<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="path" value="<%=request.getContextPath() %>"/>
<!-- 采集子系统的地址 -->
<c:set var="gathererUrl" value="http://127.0.0.1:82"/>

<!-- 设置主路径 -->
<script type="text/javascript">
  var WEB_URL= "${path}";
  
</script>  

<!-- CSS -->
<!-- Bootstrap Core CSS -->
<link href="${path}/resource/css/plugins/bootstrap/bootstrap.css" rel="stylesheet">
<!-- 选择插件 -->
<link href="${path}/resource/css/plugins/bootstrap-select2/select2.css" rel="stylesheet">
<!-- MetisMenu CSS -->
<link href="${path}/resource/css/plugins/metisMenu/metisMenu.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="${path}/resource/css/service/dist/sb-admin-2.css" rel="stylesheet">
<!-- Morris Charts CSS -->
<link href="${path}/resource/css/plugins/morris/morris.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="${path}/resource/css/plugins/font-awesome/font-awesome.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${path}/resource/css/common/base.css"/>
<link rel="stylesheet" href="${path}/resource/css/common/browser.css"/>
<link href="${path}/resource/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
<link href="${path}/resource/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="${path}/resource/css/plugins/toastr/toastr.css" rel="stylesheet" type="text/css">
<link href="${path}/resource/css/plugins/select2/select2.css" rel="stylesheet" type="text/css">

<!-- jQuery -->
<script src="${path}/resource/js/plugins/jquery/jquery.js"></script>
<script type="text/javascript" src="${path}/resource/js/common/constants.js"></script>
<script type="text/javascript" src="${path}/resource/js/common/common.js"></script> 
    