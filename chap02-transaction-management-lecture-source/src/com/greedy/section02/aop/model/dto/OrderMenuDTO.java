package com.greedy.section02.aop.model.dto;

public class OrderMenuDTO {
	private int code;
	private int menuCode;
	private int amount;
	
	
	public OrderMenuDTO() {
	}

	public OrderMenuDTO(int code, int menuCode, int amount) {
		this.code = code;
		this.menuCode = menuCode;
		this.amount = amount;
	}

	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public int getMenuCode() {
		return menuCode;
	}


	public void setMenuCode(int menuCode) {
		this.menuCode = menuCode;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "OrderMenuDTO [code=" + code + ", menuCode=" + menuCode + ", amount=" + amount + "]";
	}
	
}