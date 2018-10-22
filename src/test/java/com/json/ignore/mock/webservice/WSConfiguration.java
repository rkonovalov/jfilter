package com.json.ignore.mock.webservice;
  
import com.json.ignore.EnableJsonFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"com.json.ignore", "com.json.ignore.mock.webservice"})
@EnableWebMvc
@EnableJsonFilter
public class WSConfiguration extends WebMvcConfigurerAdapter {

}