package com.kingwant.videoAnaly.entity;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

public class BootstrapPageResult<T> {	
	
		/**
		 * 总共条数
		 */
		private Integer total;	
		/**
		 * 前端传过来的参数，原样返回
		 */
		private Integer draw;
		/**
		 * 列表数据
		 */
		private List<T> data;
		/**
		 * 单个数据
		 */
		private T singledata;
		
		/**
		 * 消息提示
		 */
		private String  msg;
		private Boolean rs;
		
		/**
		 * 出错时给出异常信息
		 * @param msg
		 */
		public BootstrapPageResult(String msg,Boolean rs) {
			// TODO Auto-generated constructor stub
			this.msg=msg;
			this.rs = rs;
		}
		
		
		


		public BootstrapPageResult(T singledata, String msg, Boolean rs) {
			super();
			this.singledata = singledata;
			this.msg = msg;
			this.rs = rs;
		}





		/**
		 * 成功返回
		 * @param list page对象
		 * @param draw
		 */
		public BootstrapPageResult(Page<T> list,int draw) {
			this.draw=draw;
			this.total=list.getTotal();
			this.data=list.getRecords();
			
			this.msg="查询成功！";
			this.rs=true;
		}

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

		public Integer getDraw() {
			return draw;
		}

		public void setDraw(Integer draw) {
			this.draw = draw;
		}

		public List<T> getData() {
			return data;
		}

		public void setData(List<T> data) {
			this.data = data;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public Boolean getRs() {
			return rs;
		}
		
		public T getSingledata() {
			return singledata;
		}

		public void setSingledata(T singledata) {
			this.singledata = singledata;
		}

		public void setRs(Boolean rs) {
			this.rs = rs;
		}
		
		

}
