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
	
	 dtconfig.initComplete=function(row, data, dataIndex) {
		 var tab = document.getElementById("camera-datatable"); //找到这个表格
			var rows = tab.rows; //取得这个table下的所有行
			for(var i=0;i<rows.length;i++)
			{
			for(var j=0;j<rows[i].cells.length;j++)
			{
			var cell = rows[i].cells[j];
			if(($(cell).text()).indexOf("编辑") != -1||($(cell).text()).indexOf("删除") != -1){
				continue;
			}else{
				$(cell).attr("title",cell.innerHTML);
			}
			}
			}
		 };
	//实例化table
	myDataTable = $('#camera-datatable').DataTable(dtconfig);	
	
}
//新增
function add(){
	loadModal("新增");
}
//表单提交
function submitform(){	
	ajaxsubmit();
	
}

function ajaxsubmit(){	
	//基础验证
	 $('#modifyForm').parsley().validate();
	 //复选框验证
	 var f=false;
	 $('input[name="anltsisType"]').each(function(){  
		 if($(this).is(":checked")){
			 f=true;
			 return false;
		 }
	 });
	
	 if (true === $('#modifyForm').parsley().isValid()) {
		 if(!f){
			 $.showErr("请至少选择一个分析类型！");
			 return;
		 }
		 
		 $.ajax({
        url:"/updateorinsertvc",
        data:$('#modifyForm').serializeArray(),
        async:false,
        type:"post",
        success:function(data){
        	if(data.rs){
        		//成功之后刷新table
        		myDataTable.ajax.reload();
        		$("#myModal").modal("hide");
        		$.showSuccess("操作成功",myDataTable.ajax.reload());
        	}
        	else{
        		$.showErr("操作失败"+data.msg);
        	}
        },
        error:function(data){
        	$.showErr("操作失败"+data.msg);
        }
    });
}
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
                    	 $.showErr("获取数据失败:"+data.msg);
                     }
                 },
                 error:function()
                 {
                	 $.showErr("获取数据失败:"+msg);
                 },
                 complete:function()
                 {
                     // $('#tips').hide();
                 }
             });
	 
	 loadModal("编辑");
    
}


function loadModal(name){
	
	//模式框标题
	$("#myModalLabel").text(name);
	
	
	if("新增"==name){//新增定制
		$('#modifyForm')[0].reset();
		$("#formid").val("");
	}else if("编辑"==name){//编辑定制
		
	}
	//展示模式框
	 $("#myModal").modal("show");
	 //重置验证
	 $('#modifyForm').parsley().reset();
	 
	 //是否展示对应人数框
	 $('input[name="anltsisType"]').each(function(){  
		 showOrHide($(this).val(),$(this).is(":checked"));
	 });
	 //添加change事件
	 $("#ONLINE").change(function() { 
		 showOrHide($(this).val(),$(this).is(":checked"));
	 });
	 $("#NUM").change(function() { 
		 showOrHide($(this).val(),$(this).is(":checked"));
	 });
	
}

function showOrHide(val,checked){
	var str=checked?'block':'none';
	
	if("B"==val){
		$("#range-num").css('display',str); 
		if(checked){
			 $("#ONLINE_NUM").attr("required","required");
		}else{
			 $("#ONLINE_NUM").removeAttr("required");
		}
	}else if("C"==val){
		$("#total-num").css('display',str); 
		if(checked){
			 $("#TOTAL").attr("required","required");
		}else{
			 $("#TOTAL").removeAttr("required");
		}
	}
}







