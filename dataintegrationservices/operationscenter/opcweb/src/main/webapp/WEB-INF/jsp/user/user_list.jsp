<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!-- 正文 -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h4 class="page-header">面包屑--后续实现</h4>
        </div>
        <div class="col-lg-12">
            <div class="panel-body" style="padding-bottom:0px;">
                <div class="panel panel-default">
                    <div class="panel-heading">查询条件</div>
                    <div class="panel-body">
                        <form id="formSearch" class="form-horizontal">
                            <div class="form-group" style="margin-top:10px">
                               <!--  <label class="control-label col-sm-2" for="txt_search_role">角色</label> -->
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="inputSearchId" placeholder="请输入昵称/账号进行搜索">
                                </div>
                                <!-- <label class="control-label col-sm-2" for="txt_search_modifyTime">最近修改时间</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="txt_search_modifyTime">
                                </div> -->
                                <div class="col-sm-2" style="text-align:left;">
                                    <button type="button" style="margin-left:50px" id="btn_query"
                                            class="btn btn-primary" onclick="user_list.tableList();">查询
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button authority="userSave_16_3" id="btn_add" type="button" class="btn btn-default" onclick="user_list.addBefore();">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                    <button authority="userUpdateuser_17_1" id="btn_edit" type="button" class="btn btn-default" onclick="user_list.updateBefore();" >
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>编辑
                    </button>
                    <button authority="userDeletes_18_3" id="btn_delete" type="button" class="btn btn-default" onclick="user_list.dels();">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                    
                    
                    
                </div>
                
                <!-- 列表 -->
                <table id="tb2systemUser">
                	<thead>
				        <tr>      
				            <th data-checkbox="true"><input type="checkbox" /></th>
				            <!-- <th data-formatter="user_list.renderAccount" >账号</th>
				            <th data-formatter="user_list.renderNickname">昵称</th> -->
				             <th data-field="account" >账号</th>
				            <th data-field="nickname">昵称</th>
				            <!-- <th data-field="email">邮箱</th>
                            <th data-field="phone">手机号</th> -->
				            <th data-formatter="user_list.renderRoleName">角色</th>
				            <!-- <th data-field="lastLoginIp">最近登录IP</th> -->
				            <!-- <th data-formatter="user_list.renderLastLoginTime">最近登录时间</th> -->       
				            <!-- <th data-field="status" >状态</th> -->
				            <th data-formatter="user_list.renderStatus">状态</th>
                            <th data-formatter="user_list.renderOperate">操作</th>
				        </tr>
				    </thead>
				   
                </table>
                
            </div>
        </div>
    </div>
</div>

