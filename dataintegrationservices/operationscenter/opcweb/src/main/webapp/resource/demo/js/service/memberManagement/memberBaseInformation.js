var age = [
    {
        id: 1,
        text: '1'
    },
    {
        id: 2,
        text: '2'
    },
    {
        id: 3,
        text: '3'
    },
    {
        id: 4,
        text: '4'
    },
    {
        id: 5,
        text: '5'
    },
    {
        id: 6,
        text: '6'
    },
    {
        id: 7,
        text: '7'
    },
    {
        id: 8,
        text: '8'
    },
    {
        id: 9,
        text: '9'
    },
    {
        id: 10,
        text: '10'
    },
    {
        id: 11,
        text: '11'
    },
    {
        id: 12,
        text: '12'
    },
    {
        id: 13,
        text: '13'
    }, {
        id: 14,
        text: '14'
    }, {
        id: 15,
        text: '15'
    }, {
        id: 16,
        text: '16'
    }, {
        id: 17,
        text: '17'
    }, {
        id: 18,
        text: '18'
    }, {
        id: 19,
        text: '19'
    }, {
        id: 20,
        text: '20'
    }, {
        id: 21,
        text: '21'
    }, {
        id: 22,
        text: '22'
    }, {
        id: 23,
        text: '23'
    }, {
        id: 24,
        text: '24'
    }, {
        id: 25,
        text: '25'
    }, {
        id: 26,
        text: '26'
    }, {
        id: 27,
        text: '27'
    }, {
        id: 28,
        text: '28'
    }, {
        id: 29,
        text: '29'
    }, {
        id: 30,
        text: '30'
    }, {
        id: 31,
        text: '31'
    }, {
        id: 32,
        text: '32'
    }, {
        id: 33,
        text: '33'
    }, {
        id: 34,
        text: '34'
    }, {
        id: 35,
        text: '35'
    }, {
        id: 36,
        text: '36'
    }, {
        id: 37,
        text: '37'
    }, {
        id: 38,
        text: '38'
    }, {
        id: 39,
        text: '39'
    }, {
        id: 40,
        text: '40'
    }, {
        id: 41,
        text: '41'
    }, {
        id: 42,
        text: '42'
    }
];

var constellationData = [
    {
        id: 0,
        text: '白羊座'
    },
    {
        id: 1,
        text: '金牛座'
    },
    {
        id: 2,
        text: '双子座'
    },
    {
        id: 3,
        text: '巨蟹座'
    },
    {
        id: 4,
        text: '狮子座'
    },
    {
        id: 5,
        text: '处女座'
    },
    {
        id: 6,
        text: '天秤座'
    },
    {
        id: 7,
        text: '天蝎座'
    },
    {
        id: 8,
        text: '人马座'
    },
    {
        id: 9,
        text: '山羊座'
    },
    {
        id: 10,
        text: '水瓶座'
    },
    {
        id: 11,
        text: '双鱼座'
    }
];

var educationData = [
    {
        id: 0,
        text: '博士'
    },
    {
        id: 1,
        text: '硕士'
    },
    {
        id: 2,
        text: '本科'
    },
    {
        id: 3,
        text: '专科'
    },
    {
        id: 3,
        text: '其他'
    }

];

