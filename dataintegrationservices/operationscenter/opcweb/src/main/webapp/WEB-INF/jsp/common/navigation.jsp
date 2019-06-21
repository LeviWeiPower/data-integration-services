<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!-- 菜单栏 -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    
    <!-- 头部，主要导入用户登录内容等 -->
	<%@include file="header.jsp" %>
	
	<div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <!-- <li class="sidebar-search">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                    </div>
                </li> -->
                <!-- 首页 -->
                <li>
                    <a class="navigation" href="javascript:void(0);" id="indexAId" onclick='tool.ajaxloadPage("/main/index","get",CONSTANTS.CONTENT_ID);'><i class="fa fa-dashboard fa-fw"></i>首页</a>
                </li>
                
                <!-- 采集系统 -->
                <li>
                    <a class="navigation" href="javascript:void(0);"><i class="fa fa-leanpub fa-fw"></i>采集系统<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li authority="websiteDomainGetlistpage_39_3">
                            <a class="navigation" onclick='tool.ajaxloadPage("/gatherer/list","get",CONSTANTS.CONTENT_ID);'>采集列表</a>
                        </li>
                        <li authority="websiteModuleGetlistpage_44_3">
                            <a class="navigation" onclick='tool.ajaxloadPage("/website/module/getListPage","get",CONSTANTS.CONTENT_ID);'>采集日志</a>
                        </li>
                    </ul>
                </li>
                
                <!-- 数据清洗 -->
				<li>
                    <a class="navigation" href="javascript:void(0);"><i class="fa fa-group fa-fw"></i>数据清洗系统<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                       	<li authority="teamMemberGetlistpage_28_3">
                            <a class="navigation" onclick='tool.ajaxloadPage("/team/member/getListPage","get",CONSTANTS.CONTENT_ID);'>基础库</a>
                        </li>
                        <li authority="teamAccessGetlistpage_58_3">
                            <a class="navigation" onclick='tool.ajaxloadPage("/team/access/serverUser/getListPage","get",CONSTANTS.CONTENT_ID);'>数据规则</a>
                        </li>
                       	<li authority="teamFinanceGetlistpage_33_3">
                            <a class="navigation" onclick='tool.ajaxloadPage("/team/finance/getListPage","get",CONSTANTS.CONTENT_ID);'>待清洗数据</a>
                        </li>
                       	<li authority="teamFinanceGetlistpage_33_3">
                            <a class="navigation" onclick='tool.ajaxloadPage("/team/finance/getListPage","get",CONSTANTS.CONTENT_ID);'>结果数据</a>
                        </li>
                        
                    </ul>
                </li>
				
                <!-- 系统设置 -->
                <li>
                    <a class="navigation" href="javascript:void(0);"><i class="fa fa-wrench fa-fw"></i>系统设置<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="navigation" href="javascript:void(0);">用户管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-third-level">
                                <li authority="userGetlistpage_25_3">
                                <!-- <li> -->
                                    <a class="navigation" onclick='tool.ajaxloadPage("/user/getListPage","get",CONSTANTS.CONTENT_ID);'>系统用户</a>
                                </li>
                                <li authority="userRoleGetlistpage_27_3">
                                    <a class="navigation" onclick='tool.ajaxloadPage("/user/role/getListPage","get",CONSTANTS.CONTENT_ID);'>系统角色</a>
                                </li>
								<li authority="userAuthorityGetlistpage_26_3">
                                    <a class="navigation" onclick='tool.ajaxloadPage("/user/authority/getListPage","get",CONSTANTS.CONTENT_ID);'>系统权限</a>
                                </li>
                                
                            </ul>
                            
                        </li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>

            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>
<script src="${path}/resource/js/service/main/navigation.js"></script>