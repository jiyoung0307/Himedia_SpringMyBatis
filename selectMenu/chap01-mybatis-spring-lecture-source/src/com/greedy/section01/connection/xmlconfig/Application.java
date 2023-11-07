package com.greedy.section01.connection.xmlconfig;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Application {

	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext("com/greedy/section01/connection/xmlconfig/config/spring-context.xml");
		
		/* 스프링 트랜잭션 */
		
//		System.out.println("나와 이눔아");
		
		MenuService menuService = context.getBean("menuService", MenuService.class);
		
		List<MenuDTO> menuList = menuService.selectMenuList();
		for(MenuDTO menu : menuList) {
			System.out.println(menu);
		}
	}

}