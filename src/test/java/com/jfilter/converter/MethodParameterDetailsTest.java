package com.jfilter.converter;

import com.jfilter.filter.FilterFields;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

public class MethodParameterDetailsTest {

    @Test
    public void testGetMethodHashCode() {
        MethodParameterDetails methodParameterDetails = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertEquals(100, methodParameterDetails.getMethodHashCode());
    }

    @Test
    public void testEquals() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testEqualsCurrentMethod() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails1);
    }

    @Test
    public void testNotEquals() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(100, MediaType.APPLICATION_XML, null);
        Assert.assertNotEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testNullNotEquals() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        Assert.assertNotEquals(methodParameterDetails1, null);
    }

    @Test
    public void testEqualsHashCode() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, null, null);
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(100, null, null);
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testEqualsMediaType() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, null);
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, null);
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }

    @Test
    public void testEqualsFilterFields() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, null, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(100, null, new FilterFields());
        Assert.assertEquals(methodParameterDetails1, methodParameterDetails2);
    }


}
