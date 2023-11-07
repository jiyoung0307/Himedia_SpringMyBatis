package com.greedy.section01.connection.xmlconfig;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	
//	@Autowired
	private final MenuDAO menuDAO; // 다음 단계의 계층의 빈 @Autowired 됨 (다음단계에서 계층 많이 안생기게 하기 위해)
//	@Autowired
	private final SqlSessionTemplate sqlSession;
	
	// 생성자에서 @Autowired는 변수를 추가해주어야 한다.
	@Autowired
	public MenuServiceImpl(MenuDAO menuDAO, SqlSessionTemplate sqlSession) {
		this.menuDAO = menuDAO;
		this.sqlSession = sqlSession;
	}
	
	// 동적 바인딩에 의해서 얘가 실행
	@Override
	public List<MenuDTO> selectMenuList() {
		System.out.println("연결 객체 잘 만들어 졌나 : " + sqlSession);
		
		return menuDAO.selectMenuList(sqlSession);
	}
}
