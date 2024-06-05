package com.tjoeun.config;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tjoeun.beans.UserBean;
import com.tjoeun.interceptor.CheckLoginInterceptor;
import com.tjoeun.interceptor.CheckWriterInterceptor;
import com.tjoeun.interceptor.TopMenuInterceptor;
import com.tjoeun.mapper.BoardMapper;
import com.tjoeun.mapper.CommentMapper;
import com.tjoeun.mapper.TopMenuMapper;
import com.tjoeun.mapper.UserMapper;
import com.tjoeun.service.BoardService;
import com.tjoeun.service.TopMenuService;

// @Configuration : Spring MVC 프로젝트에 관련된 설정을 하는 클래스
// servlet-context.xml 역할을 함
@Configuration
// @EnableWebMvc : Controller 어노테이션이 설정된 클래스를 Controller 로 등록함
@EnableWebMvc
// @ComponentScan() : scan 할 package 를 지정함  
@ComponentScan("com.tjoeun.controller")
@ComponentScan("com.tjoeun.dao")
@ComponentScan("com.tjoeun.service")
// properties 파일 인식하기
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer {
	
	@Value("${db.classname}")
	private String db_classname;
	
	@Value("${db.url}")
	private String db_url;
	
	@Value("${db.username}")
	private String db_username;
	
	@Value("${db.password}")
	private String db_password;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@Autowired
	private BoardService boardService;
	
	// Session Scope 에 생성된 loginUserBean 주입 받기
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	

	// Controller 의 메소드가 반환하는 View page 의 접두사 접미사 등록하기
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// 정적 파일의 경로를 mapping 함
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	
	// DataBase 접속 정보 관리하는 객체 설정하기
	// (관리하는 클래스의 객체를 springframework 가 자동으로 생성함)
	//             ㄴ BasicDataSource           ㄴ spring container
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);
		return source;
	}
	
	// query 문, 접속정보 등을 관리하는 객체 설정하기
	//                                   SqlSessionFactory
  @Bean
  public SqlSessionFactory factory(BasicDataSource source) throws Exception{
  	SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
  	factoryBean.setDataSource(source);
  	SqlSessionFactory factory = factoryBean.getObject();
  	return factory;
  }
  
  // query 문을 실행하기 위한 객체 설정하기
  //                          MapperFactoryBean<매퍼인터페이스>
  // Mapper 등록 : BoardMapper
  // BoardMapper 객체를 springframework 가 
  // spring container 에 자동으로 생성함
  @Bean
  public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception{
  	MapperFactoryBean<BoardMapper> factoryBean = 
  			new MapperFactoryBean<BoardMapper>(BoardMapper.class);
  	factoryBean.setSqlSessionFactory(factory);
  	return factoryBean;  	
  }
  
  // Mapper 등록 : TopMenuMapper
  // TopMenuMapper 객체를 springframework 가 
  // spring container 에 자동으로 생성함 
  @Bean
  public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception{
  	MapperFactoryBean<TopMenuMapper> factoryBean = 
  			new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
  	factoryBean.setSqlSessionFactory(factory);
  	return factoryBean;  	
  }
  
  // Mapper 등록 : UserMapper
  // UserMapper 객체를 springframework 가 
  // spring container 에 자동으로 생성함 
  @Bean
  public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception{
  	MapperFactoryBean<UserMapper> factoryBean = 
  			new MapperFactoryBean<UserMapper>(UserMapper.class);
  	factoryBean.setSqlSessionFactory(factory);
  	return factoryBean;  	
  }
  @Bean
  public MapperFactoryBean<CommentMapper> getCommentMapper(SqlSessionFactory factory) throws Exception{
  	MapperFactoryBean<CommentMapper> factoryBean = 
  			new MapperFactoryBean<CommentMapper>(CommentMapper.class);
  	factoryBean.setSqlSessionFactory(factory);
  	return factoryBean;  	
  }
  
  // interceptor 등록하기
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
  	WebMvcConfigurer.super.addInterceptors(registry);
  	TopMenuInterceptor topMenuInterceptor = 
  			new TopMenuInterceptor(topMenuService, loginUserBean);
  	InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
  	reg1.addPathPatterns("/**");
  	
  	CheckLoginInterceptor checkLoginInterceptor = 
  			new CheckLoginInterceptor(loginUserBean);
  	InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor);
  	reg2.addPathPatterns("/user/modify", "/user/logout", "/board/*");
  	reg2.excludePathPatterns("/board/main");
  	
  	CheckWriterInterceptor checkWriterInterceptor = 
  			new CheckWriterInterceptor(loginUserBean, boardService);
  	InterceptorRegistration reg3 = registry.addInterceptor(checkWriterInterceptor);
  	reg3.addPathPatterns("/board/modify", "/board/delete");
  	
  }
  
  // error_message.properties 파일 등록하기
  @Bean 
  public ReloadableResourceBundleMessageSource messageSource() {
  	ReloadableResourceBundleMessageSource res = 
  			new ReloadableResourceBundleMessageSource();
  	res.setBasenames("/WEB-INF/properties/error_message");
  	return res;
  }
  
  // db.properties 파일과 error_message.properties 파일이 출동하지 않게 함
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
  	return new PropertySourcesPlaceholderConfigurer();
  }
  
  // 파일 업로드를 위한 설정
  @Bean
  public StandardServletMultipartResolver multipartResolver() {
  	return new StandardServletMultipartResolver();
  }
 
  
}





