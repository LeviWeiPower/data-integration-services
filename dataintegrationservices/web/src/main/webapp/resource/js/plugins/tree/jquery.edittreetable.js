/*!
 * bootstrap-treetable - jQuery plugin for bootstrapview treetable
 *
 * Copyright (c) 2007-2015 songhlc
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 *
 * Project home:
 *   https://github.com/songhlc/jquery.edittreetable
 *
 * Version:  1.0.0
 *
 */
(function($){
    $.fn.bstreetable = function(options){
        $window = window;
        var element = this;
        var $container;
        var settings = {
            container:window,
            data:[],
            extfield:[],//{title:"列名",key:"",type:"input"} input表示是输入框
            nodeaddEnable:true,
            maxlevel:4,
            nodeaddcheck: function(id,callback){callback();},
            /*addCallback需返回新增的id*/
            nodeaddCallback:function(data,callback){},
            noderemoveCallback:function(data,callback){},
            nodeupdateCallback:function(data,callback){},
            customalert:function(msg){
                if(window.Common){
                    Common.toastr().error("",msg);
                }
                else {
                    alert(msg)
                }
            },
            customconfirm:function(msg,callback){
                if(window.Common){
                    var dialog = Common.alertDialog({
                        title: '删除操作',
                        content: '确认删除？',
                        okValue: '确 定',
                        ok: function () {
                            callback();
                        },
                        cancelValue: '取消',
                        cancel: function () {
                        }
                    });
                    dialog.showModal();
                } else {
                    if(confirm(msg)){
                        callback();
                    }
                }

            },
            text:{
                NodeDeleteText:"确认删除?"
            }
        };
        var TREENODECACHE = "treenode";
        var language ={};
        language.addchild = "添加子分类";
        if(options) {
            $.extend(settings, options);
        }
        /* Cache container as jQuery as object. */
        $container = (settings.container === undefined ||
        settings.container === window) ? $window : $(settings.container);
        /*render data*/
        var dom_addFirstLevel = $("<div class='tt-operation m-b-sm'></div>").append($("<button class='btn btn-primary btn-sm j-addClass'><i class='fa fa-level-down'></i>&nbsp;添加分类</button>"));
        var dom_table = $("<div class='tt-body'></div>");
        var dom_header = $("<div class='tt-header'></div>");
        /*renderHeader*/
        renderHeader(dom_header);
        element.html('').append(dom_addFirstLevel).append(dom_header);
        var treeData = {};
        /*render firstlevel tree*/
        for(var i=0;i<settings.data.length;i++){
            var row = settings.data[i];
            //render first level row while row.pid equals 0 or null or undefined
            if(!row.pid){
                generateTreeNode(dom_table,row,1);
                treeData[row.id] = row;
            }

        }

        element.append(dom_table);
        /*delegate click event*/
        element.delegate(".j-expend","click",function(event){
            //点击输入框不触发展开式事件
            if(event.target.classList[0]=="fa"){
                var treenode = treeData[$(this).attr('data-id')];
                toggleicon($(this));
                /*如果数据已经加载过，则只切换显示状态*/
                if($(this).parent().attr('data-loaded')){
                    toggleExpendStatus($(this),treenode);
                }
                else{
                    loadNode($(this),treenode);
                }
            }
        });
        element.delegate(".j-addClass","click",function(){
            var curElement = $(".tt-body");
            var row = {id:"",name:"",pid:0};
            var curLevel = 1;
            generateTreeNode(curElement,row,curLevel,true);
        });
        /*delegate remove event*/
        element.delegate(".j-remove","click",function(event){
            //TODO:需要判断是否存在子节点，存在则不允许删除
            var parentDom = $(this).parents(".class-level-ul");
            var isRemoveAble = false;
            if(parentDom.attr("data-loaded")=="true"){
                if(parentDom.parent().find(".class-level").length>0){
                    settings.customalert("存在下级节点，不允许删除！");
                    return;
                }
                else{
                    isRemoveAble = true;
                }
            }
            else{
                //如果是新增加的节点则设置成可删除否则需要展开再删除
                if(parentDom.attr("data-id")){
                    var existChild = false;
                    for(var i=0;i<settings.data.length;i++){
                        if(settings.data[i].pid==parentDom.attr("data-id")){
                            existChild = true;
                            break;
                        }
                    }
                    if(existChild){
                        settings.customalert("可能存在下级节点,请确认后重试！");
                        return;
                    }
                    else{
                        isRemoveAble = true;
                    }
                }
                else{
                    isRemoveAble = true;
                }
            }
            if(isRemoveAble){
                var that = $(this);
                //删除确认
                settings.customconfirm(settings.text.NodeDeleteText,function(){
                    /*trigger remove callback*/
                    settings.noderemoveCallback(that.parents(".class-level-ul").attr("data-id"),function(){
                        that.parents(".class-level-ul").parent().remove();
                    });
                })
            }
        });
        /*delegate addchild event*/
        element.delegate(".j-addChild","click",function(){
            var curElement = $(this).closest(".class-level");
            var requiredInput = curElement.find(".form-control*[required]");
            var hasError = false;
            requiredInput.each(function(){
                if($(this).val()==""){
                    $(this).parent().addClass("has-error");
                    hasError = true;
                }
            });
            if(!hasError){
                var pid = curElement.find(".j-expend").attr("data-id");
                var that = this;
                settings.nodeaddcheck(pid,function(){
                    var curLevel = $(that).parents(".class-level-ul").attr("data-level")-0+1;
                    var row = {id:"",name:"",pid:pid};
                    generateTreeNode(curElement,row,curLevel);
                })
            }

        });
        /*焦点事件*/
        element.delegate(".form-control","focus",function(){
            //在blur事件里如果输入内容为空会添加has-error样式
            $(this).parent().removeClass("has-error");
        });
        /*delegate lose focus event*/
        element.delegate(".form-control","blur",function(){
            var curElement = $(this);
            var data = {};
            /*代码里用了太多的parent的方式需要重构一下*/
            data.id = curElement.parent().parent().attr("data-id");
            var parentUl = curElement.closest(".class-level-ul");
            data.pid = parentUl.attr("data-pid");
            data.innercode = parentUl.attr("data-innercode");
            data.pinnercode = curElement.parents(".class-level-"+(parentUl.attr("data-level")-1)).children("ul").attr("data-innercode");
            parentUl.find(".form-control").each(function(){
                data[$(this).attr("name")]=$(this).val();
            });
            if(!data.id&&!curElement.attr("data-oldval")){
                console.log("add node");
                settings.nodeaddCallback(data,function(_data){
                    if(_data){
                        curElement.parent().attr("data-id",_data.id);
                        curElement.parent().parent().attr("data-id",_data.id);
                        curElement.parent().parent().attr("data-innercode",_data.innercode);
                        curElement.attr("data-oldval",curElement.val());
                    }
                });
            }
            else if(curElement.attr("data-oldval")!=curElement.val()){
                console.log("update node");
                settings.nodeupdateCallback(data,function(){
                    curElement.attr("data-oldval",curElement.val());
                });

            }
        });
        /*渲染表头*/
        function renderHeader(_dom_header){
            var dom_row = $('<div></div>');
            dom_row.append($("<span class='maintitle'></span>").text(settings.maintitle));
            dom_row.append($("<span></span>"));
            //render extfield
            for(var j=0;j<settings.extfield.length;j++){
                var column = settings.extfield[j];
                $("<span></span>").css("min-width","166px").text(column.title).appendTo(dom_row);
            }
            dom_row.append($("<span class='textalign-center'>操作</span>"));
            _dom_header.append(dom_row);
        }
        //动态生成扩展字段
        function generateColumn(row,extfield){
            var generatedCol;
            switch(extfield.type){
                case "input":generatedCol=$("<input type='text' class='form-control input-sm'/>").val(row[extfield.key]).attr("data-oldval",row[extfield.key]).attr("name",extfield.key);break;
                default:generatedCol=$("<span></span>").text(row[extfield.key]);break;
            }
            return generatedCol;
        }
        function toggleicon(toggleElement){
            var _element = toggleElement.find(".fa");
            if(_element.hasClass("fa-plus")){
                _element.removeClass("fa-plus").addClass("fa-minus");
                toggleElement.parent().addClass("selected");
            }else{
                _element.removeClass("fa-minus").addClass("fa-plus");
                toggleElement.parent().removeClass("selected")
            }
        }
        function toggleExpendStatus(curElement){
            if(curElement.find(".fa-minus").length>0){
                curElement.parent().parent().find(".class-level").removeClass("rowhidden");
            }
            else{
                curElement.parent().parent().find(".class-level").addClass("rowhidden");
            }

        }
        function collapseNode(){

        }
        /*展开节点*/
        function expendNode(){

        }
        /*加载子节点*/
        function loadNode(loadElement,parentNode){
            var curElement = loadElement.parent().parent();
            var curLevel = loadElement.parent().attr("data-level")-0+1;
            //TODO:将已经加载过的数据从list中删除，减少循环次数
            if(parentNode&&parentNode.id){
                for(var i=0;i<settings.data.length;i++){
                    var row = settings.data[i];
                    //render first level row while row.pid equals 0 or null or undefined
                    if(row.pid==parentNode.id){
                        generateTreeNode(curElement,row,curLevel);
                        //cache treenode
                        treeData[row.id] = row;
                    }
                }
            }
            loadElement.parent().attr('data-loaded',true);

        }
        /*初始化需要生成的下级节点的 params:
         curElement当前节点，
         row对应行数据，
         curLevel以及当前层级*/
        function generateTreeNode(curElement,row,curLevel,isPrepend){
            var dom_row = $('<div class="class-level class-level-'+curLevel+'"></div>');
            var dom_ul =$('<ul class="class-level-ul"></ul>');
            dom_ul.attr("data-pid",row.pid).attr("data-level",curLevel).attr("data-id",row.id);
            row.innercode&&dom_ul.attr("data-innercode",row.innercode);
            if(curLevel-0>=settings.maxlevel){
                $('<li class="j-expend"></li>').append('<label class="fa p-xs"></label>').append($("<input type='text' class='form-control input-sm' required/>").attr("data-oldval",row['name']).val(row['name']).attr("name","name")).attr('data-id',row.id).appendTo(dom_ul);
                dom_ul.attr("data-loaded",true);
            }
            else{
                $('<li class="j-expend"></li>').append('<label class="fa fa-plus p-xs"></label>').append($("<input type='text' class='form-control input-sm' required/>").attr("data-oldval",row['name']).val(row['name']).attr("name","name")).attr('data-id',row.id).appendTo(dom_ul);
            }

            if(settings.nodeaddEnable){
                if(curLevel-0>=settings.maxlevel){
                    $("<li></li>").attr("data-id",row.id).appendTo(dom_ul);
                }
                else{
                    $("<li></li>").append($('<button class="btn btn-outline btn-sm j-addChild"><i class="fa fa-plus"></i>'+language.addchild +'</button>').attr("data-id",row.id)).appendTo(dom_ul);
                }

            }
            for(var j=0;j<settings.extfield.length;j++){
                var colrender = settings.extfield[j];
                var coltemplate = generateColumn(row,colrender);
                $('<li></li>').attr("data-id",row.id).html(coltemplate).appendTo(dom_ul);
            }
            dom_ul.append($("<li><i class='fa fa-remove j-remove'></i></li>"));
            dom_row.append(dom_ul);
            if(isPrepend){
                curElement.prepend(dom_row);
            }
            else{
                curElement.append(dom_row);
            }

        }
    }
})(jQuery)
