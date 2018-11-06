package com.json.ignore.mock.webservice;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.file.FileFilterSetting;
import com.json.ignore.filter.strategy.SessionStrategy;
import com.json.ignore.mock.MockClasses;
import com.json.ignore.mock.MockUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WSMethod {
    public static final String MAPPING_SIGN_IN_SINGLE_ANNOTATION = "/customers/signInSingleAnnotation";
    public static final String MAPPING_SIGN_IN_FILE_ANNOTATION = "/customers/signInFileAnnotation";
    public static final String MAPPING_SIGN_IN_FILE_DEFAULT_STRATEGY = "/customers/signInFileDefaultStrategy";
    public static final String MAPPING_SIGN_IN_UN_EXIST_FILE = "/customers/signInUnExistedFile";
    public static final String MAPPING_SIGN_IN_STRATEGY_ANNOTATION = "/customers/signInStrategyAnnotation";
    public static final String MAPPING_SIGN_IN_STRATEGY_DEFAULT = "/customers/signInStrategyDefault";
    public static final String MAPPING_SIGN_IN_DEFAULT = "/signInDefault";

    @FieldFilterSetting(fields = {"id", "password"})
    @RequestMapping(value = MAPPING_SIGN_IN_SINGLE_ANNOTATION,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInSingleAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
       return MockClasses.getUserMock();
    }

    @FileFilterSetting(fileName = "configMockWebService.xml")
    @RequestMapping(value = MAPPING_SIGN_IN_FILE_ANNOTATION,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInFileAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"id", "password"})
    })
    @RequestMapping(value = MAPPING_SIGN_IN_STRATEGY_ANNOTATION,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInStrategyAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @SessionStrategy(ignoreFields = {
            @FieldFilterSetting(fields = {"id", "password", "email"})
    })
    @RequestMapping(value = MAPPING_SIGN_IN_STRATEGY_DEFAULT,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInStrategyDefault(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @FileFilterSetting(fileName = "unExistFile.xml")
    @RequestMapping(value = MAPPING_SIGN_IN_UN_EXIST_FILE,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInUnExistedFile(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @FileFilterSetting(fileName = "config_default_strategy.xml")
    @RequestMapping(value = MAPPING_SIGN_IN_FILE_DEFAULT_STRATEGY,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInDefaultStrategyFile(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @RequestMapping(value = MAPPING_SIGN_IN_DEFAULT,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInDefault(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }
}