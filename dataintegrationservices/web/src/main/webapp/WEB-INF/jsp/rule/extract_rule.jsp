<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<div class="div-table-padding-top div-index-center">

	<!-- 标题显示 -->
    <div>
		<div class="div-inline div-padding-top">
	        <span href='#' class="span-title" id="websiteNameSpanId"></span>
	    </div>
	
	    <span class="span-title">-------</span>
	
	    <div class="div-inline div-padding-top">
	        <span class="span-title" id="websiteUrlSpanId"></span>
	    </div>
	</div>
		
	<!-- <div class="div-padding-top">
        <span>页面数据标识：</span> <input type="text" id="pageDataInputId"/>
        <label class="col-sm-2 control-label">页面数据标识：</label>
        <div class="col-sm-10">
			<input type="text" class="form-control" id="pageDataInputId" placeholder="请输入页面数据标识" 
							data-bv-notempty data-bv-notempty-message="页面数据标识不能为空"/>
		</div>
    </div> -->
	
	
    <div class="div-padding-top">
    	
        <label class="col-sm-2 control-label">模板地址：</label>
        <div class="col-sm-10 ">
			<input type="text" class="form-control" id="queryTemplateInputId" placeholder="请输入模板地址" style="width: 90%;display:inline;">
			<button type="button" class="btn btn-default" data-dismiss="modal"
	     		 onclick='extract_rule.getTemplatePageHtml();' value="true" style="display:inline;">确定</button>
		</div>
        
        
        
    </div>

	<!-- HTML展示区 -->
    <div class="browser" id="browser">
		
    </div>
	
	<!-- 要采集的数据展示区域 -->
    <div class="div-padding-top">

        <div class="div-padding-top">
            <span style="font-size: 20px;">以下是您数据采集模板：</span>
            <br/>
            
            <!-- 采集到的数据的标识 -->
            <div id="addDataTagId" style="display: none;">
            	<span>数据标识：</span>
            	<input id="dataTagInputId" style="width:325px;height:30px;font-size:24px; 
            			line-height:30px; text-align: center;" type="text" />
            	<button id="addDataTagButId" class="btn btn-default" onclick="extract_rule.addDataTag(this)">确定</button>	
            </div>
            
        </div>
        
        <div style="padding-top: 20px; margin: auto; text-align: center;">
			<table style="padding-top: 20px;font-size:16px; " border="1" align="center" cellpadding="8" cellspacing="0" >
				<thead>
					<tr>
						<th>数据元素</th>
						<th>模板数据</th>
						<th>数据标识</th>
						<th>操作</th>
					</tr>
				</thead>			
				
				<tbody id="dataTbodyId">
						
				</tbody>
				
			</table>
		</div>

    </div>
    
    <div class="div-padding-top">
        <!-- <button onclick='tool.ajaxloadPage("/main?name="+document.getElementById("name_next").value +"&url="+document.getElementById("url_next").value +"&dataClassify="+document.getElementById("dataClassify_next").value+"&hierarchy="+document.getElementById("hierarchy_next").value, "get", CONSTANTS.CONTENT_ID);'>取消</button>
        <button onclick="ruleJs.getElementIndex(this)">保存</button> -->
        
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick='crawler.cancelAddCrawler();'>取消</button>
        <button type="submit" id="btn_submit1" class="btn btn-primary" onclick="extract_rule.addExtractRule();"> 确定</button>
        
    </div>
</div>
<script type="text/javascript" src="${path}/resource/js/service/rule/extract_rule.js"></script>

