package com.bestseller.pojo.query;

import java.util.ArrayList;
import java.util.List;

public class BrandQuery {
	private Integer id;
	private String name;
	private String description;
	private String imgUrl;
	private String webSite;
	private Integer sort;
	private Integer isDisplay;
	public BrandQuery() {
		super();
	}
	public BrandQuery(Integer id, String name, String description, String imgUrl, String webSite, Integer sort,
			Integer isDisplay) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imgUrl = imgUrl;
		this.webSite = webSite;
		this.sort = sort;
		this.isDisplay = isDisplay;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	//查询域
	private String fields;
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	
	//模糊查询
	private boolean nameLike;
	public boolean isNameLike() {
		return nameLike;
	}
	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}
	
	//order by排序
	public class FiledOrders{
		private String field;
		private String order;
		public FiledOrders() {
			super();
		}
		public FiledOrders(String field, String order) {
			super();
			this.field = field;
			this.order = order;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
	}
	
	private List<FiledOrders> orderByFileds=new ArrayList<>();
	public void orderById(boolean isASC){
		orderByFileds.add(new FiledOrders("id",isASC==true?"asc":"desc"));
	}
	
	private Integer pageNo=1;
	//开始行
	private Integer startRow;
	//每页数
	private Integer pageSize=10;
	
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.startRow=(pageNo-1)*pageSize;
		this.pageNo = pageNo;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.startRow=(pageNo-1)*pageSize;
		this.pageSize = pageSize;
	}
	
}
