package com.jfilter.mock.webservice;

import com.jfilter.filter.field.FieldFilterSetting;
import com.jfilter.filter.strategy.SessionStrategy;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
        @FieldFilterSetting(fields = {"id", "password"})
})

@SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
        @FieldFilterSetting(fields = {"email"})
})
public class WSClassStrategyMultiple {
    public static final String MAPPING_SIGN_IN_STRATEGY_MULTIPLE = "/strategy-multiple/customers/signIn";

    @RequestMapping(value = MAPPING_SIGN_IN_STRATEGY_MULTIPLE,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

}