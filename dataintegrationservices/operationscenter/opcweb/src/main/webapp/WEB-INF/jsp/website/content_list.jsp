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
                                    <input type="text" class="form-control" id="inputSearchId" placeholder="请输入域名进行搜索">
                                </div>
                                <!-- <label class="control-label col-sm-2" for="txt_search_modifyTime">最近修改时间</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="txt_search_modifyTime">
                                </div> -->
                                <div class="col-sm-2" style="text-align:left;">
                                    <button type="button" style="margin-left:50px" id="btn_query"
                                            class="btn btn-primary" onclick="content_list.tableList();">查询
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button authority="websiteContentSave_51_3" id="btn_add" type="button" class="btn btn-default" onclick="content_list.addBefore();">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                    <button authority="websiteContentUpdate_52_3" id="btn_edit" type="button" class="btn btn-default" onclick="content_list.updateBefore();" >
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>编辑
                    </button>
                    <button authority="websiteContentDelete_53_3" id="btn_delete" type="button" class="btn btn-default" onclick="content_list.dels();">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                    
                    
                    
                </div>
                
                <!-- 列表 -->
                <table id="websiteContent">
                	<thead>
				        <tr>      
				            <th data-checkbox="true"><input type="checkbox" /></th>
				            <th data-field="websiteName" >网站名称</th>
				            <th data-field="moduleName" >模块名称</th>
				            <th data-formatter="content_list.renderContentType" >内容类型</th>
				            <th data-formatter="content_list.renderContent" >内容</th>
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
<div class="modal fade" id="modal2WebsiteContent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">名称</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="form2WebsiteContent" style="margin: 20px;">
                <input type="hidden" name="id" id="idInputId"/>
                	<div class="form-group">
                        <label>网站名称</label>
                        <input type="text" class="form-control"
                               name="websiteName" id="add_websiteName"
                               placeholder="请输入网站名称" 
                               data-bv-notempty data-bv-notempty-message="网站名称不能为空"/>
                    </div>
                    <div class="form-group">
                        <label>域名</label>
                        <input type="text" class="form-control"
                               id="add_moduleName" name="moduleName"
                               placeholder="请输入域名" 
                               data-bv-notempty data-bv-notempty-message="域名不能为空"/>
                    </div>
                    <div class="form-group" >
                        <label>文本类型</label>
                        <input type="radio" name="contentType"  size="20" value="1" onclick = "content_list.selectType('1')">文字
                    	<input type="radio" name="contentType"  size="20" value="2" onclick = "content_list.selectType('2')">图片
                    	<input type="radio" name="contentType"  size="20" value="3" onclick = "content_list.selectType('3')">富文本
                    </div>
                    <div class="form-group" id = "type1" >
                        <label>内容</label>
                        <input type="text" class="form-control"
                               id="add_contentName1" name="content1"
                               placeholder="请输入内容" />
                    </div>
                   <div class="form-group" id = "type2" style = "display:none">
                        <label>内容</label>
                       <input type="file" id="up" name="content2"/>
                    </div>
                    <div class="form-group" id = "type3" style = "display:none">
                        <label>内容</label>
                        <div id="editor"  style="width:300px;height:300px;"></div>
                    </div>
                    <div class="form-group">
                        <label>位置选择器</label>
                        <input type="text" class="form-control"
                               id="add_positionSelector" name="positionSelector"
                               placeholder="请输入域名" 
                               data-bv-notempty data-bv-notempty-message="域名不能为空"/>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <input type="text" class="form-control"
                               name="remarks" id="add_remarks"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit1" class="btn btn-primary" onclick="content_list.add();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<script src="${path}/resource/js/plugins/ueditor/ueditor.config.js"></script>
<script src="${path}/resource/js/plugins/ueditor/ueditor.all.min.js"></script>
<script src="${path}/resource/js/service/website/content_list.js"></script>
