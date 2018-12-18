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
					"url" : "/test",
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
                            var html ='<button class="btn btn-xs jfedit btn-info" onclick="edit('+row.id+')">编辑</button>&nbsp;&nbsp;'
                            +'<button class="btn btn-xs btn-danger jfdelete" onclick="del('+row.id+')">删除</button>';
                            return html;
                        }

				    }]
				   
			
			}

	);	
	
}

function add(){
	alert('新增');
}


function del(id,name){
	//处理业务
	
	//成功之后刷新table
	myDataTable.ajax.reload();
}

function edit(id,name){
	alert(id);
}