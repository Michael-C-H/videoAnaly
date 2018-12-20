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
$("#modifyForm").submit(function(e){
	$("#submitButton").attr('disabled',"true");
	$.ajax({
        url:$("#modifyForm").attr("action"),
        data:$('#modifyForm').serialize(),
        type:"post",
        success:function(data){
        	if(data){
        		alert("操作成功");
        		//成功之后刷新table
        		myDataTable.ajax.reload();
        		$("#myModal").modal("hide");
        		
        	}
        	else{
        		alert("操作失败");
        	}
        	$("#submitButton").removeAttr("disabled");
        },
        error:function(data){
        	//alert("操作异常：" + $("#id").val() + data.responseText);
        	$("#submitButton").removeAttr("disabled");
        }
    });
    return false;
});

function clickonline(checkbox){
	if (checkbox.checked == true){
		
	}
}


function del(id,name){
	//处理业务
	console.log(id);
    $.ajax(
            {
                url: "/deletevc",
                data:{"id":id},
                type: "post",
                beforeSend:function()
                {
                    return true;
                },
                success:function(data)
                {
                    if(data)
                    {
                        alert('操作成功');
                        //成功之后刷新table
                    	myDataTable.ajax.reload();
                    	$("#myModal").modal("hide");
                    }
                    else
                    {
                        alert('操作失败');
                    }
                },
                error:function()
                {
                    alert('请求出错');
                },
                complete:function()
                {
                    // $('#tips').hide();
                }
            });

    return false;
	
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
                 type: "post",
                 beforeSend:function()
                 {
                     // $("#tip").html("<span style='color:blue'>正在处理...</span>");
                     return true;
                 },
                 success:function(data)
                 {
                     if(data)
                     {
                    	 
                         // 解析json数据
                         var data = data;
                         // 赋值
                         $("#formid").val(data.id);
                         $("#CODE").val(data.code);
                         $("#NAME").val(data.name);
                         $("#SOURCE").val(data.source);
                         var analy = data.anltsisType;
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
                         $("#ONLINE_NUM").val(data.onlineNum);
                         $("#TOTAL").val(data.total);

                         // 将input元素设置为readonly
                        // $('#user_id').attr("readonly","readonly")
                        // location.reload();
                     }
                     else
                     {
                        //$("#tip").html("<span style='color:red'>失败，请重试</span>");
                        alert('操作失败');
                     }
                 },
                 error:function()
                 {
                     alert('请求出错');
                 },
                 complete:function()
                 {
                     // $('#tips').hide();
                 }
             });
    
}



