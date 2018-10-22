package com.json.ignore.mock;

import com.json.ignore.filter.file.FileFilterSetting;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FileFilterSetting(fileName = "configMockWebService.xml")
public class MockWebServiceClassAnnotated {

    //@FileFilterSetting(fileName = "configMockWebService.xml")
    @RequestMapping(value = "/class-annotated/customers/signInFileAnnotation",
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInFileAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

}