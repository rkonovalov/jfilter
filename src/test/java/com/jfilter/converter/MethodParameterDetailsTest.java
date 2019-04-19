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
    public void testNotEquals() {
        MethodParameterDetails methodParameterDetails1 = new MethodParameterDetails(100, MediaType.APPLICATION_JSON, new FilterFields());
        MethodParameterDetails methodParameterDetails2 = new MethodParameterDetails(100, MediaType.APPLICATION_XML, null);
        Assert.assertNotEquals(methodParameterDetails1, methodParameterDetails2);
    }


}
