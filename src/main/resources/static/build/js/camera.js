var myDataTable;
$(document).ready(function() {
		initDt();		
});

function initDt(){
	//克隆默认配置
	var dtconfig=deepClone($.fn.dtconfig);
	//修改url
	dtconfig.ajax.url="/videocameralist";
	//修改查询参数
	var searchParams = function(){
		return {
			"code" : $("#s_code").val(),
			"name" : $("#s_name").val()	
		}
	};
	dtconfig.ajax.data = dtconfig.ajax.data(searchParams);	
	//修改列设置
	dtconfig.columns=[ {
		"data" : null
	}, {
		"data" : "code"
	}, {
		"data" : "name"
	}, {
		"data" : "source"
	}, {
		"data" : "anltsisType"
	}, {
		"data" : "onlineNum"
	}, {
		"data" : "total"
	}, {
		"data" : "linePt1"
	}, {
		"data" : "linePt2"
	}, {
		"data" : null
	}];
	//列操作
	dtconfig.columnDefs= [{
        //   指定第最后一列
        targets: -1,
        render: function(data, type, row) {
            var html ='<button class="btn btn-xs jfedit btn-info" onclick="edit(\''+row.id+'\')">编辑</button>&nbsp;&nbsp;'
            +'<button class="btn btn-xs btn-danger jfdelete" onclick="del(\''+row.id+'\')">删除</button>';
            return html;
        }

    }];
	//实例化table
	myDataTable = $('#camera-datatable').DataTable(dtconfig);	
	
}
var $table = $('#camera-datatable');
var isAdd=false;
//新增
function add(){
	isAdd=true;
	$("#myModal").modal().on(
            "shown.bs.modal",
            function() {
            	if(isAdd){            		
            		$(':input','#updateform').not(':button,:submit,:reset').val('').removeAttr('checked').removeAttr('checked');
            	}
            	/*$(this).removeData("bs.modal");
            	$('#myform')[0].reset();*/
            }).on('hidden.bs.modal', function(event) {
            	// 关闭弹出框的时候清除绑定(这个清空包括清空绑定和清空注册事件)
            	
            	
    });
}
//表单提交
function submitform(){
	
	if($("#CODE").val()==''){
		$.showErr("编码不能为空");
		return false;
	}else if($("#NAME").val()==''){
		$.showErr("名称不能为空");
		return false;
	}else if($("#SOURCE").val()==''){
		$.showErr("数据源不能为空");
		return false;
	}else if($("#ONLINE").is(':checked')){
		if($("#ONLINE_NUM").val()==''){
			$.showErr("勾选可见范围人数时,可见范围阀值必填");
			return false;
		}
		ajaxsubmit();
	}else if($("#NUM").is(':checked')){
		if($("#TOTAL").val()==''){
			$.showErr("勾选总人数时,总人数阀值必填");
			return false;
		}
		ajaxsubmit();
	}else{
		ajaxsubmit();
	}
	
}

function ajaxsubmit(){
	$.ajax({
        url:"/updateorinsertvc",
        data:$('#modifyForm').serialize(),
        async:false,
        type:"post",
        success:function(data){
        	if(data.rs){
        		//成功之后刷新table
        		myDataTable.ajax.reload();
        		$("#myModal").modal("hide");
        		
        	}
        	else{
        		$.showErr("编辑失败"+data.msg);
        	}
        },
        error:function(data){
        	$.showErr("编辑失败"+data.msg);
        }
    });
}
function del(id,name){
	BootstrapDialog.confirm({
        title : '确认',
        message : '是否删除？',
        type : BootstrapDialog.TYPE_WARNING, // <-- Default value is
        // BootstrapDialog.TYPE_PRIMARY
        closable : true, // <-- Default value is false，点击对话框以外的页面内容可关闭
        draggable : true, // <-- Default value is false，可拖拽
        btnCancelLabel : '取消', // <-- Default value is 'Cancel',
        btnOKLabel : '确定', // <-- Default value is 'OK',
        btnOKClass : 'btn-warning', // <-- If you didn't specify it, dialog type
        size : BootstrapDialog.SIZE_SMALL,
        // 对话框关闭的时候执行方法
       // onhide : funcclose,
        callback : function(result) {
            // 点击确定按钮时，result为true
            if (result) {
            	$.ajax(
                {
                    url: "/deletevc",
                    data:{"id":id},
                    async:false,
                    type: "post",
                    
                    success:function(data)
                    {
                        if(data.rs)
                        {
                            //alert('操作成功');
                            $.showSuccess("删除成功",myDataTable.ajax.reload());
                            //成功之后刷新table
                        	//myDataTable.ajax.reload();
                        	//$("#myModal").modal("hide");
                        }
                        else
                        {
                        	$.showErr("删除失败"+data.msg);
                        }
                    },
                    error:function()
                    {
                    	$.showErr("删除失败"+data.msg);
                    }
                    
                });

            }
        }
    });
   
	
}


function edit(id,name){
	isAdd=false;
	 $("#myModal").modal().on(
             "shown.bs.modal",
             function() {
             }).on('hidden.bs.modal', function() {
         // 关闭弹出框的时候清除绑定(这个清空包括清空绑定和清空注册事件)
     });
	 
	 $.ajax(
             {
                 url: "/selectone",
                 data:{"id":id},
                 async:false,
                 type: "post",
                 beforeSend:function()
                 {
                     // $("#tip").html("<span style='color:blue'>正在处理...</span>");
                     return true;
                 },
                 success:function(data)
                 {
                     if(data.rs)
                     {
                    	 
                         // 解析json数据
                         var data = data;
                         // 赋值
                         $("#formid").val(data.singledata.id);
                         $("#CODE").val(data.singledata.code);
                         $("#NAME").val(data.singledata.name);
                         $("#SOURCE").val(data.singledata.source);
                         var analy = data.singledata.anltsisType;
                         $("#dress").prop("checked", false);
                         $("#ONLINE").prop("checked", false);
                         $("#NUM").prop("checked", false);
                         if(!$.isEmptyObject(analy)){
                        	 if(analy.indexOf("A") != -1){
                            	 $("#dress").prop("checked", true); 
                             }
                             if(analy.indexOf("B") != -1){
                            	 $("#ONLINE").prop("checked", true); 
                             }
                             if(analy.indexOf("C") != -1){
                            	 $("#NUM").prop("checked", true); 
                             }
                         }
                         $("#ONLINE_NUM").val(data.singledata.onlineNum);
                         $("#TOTAL").val(data.singledata.total);

                         // 将input元素设置为readonly
                        // $('#user_id').attr("readonly","readonly")
                        // location.reload();
                     }
                     else
                     {
                        //$("#tip").html("<span style='color:red'>失败，请重试</span>");
                    	 $.showErr("编辑失败"+data.msg);
                     }
                 },
                 error:function()
                 {
                	 $.showErr("编辑失败"+msg);
                 },
                 complete:function()
                 {
                     // $('#tips').hide();
                 }
             });
    
}



