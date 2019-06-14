package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.utils.AssertAnnotations;
import com.ega.datarepositorysevice.utils.ReflectTool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

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
                ServiceInfo.class, "getLicense", JsonProperty.class);
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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ServiceInfo validServiceInfo = new ServiceInfo("string", "string", "string", "string", "string");
        Set<ConstraintViolation<ServiceInfo>> violations = validator.validate(validServiceInfo);

        Assert.assertTrue(violations.isEmpty());

        ServiceInfo wrongServiceInfo = new ServiceInfo(null, "string", "string", "string", "string");

        violations = validator.validate(wrongServiceInfo);
        for(ConstraintViolation<ServiceInfo> violation:violations){
            Assert.assertEquals("version",violation.getPropertyPath().toString());
        }


    }

}
