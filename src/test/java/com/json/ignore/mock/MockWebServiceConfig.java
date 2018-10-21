package com.json.ignore.mock;
  
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"com.json.ignore"})
@EnableWebMvc
public class MockWebServiceConfig extends WebMvcConfigurerAdapter {

}