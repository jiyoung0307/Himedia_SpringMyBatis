package com.greedy.section02.aop.run;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.greedy.section02.aop.model.dto.OrderDTO;
import com.greedy.section02.aop.model.dto.OrderMenuDTO;
import com.greedy.section02.aop.model.service.OrderService;

public class Application {

	public static void main(String[] args) {
		/* 혼용 방식(programmatic - 수동방식) */
		ApplicationContext context = new GenericXmlApplicationContext("com/greedy/section02/aop/config/spring-context.xml");
		
		OrderService orderService = context.getBean("orderService", OrderService.class);
		
//		System.out.println(orderService);
		
		Scanner sc = new Scanner(System.in);
		
//		1:N의 경우 1에 해당하는 부모 먼저 insert가 진행되어야 한다.(1:N 관계 / 1:1 관계가 있으면 1:N 관계가 더 강하다)
		
		List<OrderMenuDTO> orderMenuList = new ArrayList<>();		// NullPointerException 방지
		orderMenu:
		do {
			System.out.println("===== 트랜잭션 레스토랑 음식 주문 서비스 =====");
			System.out.println("어떤 메뉴를 주문하시겠습니까? (메뉴 코드 입력) : ");
			int menuCode = sc.nextInt();
			
			System.out.println("주문 수량을 입력해 주세요 : ");
			int amount = sc.nextInt();
			
			System.out.println("다른 메뉴를 추가로 주문하시겠습니까?(Y/N) : ");
			sc.nextLine();
			
			char continueYN = sc.nextLine().toUpperCase().charAt(0);
			
			/* 하나의 주문 메뉴에 대해 OrderMenuDTO에 담기 */
			OrderMenuDTO orderMenu = new OrderMenuDTO();		// 반복해서 리스트에 쌓자
			orderMenu.setMenuCode(menuCode);
			orderMenu.setAmount(amount);
			
			/* 하나의 주문에 따른 메뉴들을 orderMenuList에 누적 시키기 */
			orderMenuList.add(orderMenu);
			
//			label 문법(C언어의 goto)을 활용해보자
//			1. 3중 for문
//			2. 반복문에서 사용 가능
			
			switch(continueYN) {
				case 'Y' : break;
				default : break orderMenu;		// java의 Label 문법을 활용해서 Label이 달린 반복문을 한번에 빠져 나가기
			}
			
		} while(true);
		
//		System.out.println("한방에 메뉴 반복문 나왔나");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
		
		OrderDTO order = new OrderDTO();
		order.setDate(dateFormat.format(new java.util.Date(System.currentTimeMillis())));
		order.setTime(timeFormat.format(new java.util.Date(System.currentTimeMillis())));
		
		order.setOrderMenuList(orderMenuList);		// 이 위로는 총 합계 빼고는 가공처리 완료
		
		int result = orderService.registOrder(order);	// DB로 출발해보자
		
		if(result > 0) {
			System.out.println("메뉴 주문에 성공하셨습니다.");
		} else {
			System.out.println("메뉴 주문에 실패하셨습니다.");
		}
		
	}

}
