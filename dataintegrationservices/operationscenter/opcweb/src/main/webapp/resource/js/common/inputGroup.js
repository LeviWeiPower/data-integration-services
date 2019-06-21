/**
 * Created by DreamBoy on 2016/4/29.
 */
$(function () {
    $.fn.initInputGroup = function (options) {
        //1.Settings 初始化设置
        var c = $.extend({
            'widget': 'input',
            'add': "<span class=\"glyphicon glyphicon-plus\"></span>",
            'del': "<span class=\"glyphicon glyphicon-minus\"></span>"
        }, options);

        var _this = $(this);
        //添加序号为1的输入框组
        addInputGroup(1);

        /**
         * 添加序号为order的输入框组
         * @param order 输入框组的序号
         */
        function addInputGroup(order) {
            //1.创建输入框组
            debugger;
            var inputGroup = $("<div class='input-group' style='margin: 10px 0'></div>");
            //2.输入框组的序号
            var inputGroupAddon1 = $("<span class='input-group-addon'></span>");
            //3.设置输入框组的序号
            inputGroupAddon1.html(" " + order + " ");
            //4.创建输入框组中的输入控件（input或textarea）
            var widget = '', inputGroupAddon2;
            if (c.widget == 'textarea') {  // 后续需要textarea 就加上
                widget = $("<textarea class='form-control' style='resize: vertical;'></textarea>");
                inputGroupAddon2 = $("<span class='input-group-addon'></span>");
            } else if (c.widget == 'input') {
                var inputBefore = "<input class='form-control' type='text'";
                var inputAfter = "></input>";
                var input = "";
                var number = c.number;
                var data = c.data;
                for (var i = 0; i < number; i++) {
                    input = input + inputBefore + " id= '" + data.id[i] + order + " 'placeholder = '" + data.desc[i] + " 'name = '" + data.name[i] + order + "'" + inputAfter;
                    //判断是否需要添加验证
                }
                widget = $(input);
                inputGroupAddon2 = $("<span class='input-group-btn'></span>");
            }

            //5.创建输入框组中最后面的操作按钮
            var addBtn = $("<button class='btn btn-default' type='button'>" + c.add + "</button>");
            addBtn.appendTo(inputGroupAddon2).on('click', function () {
                //6.响应删除和添加操作按钮事件
                if ($(this).html() == c.del) {
                    $(this).parents('.input-group').remove();
                } else if ($(this).html() == c.add) {
                    $(this).html(c.del);
                    addInputGroup(order + 1);
                }
                //7.重新排序输入框组的序号
                resort();
            });
            inputGroup.append(inputGroupAddon1).append(widget).append(inputGroupAddon2);
            //将每次生成的动态input删除
            // _this.empty();
            _this.append(inputGroup);
            //添加验证   // 后续修改
            if (c.data.validation) {
                for (var i = 0; i < number; i++) {
                    if (c.data.validation && c.data.validation[i]) {
                        $("#form2SystemPower").data('bootstrapValidator').addField(data.name[i] + order, {
                            validators: {
                                //     $('#form2SystemPower').bootstrapValidator('addField', data.name[i]+i, {
                                // validators: {
                                notEmpty: {
                                    message: '推送业务内容不能为空'
                                }
                            }
                        });
                    }
                }
            }
        }

        function resort() {
            var child = _this.children();
            $.each(child, function (i) {
                $(this).find(".input-group-addon").eq(0).html(' ' + (i + 1) + ' ');
            });
        }
    }
})
;