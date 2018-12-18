package com.kingwant.videoAnaly.entity;

import com.baomidou.mybatisplus.plugins.Page;


public class BootstrapPageEntity<T> {
	
	
	private int draw;
	private int start;
	private int length;
	private int current;	
	
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getCurrent() {
		current=start==0?1:(start/length+1);
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}	
	public Page<T> getPageObj() {
		Page<T> page=new Page<>();
		page.setSize(getLength()==0?10:getLength());
		page.setCurrent(getCurrent());
		return page;
	}
	
	

}
