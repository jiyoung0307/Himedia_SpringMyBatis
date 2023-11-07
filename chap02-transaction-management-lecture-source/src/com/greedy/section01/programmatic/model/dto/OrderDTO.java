package com.greedy.section01.programmatic.model.dto;

import java.util.List;

public class OrderDTO {
	
	private int code;
	private String date;
	private String time;
	private int totalPrice;
	
	List<OrderMenuDTO> orderMenuList;

	public OrderDTO() {
	}

	public OrderDTO(int code, String date, String time, int totalPrice, List<OrderMenuDTO> orderMenuList) {
		this.code = code;
		this.date = date;
		this.time = time;
		this.totalPrice = totalPrice;
		this.orderMenuList = orderMenuList;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderMenuDTO> getOrderMenuList() {
		return orderMenuList;
	}

	public void setOrderMenuList(List<OrderMenuDTO> orderMenuList) {
		this.orderMenuList = orderMenuList;
	}

	@Override
	public String toString() {
		return "OrderDTO [code=" + code + ", date=" + date + ", time=" + time + ", totalPrice=" + totalPrice
				+ ", orderMenuList=" + orderMenuList + "]";
	}
}