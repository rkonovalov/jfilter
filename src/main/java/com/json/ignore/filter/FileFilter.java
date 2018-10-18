package com.json.ignore.filter;

import com.json.ignore.AnnotationUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;
import javax.servlet.http.HttpSession;

public class FileFilter extends Filter {
    private FileFilterSetting config;

    public FileFilter(ServerHttpRequest serverHttpRequest, MethodParameter methodParameter) {
        super(serverHttpRequest);
        setConfig(methodParameter);
    }

    public FileFilter(HttpSession session, MethodParameter methodParameter) {
        super(session);
        setConfig(methodParameter);
    }

    private void setConfig(MethodParameter methodParameter) {
        config = AnnotationUtil.getDeclaredAnnotation(methodParameter.getMethod(), FileFilterSetting.class);
    }

    @Override
    public void filter(Object object) throws IllegalAccessException {

    }

    public static boolean isAccept(MethodParameter methodParameter) {
        return AnnotationUtil.isAnnotationExists(methodParameter.getMethod(), FileFilterSetting.class);
    }
}
