package com.greedy.section01.programmatic.model.dao;

import com.greedy.section01.programmatic.model.dto.OrderDTO;
import com.greedy.section01.programmatic.model.dto.OrderMenuDTO;

public interface OrderMapper {

	int registOrder(OrderDTO order);	// 메소드명 자체가 쿼리 이름임!@!@

	int registOrderMenu(OrderMenuDTO orderMenu);

}
