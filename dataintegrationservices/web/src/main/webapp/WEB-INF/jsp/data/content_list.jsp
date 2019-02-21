<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="div-table-padding-top" >
   	
   
    <!-- 列表 -->
    <table id="dataListTableId" class="bTable table table-striped table-bordered table-hover table-condensed" border="1" 
           cellpadding="8" cellspacing="0" >
        <thead >
	        <tr>
	            <th data-field="contentClassify">数据类别</th>
	            <th data-field="content" >数据</th>
	            <th data-field="sequence" data-formatter="content_list.renderRelevance">关联数据</th>
	        </tr>

        </thead>
        
    </table>
</div>

<script type="text/javascript" src="${path}/resource/js/service/data/content_list.js"></script>		

