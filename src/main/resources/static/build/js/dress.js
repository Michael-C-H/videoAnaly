var myDataTable;
$(document).ready(function() {
		initDt();		
		var beginTimeStore = '';
		var endTimeStore = '';
		$('#stTime').daterangepicker({
		    "timePicker": true,
		    "locale": {
		        format: 'YYYY-MM-DD',		       
		    }
		}, function(start, end, label) {
		   
		});
});

function initDt(){
	//克隆默认配置
	var dtconfig=deepClone($.fn.dtconfig);
	//修改url
	dtconfig.ajax.url="/dresslist";
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
		"data" : "stringDate"
	}, {
		"data" : "reason"
	}, {
		"data" : null
	}];
	//列操作
	dtconfig.columnDefs= [{
        //   指定第最后一列
        targets: -1,
        render: function(data, type, row) {
        	var html ='<button class="btn btn-xs jfedit btn-info" onclick="newpage(\''+row.id+'\')">查看</button>&nbsp;&nbsp;';
            return html;
        }

    }];
	//实例化table
	myDataTable = $('#camera-datatable').DataTable(dtconfig);	
	
}

function newpage(id,name){
	window.open("/dress/detail?id="+id) 
}
function check(id,name){
	 $("#myModal").modal().on(
             "shown.bs.modal",
             function() {
             }).on('hidden.bs.modal', function() {
         // 关闭弹出框的时候清除绑定(这个清空包括清空绑定和清空注册事件)
     });
	 
	 $.ajax(
             {
                 url: "/selectonedress",
                 data:{"id":id},
                 type: "post",
                 beforeSend:function()
                 {
                     // $("#tip").html("<span style='color:blue'>正在处理...</span>");
                     return true;
                 },
                 success:function(data)
                 {
                	 console.log(data);
                     if(data)
                     {
                    	 
                         // 解析json数据
                         var data = data;
                         // 赋值
                         $("#code").html(data.code);
                         $("#name").html(data.name);
                         $("#exDate").html(data.stringDate);
                         $("#reason").html(data.reason);
                         // 将input元素设置为readonly
                        // $('#user_id').attr("readonly","readonly")
                        // location.reload();
                     }
                     else
                     {
                        //$("#tip").html("<span style='color:red'>失败，请重试</span>");
                    	 $.showErr("查看失败");
                     }
                 },
                 error:function()
                 {
                	 $.showErr("查看失败");
                 },
                 complete:function()
                 {
                     // $('#tips').hide();
                 }
             });
    
}


