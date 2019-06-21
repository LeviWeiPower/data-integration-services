<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
         
<div id="bodyDivId">    

	<!-- 正文 -->
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h4 class="page-header">面包屑--后续实现</h4>
	        </div>
	        <div class="col-lg-12">
	            <div class="panel-body" style="padding-bottom:0px;">
	               
	
	                <div id="toolbar" class="btn-group">
	                    <button authority="userAuthoritySaveuserauthority_22_3" id="btn_add" type="button" class="btn btn-default" onclick="authority_ztree.addRootNode();">
	                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增根权限
	                    </button>
	                </div>
	                <div class="form-group_test">
                        <label>权限展示</label>
                        <ul id="treeUlId" class="ztree"></ul><br/><br/><br/><br/><br/>
                    </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- 添加根权限模块 -->
	<div class="modal fade" id="modal2SystemModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	     aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">添加权限</h4>
	            </div>
	            <div class="panel-body">
	                <form method="post" class="form-horizontal" role="form" id="form2SystemPower" style="margin: 20px;">
	                    <input type="hidden" name="level" value="1"/>
	                    <input type="hidden" name="pid" id="modal2SystemModel_pid"/>
	                    
	                    <div class="form-group">
	                        <label>权限模块名称</label>
	                        <input type="text" class="form-control"
	                               name="name" id="modal2SystemModel_name"
	                               placeholder="请输入权限模块名称" data-bv-notempty data-bv-notempty-message="系统模块名称不能为空"
	                        />
	                    </div>
	                    <div class="form-group">
	                        <label>权限模块URL</label>
	                        <input type="text" class="form-control"
	                               name="uri" id="txt_systemModuleUrl"
	                               placeholder="请输入权限URL"
	                        />
	                    </div>
	                    <input type="hidden" class="form-control" id="DEPARTMENT_ID"/>
	                </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="authority_list.add();"> 确定</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- 编辑权限模块 -->
	<div class="modal fade" id="updateModal2SystemModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	     aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">编辑系统模块</h4>
	            </div>
	            <div class="panel-body">
	                <form method="post" class="form-horizontal" role="form" id="updateform2SystemPower" style="margin: 20px;">
	                    <input type="hidden" name="level" value="1"/>
	                    <input type="hidden" name="id" id="update_systemModule_id" value=""/>
	                    
	                    
	                    
	                    <div class="form-group">
	                        <label>权限模块名称</label>
	                        <input type="text" class="form-control"
	                               name="name" id="txt_systemModule"
	                               placeholder="请输入权限模块名称" data-bv-notempty data-bv-notempty-message="权限模块名称不能为空"
	                        	
	                        />
	                    </div>
	                    <div class="form-group">
	                        <label>权限模块URL</label>
	                        <input type="text" class="form-control"
	                               name="uri" id="txt_update_systemModuleUrl"
	                               placeholder="请输入权限模块URL"
	                        />
	                    </div>
	                    <input type="hidden" class="form-control" id="DEPARTMENT_ID"/>
	                </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="authority_list.update();"> 确定</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	
</div>

<link rel="stylesheet" href="${path}/resource/css/plugins/bootstrap-ztree3/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
<script src="${path}/resource/js/plugins/bootstrap-ztree3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${path}/resource/js/plugins/bootstrap-ztree3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${path}/resource/js/plugins/bootstrap-ztree3/js/jquery.ztree.exedit.js"></script>
<script src="${path}/resource/js/service/user/authority_ztree.js"></script>
<script src="${path}/resource/js/service/user/authority_list.js"></script>

