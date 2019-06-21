<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!-- 正文 -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h4 class="page-header">面包屑--后续实现-系统角色</h4>
        </div>
        <div class="col-lg-12">
            <div class="panel-body" style="padding-bottom:0px;">
                <div class="panel panel-default">
                    <div class="panel-heading">查询条件</div>
                    <div class="panel-body">
                        <form id="formSearch" class="form-horizontal">
                            <div class="form-group" style="margin-top:10px">

                                <!-- <label class="control-label col-sm-2" for="inputSearchId">角色名称</label> -->
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" placeholder="请输入搜索的角色名称..." id="inputSearchId">
                                </div>

                                <div class="col-sm-2" style="text-align:left;">
                                    <button type="button" style="margin-left:50px" id="btn_query"
                                            class="btn btn-primary" onclick="role_list.tableList();">查询
                                    </button>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button authority="userRoleSave_19_3" id="btn_add" type="button" class="btn btn-default" onclick="role_list.addBefore();">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                    <button authority="userRoleUpdate_20_3" id="btn_edit" type="button" class="btn btn-default" onclick="role_list.updateBefore();">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                    </button>
                    <button authority="userRoleDeletes_21_3" id="btn_delete" type="button" class="btn btn-default" onclick="role_list.dels();">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                </div>


                <!-- <table id="tb2RoleList"></table> -->

				<!-- 列表 -->
                <table id="tb2RoleList">
                	<thead>
				        <tr>
				            <th data-checkbox="true"><input type="checkbox" /></th>
				            <th data-field="name" >角色名称</th>
				           <!--  <th data-formatter="role_list.selectAll" >角色名称</th> -->
				            <!-- <th data-field="authorityName">权限名称</th>
				            <th data-field="authorityLevel">权限级别</th> -->
				            <th data-field="authorityIds" id="authorityId">权限Id</th>
				        </tr>
				    </thead>

                </table>

            </div>
        </div>
    </div>
</div>

<!-- 添加子模块弹出框 -->
<div class="modal fade" id="addRoleFrameId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalName2RoleList">名称</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="form2Rolelist" style="margin: 20px;">
                    <div class="form-group">
                        <label>角色名称</label>
                        <input type="text" class="form-control"
                               name="name" id="txt_roleName"
                               placeholder="请输入角色名称" data-bv-notempty data-bv-notempty-message="角色名称不能为空"
                               data-bv-remote="true"
                               data-bv-remote-url="${path }/user/role/isExists"
							   data-bv-remote-type="POST"
							   data-bv-remote-message="名称已存在"
							   data-bv-remote-delay ="1000"
                        />
                    </div>
                    <div class="form-group">
                        <label>权限模块</label></br>
                        <ul id="authorityTreeAddId" class="ztree"></ul>
                    </div>
                    
                    <input type="hidden" class="form-control" name="authorityIds" id="txt_authorityIds"/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="role_list.add();"> 确定</button>
            </div>
        </div>
    </div>
</div>

<!-- 编辑弹出框 -->
<div class="modal fade" id="updateRoleFrameId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalName2RoleList">名称</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="updateform2Rolelist" style="margin: 20px;">
                   	<!-- 更新必须要有一个Id -->
                   	<input type="hidden" name="id"/>
                    <div class="form-group">
                        <label>角色名称</label>
                        <input type="text" class="form-control"
                               name="name" id="edit_rtreeeName"
                        />
                    </div>
                    <div class="form-group">
                        <label>权限模块</label></br>
                        <ul id="authorityTreeUpdateId" class="ztree"></ul>
                    </div>
                    
                    <input type="hidden" class="form-control" name="authorityIds" id="txt_authorityUpdateIds"/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="role_list.update();"> 提交</button>
            </div>
        </div>
    </div>
</div>

<script src="${path}/resource/js/plugins/jquery/jquery.citys.js"></script>
<link rel="stylesheet" href="${path}/resource/css/plugins/bootstrap-ztree3/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
<script src="${path}/resource/js/plugins/bootstrap-ztree3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${path}/resource/js/plugins/bootstrap-ztree3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${path}/resource/js/plugins/bootstrap-ztree3/js/jquery.ztree.exedit.js"></script>
<script src="${path}/resource/js/service/user/role_ztree.js"></script>
<script src="${path}/resource/js/service/user/role_list.js"></script>



