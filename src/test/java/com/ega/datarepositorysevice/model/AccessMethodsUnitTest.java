package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.utils.AssertAnnotations;
import com.ega.datarepositorysevice.utils.ReflectTool;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.util.*;

public class AccessMethodsUnitTest {
    @Test
    public void typeAnnotations() {
        AssertAnnotations.assertType(
                Checksum.class, Entity.class, Table.class, JsonInclude.class);
    }

    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(AccessMethods.class, "accessId", Id.class, GeneratedValue.class);
        AssertAnnotations.assertField(AccessMethods.class, "type", Column.class, Enumerated.class, NotNull.class);
        AssertAnnotations.assertField(AccessMethods.class, "region", Column.class);
        AssertAnnotations.assertField(AccessMethods.class, "accessURL", OneToOne.class,NotNull.class);
        AssertAnnotations.assertField(AccessMethods.class, "object", JoinColumn.class, ManyToOne.class);

    }

    @Test
    public void methodAnnotations() {
        AssertAnnotations.assertMethod(
                AccessMethods.class, "getAccessId", JsonProperty.class);
        AssertAnnotations.assertMethod(
                AccessMethods.class, "getType", JsonProperty.class);
        AssertAnnotations.assertMethod(
                AccessMethods.class, "getRegion", JsonProperty.class);
        AssertAnnotations.assertMethod(
                AccessMethods.class, "getAccessURL", JsonProperty.class);
        AssertAnnotations.assertMethod(
                AccessMethods.class, "getObject", JsonIgnore.class);
    }

    @Test
    public void entity() {
        Entity entity
                = ReflectTool.getClassAnnotation(AccessMethods.class, Entity.class);
        Assert.assertEquals("", entity.name());
    }

    @Test
    public void table() {
        Table table
                = ReflectTool.getClassAnnotation(AccessMethods.class, Table.class);
        Assert.assertEquals("access_methods", table.name());
    }

    @Test
    public void testConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        AccessMethods validAccessMethods = new AccessMethods(Long.parseLong("1"), AccessMethodType.S3, "region", accessURL);
        Set<ConstraintViolation<AccessMethods>> violations = validator.validate(validAccessMethods);

        Assert.assertTrue(violations.isEmpty());

        AccessMethods wrongAccessMethods = new AccessMethods(Long.parseLong("1"), AccessMethodType.S3, "region", accessURL);
        violations = validator.validate(wrongAccessMethods);
        List<String> constraintProperties = Arrays.asList("type");

        for (ConstraintViolation<AccessMethods> violation : violations) {
            String property = violation.getPropertyPath().toString();
            Assert.assertTrue(constraintProperties.contains(property));
        }
    }
}
