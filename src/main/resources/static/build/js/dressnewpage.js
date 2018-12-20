
window.onload = function () { 
	var id =${id};
	$.ajax(
            {
                url: "/selectonenum",
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
                        $("#limit").html(data.limit);
                        $("#currentNum").html(data.currentNum);
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
	
};

