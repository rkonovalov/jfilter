package com.json.ignore.mock.webservice;

import com.json.ignore.filter.file.FileFilterSetting;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FileFilterSetting(fileName = "configMockWebService.xml")
public class WSClassFile {

    @RequestMapping(value = "/file/customers/signIn",
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

}