var main = {
    //获取到列表页面
    initPage: function () {
        tool.ajaxloadPage("/crawler/index", "get", CONSTANTS.CONTENT_ID);
    },
    
}

$(function () {
    main.initPage();
    
    
});



