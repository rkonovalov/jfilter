package com.jfilter.mock.webservice;

import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.DynamicFilter;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockClassesHelper;
import com.jfilter.mock.MockUser;
import com.jfilter.util.FilterUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController
@DynamicFilter(DynamicSessionFilter.class)
public class WSClassDynamicFilter {
    public static final String MAPPING_NOT_NULL_DYNAMIC_CLASS_FILTER = "/dynamic-class/notNullFilter";
    public static final String MAPPING_DYNAMIC_CLASS_SESSION_ATTRIBUTE_FIELDS = "/dynamic-class/sessionAttributeFields";

    @RequestMapping(value = MAPPING_NOT_NULL_DYNAMIC_CLASS_FILTER,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser notNullFilter(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClassesHelper.getUserMock();
    }

    @RequestMapping(value = MAPPING_DYNAMIC_CLASS_SESSION_ATTRIBUTE_FIELDS,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser sessionAttributeFields(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password) {

        FilterUtil.useFilter(session, FilterFields.getFieldsBy(Arrays.asList("id", "password", "email")));
        return MockClassesHelper.getUserMock();
    }

}