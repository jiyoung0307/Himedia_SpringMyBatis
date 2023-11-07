package com.greedy.section01.connection.javaconfig;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public interface MenuDAO {

	List<MenuDTO> selectMenuList(SqlSessionTemplate sqlSession);

}
