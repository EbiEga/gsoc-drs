package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.BundleObjectType;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BundleObjectUnitTest {
    @Test
    public void typeAnnotations() {
        AssertAnnotations.assertType(
                ServiceInfo.class, Entity.class, Table.class, JsonInclude.class);
    }

    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(BundleObject.class, "id", Id.class, GeneratedValue.class);
        AssertAnnotations.assertField(BundleObject.class, "name", Column.class, NotEmpty.class, Pattern.class);
        AssertAnnotations.assertField(BundleObject.class, "type", Column.class, Enumerated.class, NotNull.class);
        AssertAnnotations.assertField(BundleObject.class, "drsUri");
        AssertAnnotations.assertField(BundleObject.class, "bundle", JoinColumn.class, ManyToOne.class);
    }

    @Test
    public void methodAnnotations() {
        AssertAnnotations.assertMethod(
                BundleObject.class, "getId", JsonProperty.class);
        AssertAnnotations.assertMethod(
                BundleObject.class, "getName", JsonProperty.class);
        AssertAnnotations.assertMethod(
                BundleObject.class, "getType", JsonProperty.class);
        AssertAnnotations.assertMethod(
                BundleObject.class, "getDrsUri", JsonProperty.class);
        AssertAnnotations.assertMethod(
                BundleObject.class, "getBundle", JsonIgnore.class);
    }

    @Test
    public void entity() {
        Entity entity
                = ReflectTool.getClassAnnotation(BundleObject.class, Entity.class);
        Assert.assertEquals("", entity.name());
    }

    @Test
    public void table() {
        Table table
                = ReflectTool.getClassAnnotation(BundleObject.class, Table.class);
        Assert.assertEquals("bundle_object", table.name());
    }

    @Test
    public void testConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        BundleObject validBundleObject = new BundleObject(Long.parseLong("1"), "name", BundleObjectType.OBJECT, null, null);
        Set<ConstraintViolation<BundleObject>> violations = validator.validate(validBundleObject);

        Assert.assertTrue(violations.isEmpty());

        BundleObject wrongBundleObject = new BundleObject(Long.parseLong("1"), "name", BundleObjectType.OBJECT, null, null);
        violations = validator.validate(wrongBundleObject);
        List<String> constraintProperties = Arrays.asList("type", "name");

        for (ConstraintViolation<BundleObject> violation : violations) {
            String property = violation.getPropertyPath().toString();
            Assert.assertTrue(constraintProperties.contains(property));
        }

    }

    @Test
    public void testNamePattern() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        BundleObject wrongBundleObject = new BundleObject(null, "as/ada/", BundleObjectType.OBJECT, null, null);
        Set<ConstraintViolation<BundleObject>> violations = validator.validate(wrongBundleObject);
        for (ConstraintViolation<BundleObject> violation : violations) {
            System.out.println(violation.toString());
        }

    }
}

