<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="div-table-padding-top" >
   	
   	<!-- 操作 -->
   	<div class="btn-group div-inline" >
	                	
       <!-- 添加 -->
       <button id="btn_add" type="button" class="btn btn-default"  
       			onclick='tool.ajaxloadPage("/crawler/addCrawlerPage","get",CONSTANTS.CONTENT_ID);'>
           <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增 
       </button>
       <!-- 编辑 -->
       
       <button id="btn_delete" type="button" class="btn btn-default" onclick="account_list.updateModelBefore();">
           <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span>编辑
       </button>
       
       <!-- 删除 -->
       <button id="btn_delete" type="button" class="btn btn-default" onclick="creeper_list.dels();">
           
           <span class="glyphicon glyphicon-remove" aria-hidden="true" ></span>删除
       </button>
    </div>
   
    <!-- 列表 -->
    <table id="crawlerTableListId" class="bTable table table-striped table-bordered table-hover table-condensed" border="1" 
           cellpadding="8" cellspacing="0" >
        <thead>
	        <tr>
	        	<th data-checkbox="true"><input type="checkbox"/></th>
	            <th data-field="name">网站名称</th>
	            <th data-field="url" >网址</th>
	            <th data-field="method" >请求方式</th>
	            <th data-field="dataClassify" >数据类别</th>
	            <th data-field="coding" >编码</th>
	            <th data-formatter="creeper_list.renderDataCount">数据量</th>
	            <th data-field="isStart" data-formatter="creeper_list.renderIsStart">状态</th>
	        </tr>

        </thead>
        
    </table>
</div>

<script type="text/javascript" src="${path}/resource/js/service/data/creeper_list.js"></script>		
	