$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    $("#bithday").datetimepicker({
        language: 'zh-CN',  //日期
        minView: "year",
        bootcssVer: 3,
        format: "yyyy-mm-dd",//日期格式
        autoclose: true,//选中关闭
        todayBtn: true//今日按钮
    });
    $("#txt_bithday").datetimepicker({
        language: 'zh-CN',  //日期
        minView: "year",
        bootcssVer: 3,
        format: "yyyy-mm-dd",//日期格式
        autoclose: true,//选中关闭
        todayBtn: true//今日按钮
    });

    $("#sel_menu2").select2({
        tags: true
        // maximumSelectionLength: 3  //最多能够选择的个数
    });
    $("#education").select2({
        data: educationData
    });
    $("#age").select2({
        data: age
    });
    $("#txt_education").select2({
        data: educationData
    });
    $("#constellation").select2({
        data: constellationData
    });
    $("#txt_constellation").select2({
        data: constellationData
    })
});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_departments').bootstrapTable({
            // url: '/main/GetDepartment',       //请求后台的URL（*）
            url: 'https://easy-mock.com/mock/597f4257a1d30433d84e18ea/example/spiderIndex',       //请求后台的URL（*）
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
                field: 'name',
                title: '名称'
            }, {
                field: 'sex',
                title: '性别'
            }, {
                field: 'age',
                title: '年龄'
            }, {
                field: 'birthday',
                title: '出生日期'
            }, {
                field: 'education',
                title: '学历'
            }, {
                field: 'school',
                title: '毕业院校'
            }, {
                field: 'homeTown',
                title: '家乡'
            }, {
                field: 'animal',
                title: '生肖'
            }, {
                field: 'constellation',
                title: '星座'
            }, {
                field: 'phone',
                title: '联系电话'
            }, {
                field: 'idNumber',
                title: '身份证号码'
            }
            ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            departmentname: $("#txt_search_departmentname").val(),
            statu: $("#txt_search_statu").val()
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
            $("#myModalLabel").text("新增");
            $("#myModal").find(".form-control").val("");
            $('#myModal').modal();
            postdata.DEPARTMENT_ID = "";
        });

        // $("#btn_edit").click(function () {
        //     var arrselections = $("#tb_departments").bootstrapTable('getSelections');
        //     if (arrselections.length > 1) {
        //         toastr.warning('只能选择一行进行编辑');
        //         return;
        //     }
        //     if (arrselections.length <= 0) {
        //         toastr.warning('请选择有效数据');
        //         return;
        //     }
        //     debugger;
        //
        //
        //     $("#myModalLabel").text("编辑");
        //     $("#txt_departmentname").val(arrselections[0].name);
        //     $("#txt_parentdepartment").val(arrselections[0].ParentName);
        //     $("#txt_departmentlevel").val(arrselections[0].Level);
        //     $("#txt_statu").val(arrselections[0].Desc);
        //     postdata.id = arrselections[0].ID;
        //     $('#myModal').modal();
        // });

        $("#btn_delete").click(function () {
            debugger;
            var arrselections = $("#tb_departments").bootstrapTable('getSelections');
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
                            $("#tb_departments").bootstrapTable('refresh');
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

        // $("#btn_submit").click(function () {
        //     debugger;
        //     postdata.DEPARTMENT_NAME = $("#txt_departmentname").val();
        //     postdata.PARENT_ID = $("#txt_parentdepartment").val();
        //     postdata.DEPARTMENT_LEVEL = $("#txt_departmentlevel").val();
        //     postdata.STATUS = $("#txt_statu").val();
        //     //调用验证
        //     var bootstrapValidator = $("#form_data").data('bootstrapValidator');
        //     bootstrapValidator.validate();
        //     if(bootstrapValidator.isValid())
        //         $("#form_data").submit();
        //     else return;
        //
        //     $.ajax({
        //         type: "post",
        //         url: "/Home/GetEdit",
        //         data: {"": JSON.stringify(postdata)},
        //         success: function (data, status) {
        //             if (status == "success") {
        //                 toastr.success('提交数据成功');
        //                 $("#tb_departments").bootstrapTable('refresh');
        //             }
        //         },
        //         error: function () {
        //             toastr.error('Error');
        //         },
        //         complete: function () {
        //         }
        //     });
        // });

        $("#btn_query").click(function () {
            //增加查询条件
            $("#tb_departments").bootstrapTable('refresh');
        });
    };
    return oInit;
};

// function formatRepoProvince(repo) {
//     if (repo.loading) return repo.text;
//     var markup = "<div>" + repo.name + "</div>";
//     return markup;
// };
