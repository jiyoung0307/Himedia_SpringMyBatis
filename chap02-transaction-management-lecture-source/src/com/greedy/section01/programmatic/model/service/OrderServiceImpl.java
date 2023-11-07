package com.greedy.section01.programmatic.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.greedy.section01.programmatic.model.dao.OrderMapper;
import com.greedy.section01.programmatic.model.dto.OrderDTO;
import com.greedy.section01.programmatic.model.dto.OrderMenuDTO;

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
	
	@Override
	public int registOrder(OrderDTO order) {
		
		OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
		
		/* 한 건의 주문을 TBL_ORDER 테이블(부모 테이블)에 insert */
		int orderResult = orderMapper.registOrder(order);	// 리믹스 방식은 넘겨줄 필요 X
		
		/* 한 건의 주문에서 사용자가 주문한 메뉴 및 수량들을 꺼내어 TBL_ORDER_MENU 테이블(자식 테이블)에 insert(selectKey 활용) */
		List<OrderMenuDTO> orderMenuList = order.getOrderMenuList();
		
		int orderMenuResult = 0;
		for(OrderMenuDTO orderMenu : orderMenuList) {
			orderMenuResult += orderMapper.registOrderMenu(orderMenu);
		}
		
		int result = 0;
		
		if(orderResult > 0 && orderMenuResult == orderMenuList.size()) {
			result = 1;
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		
		return result;
	}

	@Override
	public int registOrder2(OrderDTO order) {
		
		/* 스프링 트랜잭션에 대한 설정 정보(전파행위 옵션, 격리 레벨)를 담고 있는 객체 */
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();		// 전파행위, 격리레벨을 가지고 있는 객체
		
		/*
		 * 전파행위 옵션(PropagationBehavior)
		 * REQUIRED : 진행 중인 트랜잭션이 있으면 현재 메소드를 그 트랜잭션에서 실행하되 그렇지 않은 경우 새 트랜잭션을 시작해서 실행한다.
		 * REQUIRED_NEW : 항상 새 트랜잭션으로 시작해 메소드를 실행하고 진행중인 트랜잭션이 있으면 잠시 중단시킨다.
		 * SUPPORTS : 진행중인 트랜잭션이 있으면 현재 메소드를 그 트랜잭션 내에서 실행하되, 그렇지 않을 경우 트랜잭션 없이 실행한다.
		 * NOT_SUPPORTED : 트랜잭션 없이 현재 메소드를 실행하고 진행중인 트랜잭션이 있으면 잠시 중단한다.
		 * MANDATORY : 반드시 트랜잭션을 걸고 현재 메소드를 실행하되 진행중인 트랜잭션이 있으면 예외를 던진다.
		 * NEVER : 반드시 트랜잭션 없이 현재 메소드를 실행하되 진행중인 트랜잭션이 있으면 예외를 던진다.
		 * NESTED : 진행중인 트랜잭션이 있으면 현재 메소드를 이 트랜잭션의 중첩트랜잭션 내에서 실행한다. 진행중인 트랜잭션이 없으면
		 *          새 트랜잭션을 실행한다.
		 *          배치 실행 도중 처리 할 업무가 백만개라고 하면 10만개씩 끊어서 커밋하는 경우 중간에 잘못 되어도 중첩 트랜잭션을
		 *          롤백하면 전체가 아닌 10만개만 롤백된다.
		 *          세이브포인트를 이용하는 방식이다. 따라서 세이브포인트를 지원하지 않는 경우 사용 불가능하다.
		 */

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		/*
		 * 격리 레벨(IsolationLevel)
		 */
	      
	    /* 격리레벨 (동시성)
	     * DEFAULT (v) : DB의 기본 격리 수준을 사용한다. 대다수는 READ_COMMITTED가 기본 격리 수준이다. (성능 높음, 안정성 낮음, 사용자 높음)
	     * 
	     * READ_UNCOMMITTED : 다른 트랜젝션이 아직 커밋하지 않은 값을 다른 트랜젝션이 읽을 수 있다. (격리 X)
	     *                    따라서 오염된 값을 읽거나, 재현 불가능한 값 읽기, 허상 읽기 등의 문제가 발생할 수 있다.
	     *                    
	     * READ_COMMITTED (v) : 트랜젝션이 다른 트랜젝션에서 커밋한 값만 읽을 수 있다.
	     *                  오염된 값 읽기 문제는 해결되지만 재현 불가능한 값 읽기 및 허상읽기(insert)는 여전히 발생할 수 있다. (중간 단계)
	     *                  
	     * REPEATABLE_READ : 트랜젝션이 어떤 필드를 여러 번 읽어도 동일한 값을 읽도록 보장한다.
	     *                   트랜젝션이 지속되는 동안에는 다른 트랜젝션이 해당 필드를 변경할 수 없다.
	     *                   오염된 값 읽기, 재현 불가능한 값 읽기는 해결되지만 허상읽기는 여전히 발생할 수 있다.
	     *                   
	     * SERIALIZABLE (v) : 트랜젝션이 테이블을 여러 번 읽어도 정확히 동일한 로우를 읽도록 보장한다. 트랜젝션이 지속되는 동안에는 (성능 낮음(속도 낮음), 안정성 높음, 사용자 낮음 / 가장 쎔)
	     *                다른 트랜젝션이 해당 테이블에 삽입, 수정, 삭제를 할 수 없다.
	     *                동시성 문제는 모두 해소되지만 성능은 현저하게 떨어진다.
	     * 
	     * 
	     * 오염된 값 : 하나의 트랜젝션이 데이터를 변경 후 잠시 기다리는 동안 다른 트랜젝션이 데이터를 읽게 되면,
	     *          격리레벨이 READ_UNCOMMITTED인 경우 아직 변경 후 커밋하지 않은 재고값을 그대로 읽게 된다.
	     *          그러나 처음 트랜젝션이 데이터를 롤백하게 되면 다른 트랜젝션이 읽은 값은 더 이상 유효하지 않은 일시적인 값이 된다.
	     *          이것을 오염된 값라고 한다.
	     *          
	     * 재현 불가능한 값 읽기 : 처음 트랜젝션이 데이터를 수정하면 수정이 되고 아직 커밋되지 않은 로우에 수정 잠금을 걸어둔 상태에다.
	     *                  결국 다른 트랜젝션은 이 트랜젝션이 커밋 혹은 롤백 되고 수정잠금이 풀릴 때까지 기다렸다가 읽을 수 밖에 없게 된다.
	     *                  하지만 다른 로우에 대해서는 또 다른 트랜젝션이 데이터를 수정하고 커밋을 하게 되면
	     *                  가장 처음 동작한 트랜젝션이 데이터를 커밋하고 다시 조회를 한 경우 처음 읽은 그 값이 아니게 된다.
	     *                  이것이 재현 불가능한 값이라고 한다.
	     *                  
	     * 허상 읽기 : 처음 트랜젝션이 테이블에서 여러 로우를 읽은 후 이후 트랜젝션이 같은 테이블의 로우를 추가하는 경우
	     *          처음 트랜젝션이 같은 테이블을 다시 읽으면 자신이 처음 읽었을 때와 달리 새로 추가 된 로우가 있을 것이다.
	     *          이것을 허상 읽기라고 한다. (재현 불가능한 값 읽기와 유사하지만 허상 읽기는 여러 로우가 추가되는 경우를 말한다.) 
	     */ 
		
		def.setIsolationLevel(DefaultTransactionDefinition.ISOLATION_SERIALIZABLE);
		
		/* 스프링 설정(spring-context에서 DataSourceTransactionManager를 빈으로 추가 및 의존성 주입 받기) (v)*/
//		중간 단계 만들었다~~~!~!
		TransactionStatus status = transactionManager.getTransaction(def);	// Bean으로 설정해주자(격리레벨(이전 내용)에 대해 알고 있는 객체)
		
		OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
		
		/* 한 건의 주문을 TBL_ORDER 테이블(부모 테이블)에 insert */
		int orderResult = orderMapper.registOrder(order);
		
		/* 한 건의 주문에서 사용자가 메뉴 및 수량들을 꺼내어 TBL_ORDER_MENU 테이블(자식 테이블)에  insert(selectKey 활용) */
		List<OrderMenuDTO> orderMenuList = order.getOrderMenuList();
		
		int orderMenuResult = 0;
		for (OrderMenuDTO orderMenu : orderMenuList) {
			orderMenuResult += orderMapper.registOrderMenu(orderMenu);
		}
		
		int result = 0;
		if(orderResult > 0 && orderMenuResult == orderMenuList.size()) {
			result = 1;
			transactionManager.commit(status);		// status 던져
		} else {
			transactionManager.rollback(status);	// status 던져
		}
		
		return result;
	}
}
