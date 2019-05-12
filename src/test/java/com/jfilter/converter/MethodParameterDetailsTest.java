package com.jfilter.converter;

import com.jfilter.filter.FilterFields;
import com.jfilter.mock.MockMethods;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;

public class MethodParameterDetailsTest {

    private MethodParameter methodParameter;
    private int methodParameterHash;

    @Before
    public void init() {
        methodParameter = MockMethods.mockIgnoreSettingsMethod();
        methodParameterHash = methodParameter.getMethod().hashCode();
    }

    @Test
    public void testGetMethodHashCode() {
        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertEquals(methodParameterHash, methodParameterDetails.getMethodHashCode());
    }

    @Test
    public void testEquals() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testEqualsCurrentMethod() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails1);
    }

    @Test
    public void testNotEquals() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_XML, null);
        Assert.assertNotEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testNullNotEquals() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertNotEquals(methodParameterDetails1, null);
    }

    @Test
    public void testEqualsHashCode() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, null, null);
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, null, null);
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testNotEqualsHashCode() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(MockMethods.dynamicSessionFilter(), null, null);
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, null, null);
        Assert.assertNotEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testEqualsMediaType() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, null);
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, null);
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testNotEqualsMediaType() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_JSON, null);
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, MediaType.APPLICATION_XML, null);
        Assert.assertNotEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testEqualsFilterFields() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, null, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, null, new FilterFields());
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testNotEqualsFilterFields() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(methodParameter, null, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(methodParameter, null, null);
        Assert.assertNotEquals(methodParameterDetails1, methodParameterDetails2);
    }


}
