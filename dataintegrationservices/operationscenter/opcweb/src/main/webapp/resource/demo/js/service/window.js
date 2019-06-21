$(function () {
    $('#myModal').on('show.bs.modal', function () {
        // 执行一些动作...
        console.log("modal执行前触发");
    })

    $('#myModal').on('hide.bs.modal', function () {
        // 执行一些动作...
        console.log("modal关闭前触发");
    })

    //多选
    $("#sel_menu2").select2({
        tags: true,
        maximumSelectionLength: 3  //最多能够选择的个数
    });
});