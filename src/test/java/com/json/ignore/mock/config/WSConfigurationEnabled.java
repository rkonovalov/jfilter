package com.json.ignore.mock.config;
  
import com.json.ignore.EnableJsonFilter;
import com.json.ignore.advice.FilterRegister;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.List;

@RunWith(SpringRunner.class)
@Configuration
@ComponentScan({"com.json.ignore", "com.json.ignore.advice", "com.json.ignore.mock.webservice"})
@EnableScheduling
@EnableJsonFilter
public class WSConfigurationEnabled extends WebMvcConfigurerAdapter {
    @Autowired
    private  FilterRegister filterRegister;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if(filterRegister != null)
        filterRegister.configureMessageConverters(converters);
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter());
        super.configureMessageConverters(converters);
    }
}