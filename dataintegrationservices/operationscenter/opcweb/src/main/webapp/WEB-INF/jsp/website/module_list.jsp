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
                                    <input type="text" class="form-control" id="inputSearchId" placeholder="请输入模块名称进行搜索">
                                </div>
                                <!-- <label class="control-label col-sm-2" for="txt_search_modifyTime">最近修改时间</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="txt_search_modifyTime">
                                </div> -->
                                <div class="col-sm-2" style="text-align:left;">
                                    <button type="button" style="margin-left:50px" id="btn_query"
                                            class="btn btn-primary" onclick="module_list.tableList();">查询
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button authority="websiteModuleSave_41_3" id="btn_add" type="button" class="btn btn-default" onclick="module_list.addBefore();">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                    <button authority="websiteModuleUpdate_42_3" id="btn_edit" type="button" class="btn btn-default" onclick="module_list.updateBefore();" >
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>编辑
                    </button>
                    <button authority="websiteModuleDelete_43_3" id="btn_delete" type="button" class="btn btn-default" onclick="module_list.dels();">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                    
                    
                    
                </div>
                
                <!-- 列表 -->
                <table id="websitemodule">
                	<thead>
				        <tr>      
				            <th data-checkbox="true"><input type="checkbox" /></th>
				            <th data-field="websiteName" >网站名称</th>
				            <th data-field="moduleName">模块名称</th>
				            <th data-field="moduleLink">模块链接</th>
                            <th data-field="positionSelector">位置选择器</th>
				            <th data-field="remarks">备注</th>
				        </tr>
				    </thead>
				   
                </table>
                
            </div>
        </div>
    </div>
</div>

<!-- 添加 -->
<div class="modal fade" id="modal2Systemmodule" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">名称</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="form2Systemmodule" style="margin: 20px;">
                   
                    <div class="form-group">
                        <label>网站名称</label>
                        <input type="text" class="form-control"
                               name="websiteName" id="txt_websiteName"
                               placeholder="请输入网站名称" data-bv-notempty data-bv-notempty-message="网站名称不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='12'
                               data-bv-stringLength-message="网站名称长度必须在1到12位之间"/>
                    </div>
                    <div class="form-group">
                        <label>模块名称</label>
                        <input type="text" class="form-control"
                               name="moduleName" id="txt_moduleName"
                               placeholder="请输入模块名称" data-bv-notempty data-bv-notempty-message="模块名称不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='12'
                               data-bv-stringLength-message="模块名称长度必须在1到12位之间"/>
                    </div>
                    <div class="form-group">
                        <label>模块链接</label>
                        <input type="text" class="form-control"
                               name="moduleLink" id="txt_moduleLink"
                               placeholder="请输入模块链接" data-bv-notempty data-bv-notempty-message="模块链接不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='30'
                               data-bv-stringLength-message="用模块链接长度必须在1到30位之间"/>
                    </div>
                    <div class="form-group">
                        <label>位置选择器</label>
                        <input type="text" class="form-control"
                               name="positionSelector" id="txt_positionSelector"
                               placeholder="请输入位置选择器" data-bv-notempty data-bv-notempty-message="位置选择器不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='30'
                               data-bv-stringLength-message="位置选择器长度必须在1到30位之间"/>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <input type="text" class="form-control"
                               name="remarks" id="text_remarks"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit1" class="btn btn-primary" onclick="module_list.add();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 编辑 -->
<div class="modal fade" id="updatemoduleFrameId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalName2moduleList">编辑</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="updateform2modulelist" style="margin: 20px;">
                    <input type="hidden" name="id" id="idInputId"/>
                    
                    <div class="form-group">
                        <label>网站名称</label>
                        <input type="text" class="form-control"
                               name="websiteName" id="txt_websiteName"
                               placeholder="请输入网站名称" data-bv-notempty data-bv-notempty-message="网站名称不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='12'
                               data-bv-stringLength-message="网站名称长度必须在1到12位之间"/>
                    </div>
                    <div class="form-group">
                        <label>模块名称</label>
                        <input type="text" class="form-control"
                               name="moduleName" id="txt_moduleName"
                               placeholder="请输入模块名称" data-bv-notempty data-bv-notempty-message="模块名称不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='12'
                               data-bv-stringLength-message="模块名称长度必须在1到12位之间"/>
                    </div>
                    <div class="form-group">
                        <label>模块链接</label>
                        <input type="text" class="form-control"
                               name="moduleLink" id="txt_moduleLink"
                               placeholder="请输入模块链接" data-bv-notempty data-bv-notempty-message="模块链接不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='30'
                               data-bv-stringLength-message="用模块链接长度必须在1到30位之间"/>
                    </div>
                    <div class="form-group">
                        <label>位置选择器</label>
                        <input type="text" class="form-control"
                               name="positionSelector" id="txt_positionSelector"
                               placeholder="请输入位置选择器" data-bv-notempty data-bv-notempty-message="位置选择器不能为空"
                               data-bv-stringLength="true" data-bv-stringLength-min='1' data-bv-stringLength-max='30'
                               data-bv-stringLength-message="位置选择器长度必须在1到30位之间"/>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <input type="text" class="form-control"
                               name="remarks" id="text_remarks"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="module_list.update();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<script src="${path}/resource/js/service/website/module_list.js"></script>
