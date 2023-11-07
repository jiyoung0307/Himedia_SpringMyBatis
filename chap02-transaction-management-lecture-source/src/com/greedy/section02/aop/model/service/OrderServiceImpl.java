package com.greedy.section02.aop.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.section02.aop.model.dao.OrderMapper;
import com.greedy.section02.aop.model.dto.OrderDTO;
import com.greedy.section02.aop.model.dto.OrderMenuDTO;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	
	private SqlSessionTemplate sqlSession;
	
	/* 스프링 트랜잭션 처리할 때 추가하자 (까먹지 말고 (v))*/
	private DataSourceTransactionManager transactionManager;
	
	@Autowired
	public OrderServiceImpl(SqlSessionTemplate sqlSession, DataSourceTransactionManager transactionManager) {
		this.sqlSession = sqlSession;
		this.transactionManager = transactionManager;
	} 
		
	@Override		// OrderMapper 메소드와 연결
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = {Exception.class})	// spring-context에서 설정 추가하자
	/* 트랜잭션으로써의 기능 완 */
	public int registOrder(OrderDTO order) {
		
		OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
		
		int orderResult = orderMapper.registOrder(order);
		
		List<OrderMenuDTO> orderMenuList = order.getOrderMenuList();
		
		int orderMenuResult = 0;
		for (OrderMenuDTO orderMenu : orderMenuList) {
			orderMenuResult += orderMapper.registOrderMenu(orderMenu);
		}
		
		int result = 0;
		if(orderResult > 0 && orderMenuResult == orderMenuList.size()) {
			result = 1;
		}
		
		return result;
	}
}
