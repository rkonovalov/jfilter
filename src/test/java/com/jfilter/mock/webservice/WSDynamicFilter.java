package com.jfilter.mock.webservice;

import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.DynamicFilter;
import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockUser;
import com.jfilter.mock.config.MockDynamicNullFilter;
import com.jfilter.util.FilterUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController
public class WSDynamicFilter {
    public static final String MAPPING_NOT_NULL_DYNAMIC_FILTER = "/dynamic/notNullFilter";
    public static final String MAPPING_NULL_DYNAMIC_FILTER = "/dynamic/nullFilter";
    public static final String MAPPING_DYNAMIC_SESSION_ATTRIBUTE_FIELDS = "/dynamic/sessionAttributeFields";
    public static final String MAPPING_DYNAMIC_REQUEST_ATTRIBUTE_FIELDS = "/dynamic/requestAttributeFields";

    @DynamicFilter(DynamicSessionFilter.class)
    @RequestMapping(value = MAPPING_NOT_NULL_DYNAMIC_FILTER,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser notNullFilter(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @DynamicFilter(MockDynamicNullFilter.class)
    @RequestMapping(value = MAPPING_NULL_DYNAMIC_FILTER,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser nullFilter(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @DynamicFilter(DynamicSessionFilter.class)
    @RequestMapping(value = MAPPING_DYNAMIC_SESSION_ATTRIBUTE_FIELDS,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser sessionAttributeFields(HttpSession session,  @RequestParam("email") String email, @RequestParam("password") String password) {

        FilterUtil.useFilter(session, FilterFields.getFieldsBy(Arrays.asList("id", "password", "email")));
        return MockClasses.getUserMock();
    }

    @DynamicFilter(DynamicSessionFilter.class)
    @RequestMapping(value = MAPPING_DYNAMIC_REQUEST_ATTRIBUTE_FIELDS,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser sessionAttributeFields(HttpServletRequest request, @RequestParam("email") String email, @RequestParam("password") String password) {

        FilterUtil.useFilter(request, FilterFields.getFieldsBy(Arrays.asList("id", "password", "email")));
        return MockClasses.getUserMock();
    }

}