/**
 *   通用table表单回显方法  传入页面modal id 与 改页面的描述信息区域的ID  将以后的值根据key进行回显 modal
 * */

function onReappear(modal, modalName,formTable) {

    var arrselections = $("#"+formTable).bootstrapTable('getSelections');
    if (arrselections.length > 1) {
        toastr.warning('只能选择一行进行编辑');
        return;
    }
    if (arrselections.length <= 0) {
        toastr.warning('请选择有效数据');
        return;
    }
    $("#" + modalName).text("编辑");
    for (var key in arrselections[0]) {
        $("#txt_" + key).val(arrselections[0][key]);
    }
    $('#' + modal).modal();
}

/**
 *   通用table表单提交方法  -- 后续修改
 * */

function onSubmit(form, url,tb_departments,myModal) {
    var postdata = {};
    //获取当前form表单提交的数据
    var t = $("#" + form).serializeArray();
    $.each(t, function () {
        postdata[this.name] = this.value;
    });
    // console.log(JSON.stringify(postdata));  //{"name":"12333331","sex":"1","education":"3620194","bithday":"","age":"3620194","school":"122","phone":"17557295971"}

    var bootstrapValidator = $("#" + form).data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            type: "post",
            url: url,
            data: {"": JSON.stringify(postdata)},
            success: function (data, status) {
                if (status == "success") {
                    //编辑窗口关闭
                    $('#'+myModal).modal('hide');
                    toastr.success('提交数据成功');
                    //数据请求成功之后刷新表单数据
                    $("#"+tb_departments).bootstrapTable('refresh');
                }
            },
            error: function () {
                toastr.error('Error');
            },
            complete: function () {
            }
        });
    } else {
        return;
    }
}