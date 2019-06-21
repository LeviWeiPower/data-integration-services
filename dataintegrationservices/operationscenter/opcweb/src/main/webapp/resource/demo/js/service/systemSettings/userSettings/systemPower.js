$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    //隐藏不显示的table行
    $('#tb2systemPower').bootstrapTable('hideColumn', 'systemModuleUrl');
    $('#tb2systemPower').bootstrapTable('hideColumn', 'managementModuleUrl');
    $('#tb2systemPower').bootstrapTable('hideColumn', 'moduleFunction');
});

// table 表设置
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb2systemPower').bootstrapTable({
            // url: '/main/GetDepartment',       //请求后台的URL（*）
            url: 'http://www.easy-mock.com/mock/59f6e881ee3c3f29ab2f6997/example/systemPower',       //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'systemModule',
                title: '系统模块名称'
            }, {
                field: 'managementModule',
                title: '子模块名称'
            }, {
                field: 'operation',
                title: '操作功能'
            }, {
                field: 'modifyTime',
                title: '最近更新时间'
            }, {
                field: 'systemModuleUrl',
                title: '系统模块URL'
            }, {
                field: 'managementModuleUrl',
                title: '子模块URL'
            }, {
                field: 'moduleFunction',
                title: '模块功能'
            }
            ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            role: $("#txt_search_role").val(),  // 修改
            modifyTime: $("#txt_search_modifyTime").val()
        };
        return temp;
    };
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};
    oInit.Init = function () {
        //初始化页面上面的按钮事件

        $("#btn_add").click(function () {
            // 初始化通用弹出框
            $("#myModalLabel").text("新增");
            $("#modal2SystemPower").find(".form-control").val("");
            $('#modal2SystemPower').modal();
            postdata.DEPARTMENT_ID = "";  // ID问题  需要处理
        });

        $("#btn_edit").click(function () {
            $(function () {
                var data = {
                    "id": {
                        0: "txt_moduleFunctionName",
                        1: "txt_moduleFunctionUrl"
                    },
                    "name": {
                        0: "moduleFunctionName",
                        1: "moduleFunctionUrl"
                    },
                    "desc": {
                        0: "请输入模块功能名称",
                        1: "请输入模块功能对应URL"
                    },
                    "values": {
                        0: {
                            name: "新增",
                            value: "add"
                        },
                        1: {
                            name: "修改",
                            value: "value"
                        }
                    }
                    //                "validation": {
                    //                    0: "moduleFunctionName",
                    //                    1: "moduleFunctionUrl"
                    //                }
                }
                $('.input-group-add').initInputGroup({
                    'widget': 'input', //输入框组中间的空间类型
                    'number': '2',
                    "data": data
                });
            });
            //     // $("#myModalLabel").text("编辑");
            //     // $("#txt_departmentname").val(arrselections[0].name);
            //     // $("#txt_parentdepartment").val(arrselections[0].ParentName);
            //     // $("#txt_departmentlevel").val(arrselections[0].Level);
            //     // $("#txt_statu").val(arrselections[0].Desc);
            //     // postdata.id = arrselections[0].ID;
            //     // $('#myModal').modal();
        });

        $("#btn_delete").click(function () {
            debugger;
            var arrselections = $("#tb2systemPower").bootstrapTable('getSelections');
            if (arrselections.length <= 0) {
                toastr.warning('请选择有效数据');
                return;
            }
            Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
                if (!e) {
                    return;
                }
                $.ajax({
                    type: "post",
                    url: "/Home/Delete",
                    data: {"": JSON.stringify(arrselections)},
                    success: function (data, status) {
                        if (status == "success") {
                            toastr.success('提交数据成功');
                            $("#tb2systemPower").bootstrapTable('refresh');
                        }
                    },
                    error: function () {
                        toastr.error('Error');
                    },
                    complete: function () {

                    }
                });
            });
        });

        $("#btn_submit").click(function () {
            // postdata.DEPARTMENT_NAME = $("#txt_departmentname").val();
            // postdata.PARENT_ID = $("#txt_parentdepartment").val();
            // postdata.DEPARTMENT_LEVEL = $("#txt_departmentlevel").val();
            // postdata.STATUS = $("#txt_statu").val();
            //调用验证
            var bootstrapValidator = $("#form2SystemPower").data('bootstrapValidator');
            bootstrapValidator.validate();
            if (bootstrapValidator.isValid())
                $("#form_data").submit();
            else return;
            $.ajax({
                type: "post",
                url: "/Home/GetEdit",
                data: {"": JSON.stringify(postdata)},
                success: function (data, status) {
                    if (status == "success") {
                        toastr.success('提交数据成功');
                        $("#tb2systemPower").bootstrapTable('refresh');
                    }
                },
                error: function () {
                    toastr.error('Error');
                },
                complete: function () {
                }
            });
        });

        $("#btn_query").click(function () {
            //增加查询条件
            $("#tb2systemPower").bootstrapTable('refresh');
        });
    };
    return oInit;
};