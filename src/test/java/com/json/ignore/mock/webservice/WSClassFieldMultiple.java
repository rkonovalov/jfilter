package com.json.ignore.mock.webservice;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldFilterSetting(fields = {"id"})
@FieldFilterSetting(fields = {"password"})
public class WSClassFieldMultiple {
    public static final String MAPPING_SIGN_IN_FIELD_MULTIPLE = "/field-multiple/customers/signIn";


    @RequestMapping(value = MAPPING_SIGN_IN_FIELD_MULTIPLE,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

}