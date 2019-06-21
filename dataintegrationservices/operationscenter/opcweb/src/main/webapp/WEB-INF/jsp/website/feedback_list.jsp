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
                                            class="btn btn-primary" onclick="feedback_list.tableList();">查询
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button authority="websiteFeedbackReply_47_3" id="btn_edit" type="button" class="btn btn-default" onclick="feedback_list.updateBefore();" >
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>回复
                    </button>
                    <button authority="websiteFeedbackDelete_48_3" id="btn_delete" type="button" class="btn btn-default" onclick="feedback_list.dels();">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>
                    
                    
                    
                </div>
                <!-- 列表 -->
                <table id="websitefeedback">
                	<thead>
				        <tr>      
				            <th data-checkbox="true"><input type="checkbox" /></th>
				            <th data-field="theme" >主题</th>
				            <th data-field="contactInformation">联系信息</th>
				             <th data-formatter="feedback_list.renderFeedbackTime">反馈时间</th>
				             <th data-formatter="feedback_list.renderType">反馈类型</th>
				            <th data-field="content">反馈内容</th>
				            <th data-field="reply">回复内容</th>
				            <th data-field="respondent">回复人</th>
				             <th data-formatter="feedback_list.renderResponseTime">回复时间</th>
				        </tr>
				    </thead>
				   
                </table>
                
            </div>
        </div>
    </div>
</div>
<!-- 回复 -->
<div class="modal fade" id="updatefeedbackFrameId" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalName2feedbackList">回复</h4>
            </div>
            <div class="panel-body">
                <form method="post" class="form-horizontal" role="form" id="updateform2feedbacklist" style="margin: 20px;">
                    <input type="hidden" name="id" id="idInputId"/>
                    <div class="form-group">
                        <label>回复</label>
                        <input style="width:400px;height:200px" type="text" class="form-control"
                               name="reply" id="text_reply"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" id="btn_submit" class="btn btn-primary" onclick="feedback_list.update();"> 确定</button>
            </div>
        </div>
    </div>
</div>
<script src="${path}/resource/js/service/website/feedback_list.js"></script>
