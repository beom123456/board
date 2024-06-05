package com.tjoeun.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
  web.xml 역할을 하는 클래스
    ㄴ WebApplicationInitializer 인터페이스를 구현함 
*/
/*
public class SpringConfigClass implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// System.out.println("onStartup");
		// Spring MVC 프로젝트 설정을 위해 작성하는 클래스의 객체를 생성함
		AnnotationConfigWebApplicationContext servletAppContext = 
				new AnnotationConfigWebApplicationContext();		
		servletAppContext.register(ServletAppContext.class);
				
		// 요청 발생시 이 요청을 처리하는 servlet 을
		// DispatcherServlet 으로 설정함
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		ServletRegistration.Dynamic servlet = 
				servletContext.addServlet("dispatcher", dispatcherServlet);
		
		// 부가 설정하기
		servlet.setLoadOnStartup(1);		
		servlet.addMapping("/");
		
		// Bean 을 정의하는 클래스를 지정함
		AnnotationConfigWebApplicationContext rootAppContext = 
				new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		ContextLoaderListener listener = 
				new ContextLoaderListener(rootAppContext);
		
		// 한글 인코딩 설정하기
		FilterRegistration.Dynamic filter = 
				servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		filter.addMappingForServletNames(null, false, "dispatcher");
		
	}
}
*/
public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer{

	// DispatcherServlet 에 mapping 할 주소를 세팅함
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	// Spring MVC 프로젝트 설정을 위한 클래스를 지정함
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletAppContext.class };
	}
	
	// project 에서 사용하는 Bean 들을 정의하기 위한 클래스를 지정함
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootAppContext.class };
	}
	
	// 한글 인코딩 설정하기
	@Override
	protected Filter[] getServletFilters() {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	
	// 첨부파일 내용 설정하기
	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);
		
		/* 
		   50MB <-- 50 X 1024(1KB) X 1024(1KB) <-- 52428800
		   
		   MultipartConfigElement
		    생성자 parameter들
        public MultipartConfigElement(String location,
                                      long maxFileSize,
                                      long maxRequestSize,
                                      int fileSizeThreshold)
		*/
		MultipartConfigElement config1 = 
				new MultipartConfigElement(null, 50 * 1024 * 1024, 50 * 1024 * 1024, 0);
		registration.setMultipartConfig(config1);
		
	}
	
}














