package com.ssafy.indieAndBob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.indieAndBob.jwt.interceptor.JwtInterceptor;

@SpringBootApplication
@EnableScheduling
public class IndieAndBobApplication implements WebMvcConfigurer {
	
//	@Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }

	public static void main(String[] args) {
		SpringApplication.run(IndieAndBobApplication.class, args);
	}

	@Autowired
	private JwtInterceptor jwtInterceptor;
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtInterceptor).addPathPatterns("/user/**")
//		        .excludePathPatterns(java.util.Arrays.asList("/user/join/**"));
//	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				.exposedHeaders("jwt-auth-token");
	}
}