<!-- 添加 -->
<div class="modal fade" id="modal2SystemUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">名称</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="form2SystemUser" style="margin: 20px;">
                   
                    <div class="form-group">
                        <label>昵称</label>
                        <input type="text" class="form-control"
                               name="nickname" id="txt_userNameUpdate"
                               placeholder="请输入昵称" data-bv-notempty data-bv-notempty-message="昵称不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='12'
                               data-bv-stringLength-message="昵称长度必须在1到12位之间"/>
                    </div>
                    
                    <div class="form-group">
                        <label>角色</label>
                        <select id="education" class="js-example-data-array form-control " name="roleId">
                            <%--<option value="3620194" selected="selected">请选择</option>--%>
                        </select>
                    </div>
                    
                    <div class="form-group">
                    
                        <label>账号</label>
                        <input type="text" class="form-control"
                               name="account" id="txt_account"
                               placeholder="请输入账号" data-bv-notempty data-bv-notempty-message="账号不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='6' data-bv-stringLength-max='18'
                               data-bv-stringLength-message="账号长度必须在6到18位之间"
                               data-bv-regexp="true" data-bv-regexp-regexp="^[a-zA-Z0-9_]+$"
                               data-bv-regexp-message="账号只能包含大写、小写、数字和下划线"
                               data-bv-remote="true"
                               data-bv-remote-url="${path }/user/isExists"
							   data-bv-remote-type="POST"
							   data-bv-remote-message="账号已存在"
							   data-bv-remote-delay ="1000"
                        />
						
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="password" class="form-control"
                               name="password" id="txt_password"
                               placeholder="请输入密码" data-bv-notempty data-bv-notempty-message="密码不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='6' data-bv-stringLength-max='18'
                               data-bv-stringLength-message="密码长度必须在6到18位之间"
                               data-bv-regexp="true" data-bv-regexp-regexp="^[a-zA-Z0-9_]+$"
                               data-bv-regexp-message="账号只能包含大写、小写、数字和下划线"
                               
                        />
                    </div>
                    <div class="form-group">
                        <label>密码确定</label>
                        <input type="password" class="form-control"
                               id="txt_confirmPassword" name="confirmPassword"
                               placeholder="请再次确认密码"
                            	data-bv-identical="true"
								data-bv-identical-field="password"
								data-bv-identical-message="两次密码输入不一致"
                            />
                    </div>
                    <!-- <div class="form-group">
                        <label>邮箱</label>
                        <input type="text" class="form-control"
                               name="email" id="txt_email"
                               placeholder="请输入邮箱" data-bv-notempty data-bv-notempty-message="邮箱不能为空"
                               data-bv-validators="true" data-bv-emailAddress data-bv-emailAddress-message="邮箱地址格式有误"
                        />
                    </div>
                    <div class="form-group">
                        <label>电话</label>
                        <input type="text" class="form-control"
                               name="phone" id="txt_phone"
                               placeholder="请输入电话号码" data-bv-notempty data-bv-notempty-message="电话号码不能为空"

                        />
                    </div> -->
                    
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit1" class="btn btn-primary" onclick="user_list.add();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 编辑 -->
<div class="modal fade" id="updateUserFrameId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalName2UserList">编辑</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="updateform2Userlist" style="margin: 20px;">
                    <input type="hidden" name="id" id="idInputId"/>
                   	
                    <div class="form-group">
                        <label>昵称</label>
                        <input type="text" class="form-control"
                               name="nickname" id="txt_userName"
                               placeholder="请输入昵称" data-bv-notempty data-bv-notempty-message="昵称不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='12'
                               data-bv-stringLength-message="昵称长度必须在1到12位之间"/>
                    </div>
                    
                    <div class="form-group">
                        <label>角色</label>
                        <select id="education2" class="js-example-data-array form-control " name="roleId">
                            <%--<option value="3620194" selected="selected">请选择</option>--%>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>账号</label>
                        <input type="text" class="form-control"
                               name="account" id="txt_account2"
                        />
						
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="password" class="form-control"
                               name="password" id="txt_passwordUpdate"
                               placeholder="请输入密码" data-bv-notempty data-bv-notempty-message="密码不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='6' data-bv-stringLength-max='18'
                               data-bv-stringLength-message="密码长度必须在6到18位之间"
                               data-bv-regexp="true" data-bv-regexp-regexp="^[a-zA-Z0-9_]+$"
                               data-bv-regexp-message="账号只能包含大写、小写、数字和下划线"
                               
                        />
                    </div>
                    <div class="form-group">
                        <label>密码确定</label>
                        <input type="password" class="form-control"
                               id="txt_confirmPassword2" name="confirmPassword"
                               placeholder="请再次确认密码"
                            	data-bv-identical="true"
								data-bv-identical-field="password"
								data-bv-identical-message="两次密码输入不一致"
                            />
                    </div>
                    <!-- <div class="form-group">
                        <label>邮箱</label>
                        <input type="text" class="form-control"
                               name="email" id="txt_email"
                               placeholder="请输入邮箱" data-bv-notempty data-bv-notempty-message="邮箱不能为空"
                               data-bv-validators="true" data-bv-emailAddress data-bv-emailAddress-message="邮箱地址格式有误"
                        />
                    </div>
                    <div class="form-group">
                        <label>电话</label>
                        <input type="text" class="form-control"
                               name="phone" id="txt_phone"
                               placeholder="请输入电话号码" data-bv-notempty data-bv-notempty-message="电话号码不能为空"

                        />
                    </div> -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="user_list.update();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<script src="${path}/resource/js/service/user/user_list.js"></script>

