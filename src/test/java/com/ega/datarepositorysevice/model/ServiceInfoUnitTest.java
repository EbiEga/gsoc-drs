package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.utils.AssertAnnotations;
import com.ega.datarepositorysevice.utils.ReflectTool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

public class ServiceInfoUnitTest {
    @Test
    public void typeAnnotations() {

        AssertAnnotations.assertType(
                ServiceInfo.class, Entity.class, Table.class, JsonInclude.class);
    }

    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(ServiceInfo.class, "version", Id.class, NotEmpty.class);
        AssertAnnotations.assertField(ServiceInfo.class, "description");
        AssertAnnotations.assertField(ServiceInfo.class, "title");
        AssertAnnotations.assertField(ServiceInfo.class, "license");
        AssertAnnotations.assertField(ServiceInfo.class, "contact");

    }

    @Test
    public void methodAnnotations() {
        AssertAnnotations.assertMethod(
                ServiceInfo.class, "getVersion", JsonProperty.class);
        AssertAnnotations.assertMethod(
                ServiceInfo.class, "getDescription", JsonProperty.class);
        AssertAnnotations.assertMethod(
                ServiceInfo.class, "getTitle", JsonProperty.class);
        AssertAnnotations.assertMethod(
                ServiceInfo.class, "getLicence", JsonProperty.class);
        AssertAnnotations.assertMethod(
                ServiceInfo.class, "getContact", JsonProperty.class);
    }

    @Test
    public void entity() {
        Entity entity
                = ReflectTool.getClassAnnotation(ServiceInfo.class, Entity.class);
        Assert.assertEquals("", entity.name());
    }

    @Test
    public void table() {
        Table table
                = ReflectTool.getClassAnnotation(ServiceInfo.class, Table.class);
        Assert.assertEquals("service_info", table.name());
    }

    @Test
    public void testConstraints(){

    }

}
