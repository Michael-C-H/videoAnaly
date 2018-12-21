var myDataTable;
$(document).ready(function() {
		initDt();		
});

function initDt(){
	myDataTable = $('#camera-datatable').DataTable(
			{
				"processing" : true,// 刷新的那个对话框
				"serverSide" : true,// 服务器端获取数据
				"paging" : true,// 开启分页
				lengthMenu : [ // 自定义分页长度
				[ 10, 50, 100 ],[ '10 条', '50条', '100条' ] ],
				ordering : false,
				"ajax" : {
					"url" : "/videocameralist",
					"type" : "POST",
					"data" : 
						
						function(d) {
						// 删除多余请求参数
						for ( var key in d) {
							if (key.indexOf("columns") == 0 /*|| key.indexOf("order") == 0 || key.indexOf("search") == 0*/) { // 以columns开头的参数删除
								delete d[key];
							}
						}
						/*var searchParams = {
							"code" : $("#retryType").val(),
							"name" : $("#departmentCode").val()
						};
						// 附加查询参数
						if (searchParams) {
							$.extend(d, searchParams); // 给d扩展参数
						}*/
					},
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
				"searching" : true,
				"columns" : [ {
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
				}],
				"oLanguage" : { // 国际化配置
					"sProcessing" : "正在获取数据，请稍后...",
					"sLengthMenu" : "每页显示 _MENU_",
					"sZeroRecords" : "没有找到数据",
					"sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
					"sInfoEmpty" : "记录数为0",
					"sInfoFiltered" : "(全部记录数 _MAX_ 条)",
					"sInfoPostFix" : "",
					"sSearch" : "查询",
					"sUrl" : "",
					"oPaginate" : {
						"sFirst" : "第一页",
						"sPrevious" : "上一页",
						"sNext" : "下一页",
						"sLast" : "最后一页"
					}
				},
				"fnDrawCallback": function(){  //序号字段
					　　var api = this.api();
					　　var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
					　　api.column(0).nodes().each(function(cell, i) {
					　　　　//此处 startIndex + i + 1;会出现翻页序号不连续，主要是因为startIndex 的原因,去掉即可。
					　　　　cell.innerHTML = startIndex + i + 1;
					　　　　//cell.innerHTML =  i + 1;

					　　}); 
					},
					"columnDefs": [{
				        //   指定第最后一列
				        targets: -1,
				        render: function(data, type, row) {
				        	console.log(row);
                            var html ='<button class="btn btn-xs jfedit btn-info" onclick="edit(\''+row.id+'\')">编辑</button>&nbsp;&nbsp;'
                            +'<button class="btn btn-xs btn-danger jfdelete" onclick="del(\''+row.id+'\')">删除</button>';
                            return html;
                        }

				    }]
				   
			
			}

	);	
	
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



