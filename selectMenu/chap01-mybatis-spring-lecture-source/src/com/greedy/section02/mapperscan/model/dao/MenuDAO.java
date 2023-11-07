package com.greedy.section02.mapperscan.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.greedy.section02.mapperscan.model.dto.MenuDTO;

public interface MenuDAO {

	List<MenuDTO> selectMenuList(SqlSessionTemplate sqlSession);

}
