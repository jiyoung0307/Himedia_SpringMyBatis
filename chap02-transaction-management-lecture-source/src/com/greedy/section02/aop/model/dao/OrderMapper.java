package com.greedy.section02.aop.model.dao;

import com.greedy.section02.aop.model.dto.OrderDTO;
import com.greedy.section02.aop.model.dto.OrderMenuDTO;

public interface OrderMapper {

	int registOrder(OrderDTO order);	// 메소드명 자체가 쿼리 이름임!@!@ (Service와 연결)

	int registOrderMenu(OrderMenuDTO orderMenu);	// (Service와 연결)

}
