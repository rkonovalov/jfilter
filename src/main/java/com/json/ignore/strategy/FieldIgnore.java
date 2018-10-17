package com.json.ignore.strategy;

import com.json.ignore.AnnotationUtil;
import com.json.ignore.JsonIgnoreFields;
import com.json.ignore.JsonIgnoreSetting;
import com.json.ignore.JsonIgnoreSettings;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

import java.util.Arrays;

public class FieldIgnore extends JsonIgnore {
    private JsonIgnoreSetting[] setting;

    public FieldIgnore(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        setting = AnnotationUtil.getSettingAnnotations(methodParameter.getMethod());
    }

    @Override
    public void jsonIgnore(Object object) throws IllegalAccessException {
        JsonIgnoreFields jsonIgnore = new JsonIgnoreFields(Arrays.asList(setting));
        jsonIgnore.ignoreFields(object);
    }

    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), JsonIgnoreSetting.class, JsonIgnoreSettings.class);
    }
}
