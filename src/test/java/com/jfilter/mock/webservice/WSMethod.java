package com.jfilter.mock.webservice;

import com.jfilter.filter.*;
import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.mock.MockClasses;
import com.jfilter.mock.MockUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class WSMethod {
    public static final String MAPPING_SIGN_IN_SINGLE_ANNOTATION = "/customers/signInSingleAnnotation";
    public static final String MAPPING_SIGN_IN_KEEP_SINGLE_ANNOTATION = "/customers/signInKeepSingleAnnotation";
    public static final String MAPPING_SIGN_IN_SINGLE_ANNOTATION_XML = "/customers/signInSingleAnnotationXml";
    public static final String MAPPING_SIGN_IN_FILE_ANNOTATION = "/customers/signInFileAnnotation";
    public static final String MAPPING_SIGN_IN_FILE_KEEP_ANNOTATION = "/customers/signInFileKeepAnnotation";
    public static final String MAPPING_SIGN_IN_FILE_DEFAULT_STRATEGY = "/customers/signInFileDefaultStrategy";
    public static final String MAPPING_SIGN_IN_UN_EXIST_FILE = "/customers/signInUnExistedFile";
    private static final String MAPPING_SIGN_IN_STRATEGY_ANNOTATION = "/customers/signInStrategyAnnotation";
    public static final String MAPPING_SIGN_IN_STRATEGY_DEFAULT = "/customers/signInStrategyDefault";
    public static final String MAPPING_SIGN_IN_DYNAMIC = "/customers/signInDynamic";
    public static final String MAPPING_SIGN_IN_DEFAULT = "/signInDefault";
    public static final String MAPPING_SIGN_IN_WITHOUT_PRODUCE = "/signInWithoutProduce";
    public static final String MAPPING_SIGN_IN_OPTIONAL_SINGLE_ANNOTATION = "/customers/signInOptionalSingleAnnotation";

    @FieldFilterSetting(fields = {"id", "password"})
    @RequestMapping(value = MAPPING_SIGN_IN_SINGLE_ANNOTATION,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInSingleAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
       return MockClasses.getUserMock();
    }

    @FieldFilterSetting(fields = {"id", "email"}, behaviour = FilterBehaviour.KEEP_FIELDS)
    @RequestMapping(value = MAPPING_SIGN_IN_KEEP_SINGLE_ANNOTATION,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInKeepSingleAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @FieldFilterSetting(fields = {"id", "password"})
    @RequestMapping(value = MAPPING_SIGN_IN_SINGLE_ANNOTATION_XML,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE})
    public MockUser signInSingleAnnotationXml(@RequestParam("email") String email, @RequestParam("password") String password) {
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

    @DynamicFilter(DynamicSessionFilter.class)
    @RequestMapping(value = MAPPING_SIGN_IN_DYNAMIC,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInDynamic(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @FieldFilterSetting(fields = {"id", "password"})
    @RequestMapping(value = MAPPING_SIGN_IN_WITHOUT_PRODUCE,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public MockUser signInWithoutProduce(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }


    @FileFilterSetting(fileName = "config_with_behaviour.xml")
    @RequestMapping(value = MAPPING_SIGN_IN_FILE_KEEP_ANNOTATION,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public MockUser signInFileKeepAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
        return MockClasses.getUserMock();
    }

    @FieldFilterSetting(fields = {"id", "password"})
    @RequestMapping(value = MAPPING_SIGN_IN_OPTIONAL_SINGLE_ANNOTATION,
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Optional<MockUser> signInOptionalSingleAnnotation(@RequestParam("email") String email, @RequestParam("password") String password) {
        return Optional.of(MockClasses.getUserMock());
    }
}