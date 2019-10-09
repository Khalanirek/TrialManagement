package com.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/com/configuration/application.properties")
@Import({DbConfiguration.class, SpringSecurityConfig.class})
public class AppConfig implements WebMvcConfigurer{

    @Autowired
    Environment env;

	@Bean
	public CommonsMultipartResolver multipartResolver() {
	  CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	  multipartResolver.setDefaultEncoding("UTF-8");
	  multipartResolver.setMaxUploadSize(20971520); //20MB
	  multipartResolver.setMaxUploadSizePerFile(2097152); //2MB
	  multipartResolver.setMaxInMemorySize(1048576); //1MB
	  return multipartResolver;
	        }

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("WEB-INF/resources/");
    }


}
