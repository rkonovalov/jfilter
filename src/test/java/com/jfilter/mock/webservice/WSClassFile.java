package com.jfilter.mock.webservice;

import com.jfilter.mock.MockClasses;
import com.jfilter.filter.file.FileFilterSetting;
import com.jfilter.mock.MockUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FileFilterSetting(fileName = "configMockWebService.xml")
public class WSClassFile {
    public static final String MAPPING_SIGN_IN_FILE = "/file/customers/signIn";

    @RequestMapping(value = MAPPING_SIGN_IN_FILE,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

}