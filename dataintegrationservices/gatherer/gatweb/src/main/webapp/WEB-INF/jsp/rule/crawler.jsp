<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="div-table-padding-top div-index-center">

	<form id="crawlerFormId" class="form-horizontal" role="form" >
		
		<div class="form-group">
		 	<label class="col-sm-2 control-label">网站名称：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="name" placeholder="请输入网站名称"
						data-bv-notempty data-bv-notempty-message="网站名称不能为空"/>
			</div>         
		</div>
		
		<div class="form-group">
		 	<label class="col-sm-2 control-label">搜索网址：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="url" placeholder="请输入主网址"
						data-bv-notempty data-bv-notempty-message="主网站不能为空"/>
			</div>      
			<p class="help-block" >注：需要采集的网站数据的总位置：如手机数据，手机导航页为主网址</p>   
		</div>
		
		<div class="form-group">
		 	<label class="col-sm-2 control-label">数据层级：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="hierarchy" placeholder="请输入数据层级"
						data-bv-notempty data-bv-notempty-message="数据层级不能为空"/>
			</div>      
			<p class="help-block" >如：采集手机列表数据，主网址为0，那么列表则为1，详情则为2</p>   
		</div>
		
		<div class="form-group">
		 	<label class="col-sm-2 control-label">编码：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="coding" placeholder="请输入编码" value="UTF-8"/>
			</div>      
			<p class="help-block" >注：网站数据编码</p>   
		</div>
		
		<div class="form-group">
		 	<label class="col-sm-2 control-label">网站数据标识：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="dataClassify" placeholder="请输入网站数据标识"
						data-bv-notempty data-bv-notempty-message="网站数据标识不能为空"/>
			</div>      
			<p class="help-block" >注：要采集的数据标识：如手机信息（价格等......）</p>   
		</div>
		
		<div class="form-group">
		 	<label class="col-sm-2 control-label">请求次数/分：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="requestNum" placeholder="请输入请求次数">
			</div>      
			<p class="help-block" >注：每分钟网站请求的次数，默认每分钟60次!</p>   
		</div>
		
		<div class="form-group">
		 	<label class="col-sm-2 control-label">数据请求方式：</label>
			<div class="col-sm-10">
				<input type="radio"  name="method" value="GET" checked="checked"/>GET 
				<input type="radio" name="method" value="POST"/>POST
			</div>      
		</div>
		
	
	</form>

	<!-- 按钮操作 -->
	<div class="div-padding-top">
		
           <button type="button" class="btn btn-default" data-dismiss="modal" onclick='crawler.cancelAddCrawler();'>取消</button>
           <button type="submit" id="btn_submit1" class="btn btn-primary" onclick='crawler.add()'>下一步</button>
		
	</div>
</div>

<script type="text/javascript" src="${path}/resource/js/service/rule/crawler.js"></script>		
	

