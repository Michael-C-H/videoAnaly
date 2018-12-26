//弹出错误提示的登录框
$.showErr = function(str, func) {
    // 调用show方法
    BootstrapDialog.show({
        type : BootstrapDialog.TYPE_DANGER,
        title : '错误 ',
        message : str,
        size : BootstrapDialog.SIZE_SMALL,// size为小，默认的对话框比较宽
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
        // 指定时间内可自动关闭
		onshown : function(dialogRef) { setTimeout(function() {
		dialogRef.close(); }, 500); },
        onhide : func
    });
};


$.fn.dtconfig =  {
			"processing" : true,// 刷新的那个对话框
			"serverSide" : true,// 服务器端获取数据
			"paging" : true,// 开启分页
			lengthMenu : [ // 自定义分页长度
			[ 10, 50, 100 ],[ '10 条', '50条', '100条' ] ],
			ordering : false,
			"autoWidth": false, //自适应宽度
			"ajax" : {
				//"url" : "/videocameralist",
				"type" : "POST",
				"data" :function(searchParams){
				 	return function(d) {
						// 删除多余请求参数
						for ( var key in d) {
							if (key.indexOf("columns") == 0 || key.indexOf("order") == 0 || key.indexOf("search") == 0) { // 以columns开头的参数删除
								delete d[key];
							}
						}						
						// 附加查询参数
						if (searchParams()) {
							$.extend(d, searchParams()); // 给d扩展参数
						}
					}
				 } 
					
					,
				"dataType" : "json",
				"dataFilter" : function(json) {// json是服务器端返回的数据
					json = JSON.parse(json);
					var returnData = {};
					returnData.draw = json.draw;
					returnData.recordsTotal = json.total;// 返回数据全部记录
					returnData.recordsFiltered = json.total;// 后台不实现过滤功能，每次查询均视作全部结果
					returnData.data = json.data;// 返回的数据列表
					return JSON.stringify(returnData);// 这几个参数都是datatable需要的，必须要
				}
			},
			"searching" : false,
			"oLanguage" : { // 国际化配置
				"sProcessing" : "正在获取数据，请稍后...",
				"sLengthMenu" : "每页显示 _MENU_",
				"sZeroRecords" : "没有找到数据",
				"sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
				"sInfoEmpty" : "记录数为0",
				"sInfoFiltered" : "(全部记录数 _MAX_ 条)",
				"sInfoPostFix" : "",
				"sSearch" : "查询",
				"sSearchPlaceholder": "编号或名称",
				"sUrl" : "",
				"oPaginate" : {
					"sFirst" : "第一页",
					"sPrevious" : "上一页",
					"sNext" : "下一页",
					"sLast" : "最后一页"
				}
			},
			"fnDrawCallback": function(){  // 序号字段
				　　var api = this.api();
				　　var startIndex= api.context[0]._iDisplayStart;// 获取到本页开始的条数
				　　api.column(0).nodes().each(function(cell, i) {
				　　　　// 此处 startIndex + i + 1;会出现翻页序号不连续，主要是因为startIndex
					// 的原因,去掉即可。
				　　　　cell.innerHTML = startIndex + i + 1;
				　　　　// cell.innerHTML = i + 1;

				　　}); 
				}
			   
		
		
}


//深度克隆
function deepClone(obj){
    var result,oClass=isClass(obj);
        //确定result的类型
    if(oClass==="Object"){
        result={};
    }else if(oClass==="Array"){
        result=[];
    }else{
        return obj;
    }
    for(key in obj){
        var copy=obj[key];
        if(isClass(copy)=="Object"){
            result[key]=arguments.callee(copy);//递归调用
        }else if(isClass(copy)=="Array"){
            result[key]=arguments.callee(copy);
        }else{
            result[key]=obj[key];
        }
    }
    return result;
}
//返回传递给他的任意对象的类
function isClass(o){
    if(o===null) return "Null";
    if(o===undefined) return "Undefined";
    return Object.prototype.toString.call(o).slice(8,-1);
}

