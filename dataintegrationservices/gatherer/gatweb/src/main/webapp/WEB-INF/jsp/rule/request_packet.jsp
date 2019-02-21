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
    
	<!-- 填写表单 -->
    <form id="reqPacketFormId" class="form-horizontal">
        
        <!-- 请求包头信息 -->
        <div class="div-padding-top">
            
            <div class="form-group" style="padding-top: 20px;">
			    <span style="font-size: 20px;">请求包头信息：</span>
	            <button id="btn_delete" type="button" class="btn btn-default" onclick="request_packet.addReqHeaderInputHtml()">
            		添加
            	</button>
	        
	        </div> 
            
            <div class="div-inline" id="reqHeaderMsgDivId">
	            <div>
	            	<input type="hidden" id="reqHeaderSequenceInputId" name="requestHeaderBeans[0].sequence" />
			        
	            	<label class="col-sm-2 control-label">Key：</label>
	            	<div class="col-sm-10">
						<input type="text" class="form-control" name="requestHeaderBeans[0].headerKey" placeholder="请输入Key" >
					</div>
	            	<label class="col-sm-2 control-label">Value：</label>
	            	<div class="col-sm-10">
						<input type="text" class="form-control" name="requestHeaderBeans[0].headerValue" placeholder="请输入Value">
					</div>
	            </div>
            </div>
            
        </div>
        
        <!-- 请求参数信息 -->
        <div class="div-padding-top">
            
            <div class="form-group" style="padding-top: 20px;">
			    <span style="font-size: 20px;">请求参数信息：</span>
	            <button id="btn_delete" type="button" class="btn btn-default" onclick="request_packet.addReqParamInputHtml()">
            		添加
            	</button>
	        
	        </div> 
            
            <div class="div-inline" id="reqParamMsgDivId">
	            <div>
	            	<input type="hidden" id="requestParamSequenceInputId" name="requestParamBeans[0].sequence" />
			        
	            	<label class="col-sm-2 control-label">Key：</label>
	            	<div class="col-sm-10">
						<input type="text" class="form-control" name="requestParamBeans[0].paramKey" placeholder="请输入Key" >
					</div>
	            	<label class="col-sm-2 control-label">Value：</label>
	            	<div class="col-sm-10">
						<input type="text" class="form-control" name="requestParamBeans[0].paramValue" placeholder="请输入Value">
					</div>
	            </div>
            </div>
            
        </div>
        
        <!-- 携带的Cookie信息 -->
        <div class="div-padding-top" >
        	
            <div class="form-group" >
			    <span style="font-size: 20px;">Cookie信息：</span>
	            <button id="btn_delete" type="button" class="btn btn-default" onclick="request_packet.addCookieInputHtml()">
            		添加
            	</button>
	        
	        </div> 
            
            <div class="div-inline" id="cookieDivId">
	            <div>
	            	
	            	<input type="hidden" id="cookieSequenceInputId" name="cookieBeans[0].sequence" />
			        
	            	<label class="col-sm-2 control-label">Key：</label>
	            	<div class="col-sm-10">
						<input type="text" class="form-control" name="cookieBeans[0].cookieKey" placeholder="请输入Key" >
					</div>
	            	<label class="col-sm-2 control-label">Value：</label>
	            	<div class="col-sm-10">
						<input type="text" class="form-control" name="cookieBeans[0].cookieValue" placeholder="请输入Value">
					</div>
	            </div>
            </div>
            
        
        </div>

    </form>
    
	<!-- 按钮操作 -->
    <div class="div-padding-top">

	    <button type="button" class="btn btn-default" data-dismiss="modal" 
	    	onclick='crawler.cancelAddCrawler();' value="true">取消</button>
	    	
	    <button type="button" class="btn btn-primary" data-dismiss="modal" 
	    	onclick='request_packet.addReqMsg(this)' value="false">下一步</button>
	
    </div>

</div>
<script type="text/javascript" src="${path}/resource/js/service/rule/request_packet.js"></script>