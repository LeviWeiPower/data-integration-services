<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:set var="path" value="http://127.0.0.1:82"/>

<script type="text/javascript" src="${path}/resource/js/plugins/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${path}/resource/js/common/constants.js"></script>

<script type="text/javascript">
  var WEB_URL= "${path}";
</script>   


<!-- CSS -->
<!-- Bootstrap Core CSS -->
<link href="${path}/resource/css/plugins/bootstrap/bootstrap.css" rel="stylesheet">
<link href="${path}/resource/css/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">

<link href="${path}/resource/css/plugins/toastr/toastr.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="${path}/resource/css/common/base.css" />
<link rel="stylesheet" href="${path}/resource/css/common/browser.css"/>