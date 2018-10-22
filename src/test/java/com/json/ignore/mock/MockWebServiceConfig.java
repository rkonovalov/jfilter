package com.json.ignore.mock;
  
import com.json.ignore.EnableJsonFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"com.json.ignore"})
@EnableWebMvc
@EnableJsonFilter
public class MockWebServiceConfig extends WebMvcConfigurerAdapter {

}