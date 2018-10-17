package com.json.ignore.strategy;

import com.json.ignore.AnnotationUtil;
import com.json.ignore.FieldIgnoreSetting;
import com.json.ignore.FieldIgnoreProcessor;
import com.json.ignore.FieldIgnoreSettings;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import java.util.Arrays;

public class FieldIgnore extends Ignore {
    private FieldIgnoreSetting[] setting;

    public FieldIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        setting = AnnotationUtil.getSettingAnnotations(methodParameter.getMethod());
    }

    @Override
    public void jsonIgnore(Object object) throws IllegalAccessException {
        FieldIgnoreProcessor jsonIgnore = new FieldIgnoreProcessor(Arrays.asList(setting));
        jsonIgnore.ignoreFields(object);
    }

    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), FieldIgnoreSetting.class, FieldIgnoreSettings.class);
    }
}
