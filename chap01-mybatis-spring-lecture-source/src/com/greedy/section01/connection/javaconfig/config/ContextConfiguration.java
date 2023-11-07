package com.greedy.section01.connection.javaconfig.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.greedy.section01.connection.javaconfig")
@PropertySource("connection-info.properties")
public class ContextConfiguration {
	/* DB 연결 정보도 여기에 작성해주자! (여기는 설정 모음!) */
	
	@Value("${oracle.dev.driver}")
	private String driver;
	
	@Value("${oracle.dev.url}")
	private String url;
	
	@Value("${oracle.dev.username}")
	private String username;
	
	@Value("${oracle.dev.password}")
	private String password;
	
	/* 어떤 DBMS, 어떤 계정과 연동할지를 빈 객체로 등록 */
	/* 2. commons-dbcp 라이브러리 추가 (요즘 쓰는 hikari cp도 참조할 것) */
	// mybatis에서 연결해줬던 방식과 비교하면서 공부하기!@!
	
	/* 3. commons-dbcp와 의존관계에 있는 라이브러리인 commons-pool 라이브러리도 추가 */
	@Bean(destroyMethod="close")		// 안쓰면 DB와의 연결을 끊어라
	public BasicDataSource dataSource() {	// close의 메소드
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDefaultAutoCommit(false); 	// 수동 커밋을 위해 작성
		
		return dataSource;
	}
	
	// scope를 적어주지 않았으니 디폴트는 singleton
	@Bean
	public SqlSessionFactory sqlSessionFactory(ApplicationContext context) throws Exception {
		
		/* 4. SqlSessionFactoryBean은 mybatis-spring 라이브러리를 추가(2.0버전) 해 주어야 한다.(mybatis-config.xml도 추가) */
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());		// 설계도 환경 정보 (factoryBean 생성)
		
		factoryBean.setConfigLocation(context.getResource("com/greedy/section01/connection/javaconfig/config/mybatis-config.xml"));	// 설계도 부가정보
		
		return factoryBean.getObject(); 	// 예외 처리해줘라~ (throws)
	}
	
	// 연결 객체(SqlSessionConplete)
	// SqlSession = SqlSessionTemplate
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(ApplicationContext context) throws Exception {
		
		/* 5. spring-jdbc 라이브러리 추가 */
		/* 6. spring-tx 라이브러리 추가 (5.3.20 version)*/
		return new SqlSessionTemplate(sqlSessionFactory(context));		// 예외처리 (공장 연결객체 / throws)
		
		
		
	}
}	

