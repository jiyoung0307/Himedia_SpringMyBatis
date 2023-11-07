package com.greedy.section02.mapperscan.run;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.greedy.section02.mapperscan.model.dto.MenuDTO;
import com.greedy.section02.mapperscan.model.service.MenuService;


public class Application {

	public static void main(String[] args) {
		
		/* 
		 * 이번 section02예제에서는 MenuDAOImpl을 없애고 mapperscan을 활용해서 
		 * mapper를 spring-context.xml에서 등록할 것이다. 
		 */
		ApplicationContext context =
				new GenericXmlApplicationContext("com/greedy/section02/mapperscan/config/spring-context.xml");
		
		System.out.println("전체 메뉴 조회하기");
		
		MenuService menuService = context.getBean("menuService", MenuService.class);
		
		List<MenuDTO> menuList = menuService.selectMenuList();
		for (MenuDTO menu : menuList) {
			System.out.println(menu);
		}
	}

}
