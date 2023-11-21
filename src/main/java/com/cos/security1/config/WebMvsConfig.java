package com.cos.security1.config;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Import(WebMvsConfig.LogAspect.class)
public class WebMvsConfig implements WebMvcConfigurer{

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new  MustacheViewResolver();
		resolver.setCharset("utf-8");
		resolver.setContentType("text/html; charset=utf-8");
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		
		registry.viewResolver(resolver);
	}
	
	
	@Aspect
	@Slf4j
	public static class LogAspect{
		@Before("execution(* com.cos.security1..*(..))")
		public void doBefore(JoinPoint joinpoint) {
			log.info("[before] {}", joinpoint.getSignature());
		}
	}
}
