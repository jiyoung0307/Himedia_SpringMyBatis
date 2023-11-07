package com.greedy.section01.connection.javaconfig;

public class MenuDTO {
	private int code;
	private String name;
	private int price;
	private int categoryCode;
	private String orderableStatus;
	
	public MenuDTO() {
	}
	public MenuDTO(int code, String name, int price, int categoryCode, String orderableStatus) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.categoryCode = categoryCode;
		this.orderableStatus = orderableStatus;
	}
	
	int getCode() {
		return code;
	}
	void setCode(int code) {
		this.code = code;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	int getPrice() {
		return price;
	}
	void setPrice(int price) {
		this.price = price;
	}
	int getCategoryCode() {
		return categoryCode;
	}
	void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}
	String getOrderableStatus() {
		return orderableStatus;
	}
	void setOrderableStatus(String orderableStatus) {
		this.orderableStatus = orderableStatus;
	}
	
	@Override
	public String toString() {
		return "MenuDTO [code=" + code + ", name=" + name + ", price=" + price + ", categoryCode=" + categoryCode
				+ ", orderableStatus=" + orderableStatus + "]";
	}
}
