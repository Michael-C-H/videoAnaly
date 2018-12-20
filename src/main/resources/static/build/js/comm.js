//弹出错误提示的登录框
$.showErr = function(str, func) {
    // 调用show方法
    BootstrapDialog.show({
        type : BootstrapDialog.TYPE_DANGER,
        title : '错误 ',
        message : str,
        size : BootstrapDialog.SIZE_SMALL,//size为小，默认的对话框比较宽
        buttons : [ {// 设置关闭按钮
            label : '关闭',
            action : function(dialogItself) {
                dialogItself.close();
            }
        } ],
        // 对话框关闭时带入callback方法
        onhide : func
    });
};


$.showSuccess = function(str, func) {
    BootstrapDialog.show({
        type : BootstrapDialog.TYPE_SUCCESS,
        title : '成功 ',
        message : str,
        size : BootstrapDialog.SIZE_SMALL,
        buttons : [ {
            label : '确定',
            action : function(dialogItself) {
                dialogItself.close();
            }
        } ],
        // 指定时间内可自动关闭
        /*onshown : function(dialogRef) {
            setTimeout(function() {
                dialogRef.close();
            }, YUNM._set.timeout);
        },*/
        onhide : func
    });
};

