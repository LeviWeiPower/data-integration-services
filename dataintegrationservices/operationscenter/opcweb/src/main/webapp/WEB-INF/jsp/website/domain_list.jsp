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
                                            class="btn btn-primary" onclick="domain_list.tableList();">查询
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button authority="websiteDomainSave_36_3" id="btn_add" type="button" class="btn btn-default" onclick="domain_list.addBefore();">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>
                    <button authority="websiteDomainUpdate_37_3" id="btn_edit" type="button" class="btn btn-default" onclick="domain_list.updateBefore();" >
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>编辑
                    </button>
                    <button authority="websiteDomainDelete_38_3" id="btn_delete" type="button" class="btn btn-default" onclick="domain_list.dels();">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                    
                    
                    
                </div>
                
                <!-- 列表 -->
                <table id="websitedomain">
                	<thead>
				        <tr>      
				            <th data-checkbox="true"><input type="checkbox" /></th>
				            <th data-formatter="domain_list.renderLogo">logo</th>
				            <th data-field="websiteName" >网站名称</th>
				            <th data-field="domainName">域名</th>
				            <th data-field="remarks">备注</th>
				        </tr>
				    </thead>
				   
                </table>
                
            </div>
        </div>
    </div>
</div>

<!-- 添加 -->
<div class="modal fade" id="modal2WebsiteDomain" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">名称</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="form2WebsiteDomain" style="margin: 20px;">
                   <div class="form-group">
                        <label>logo</label>
                        <div style=" height:0px; overflow:hidden; position:absolute;">  
						<input type="file" id="up" onchange='domain_list.viewImage(this,"preview")'/></div>  
                        <img src = "${path}/resource/img/service/website/domain/noPicture.png" id="preview" style="width:200px;height:200px;margin-left:80px;display:block" onclick="document.getElementById('up').click();"  />
                    </div>
                    
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
                               id="add_domainName" name="domainName"
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
                <button type="submit" id="btn_submit1" class="btn btn-primary" onclick="domain_list.add();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 编辑 -->
<div class="modal fade" id="updateDomainFrameId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalName2DomainList">编辑</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="updateform2DomainList" style="margin: 20px;">
                    <input type="hidden" name="id" id="idInputId"/>
                    <div class="form-group">
                        <label>logo</label>
                        <div style=" height:0px; overflow:hidden; position:absolute;">  
						<input type="file" id="up2" onchange='domain_list.viewImage(this,"updateView")'/></div>  
                        <img src = "${path}/resource/img/service/website/domain/noPicture.png" id="updateView" style="width:200px;height:200px;margin-left:80px;display:block" onclick="document.getElementById('up2').click();"  />
                        <input type="button" class="form-control"
                               name="logo" id="update_logo" style = "display:none"/>
                               <input type="button" class="form-control"
                               name="id" id="update_id" style = "display:none"/>
                    </div>
                    
                    <div class="form-group">
                        <label>网站名称</label>
                        <input type="text" class="form-control"
                               name="websiteName" id="update_websiteName"
                               placeholder="请输入网站名称" 
                               data-bv-notempty data-bv-notempty-message="网站名称不能为空"/>
                    </div>
                    <div class="form-group">
                        <label>域名</label>
                        <input type="text" class="form-control"
                               id="update_domainName" name="domainName"
                               placeholder="请输入域名" 
                               data-bv-notempty data-bv-notempty-message="域名不能为空"/>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <input type="text" class="form-control"
                               name="remarks" id="update_remarks"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="domain_list.update();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<script src="${path}/resource/js/service/website/domain_list.js"></script>
