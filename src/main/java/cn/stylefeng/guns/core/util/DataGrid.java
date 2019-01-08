package cn.stylefeng.guns.core.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 封装查询结果集
 * 
 * 2016年10月17日上午11:23:17
 */
public class DataGrid {
	private Long total = Long.valueOf(0);
	private List rows = Lists.newArrayList();
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
