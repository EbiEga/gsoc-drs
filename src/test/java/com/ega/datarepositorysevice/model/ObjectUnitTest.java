package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.AccessMethodType;
import com.ega.datarepositorysevice.model.enums.ChecksumType;
import com.ega.datarepositorysevice.utils.AssertAnnotations;
import com.ega.datarepositorysevice.utils.ReflectTool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class ObjectUnitTest {
    @Test
    public void typeAnnotations() {
        AssertAnnotations.assertType(
                Object.class, Entity.class, Table.class, JsonInclude.class);
    }

    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(Object.class, "id", Id.class, GeneratedValue.class);
        AssertAnnotations.assertField(Object.class, "name");
        AssertAnnotations.assertField(Object.class, "size", Column.class, NotNull.class);
        AssertAnnotations.assertField(Object.class, "created", Column.class, NotNull.class, JsonSerialize.class);
        AssertAnnotations.assertField(Object.class, "updated", JsonSerialize.class);
        AssertAnnotations.assertField(Object.class, "version");
        AssertAnnotations.assertField(Object.class, "mime_type");
        AssertAnnotations.assertField(Object.class, "checksums", Column.class, NotEmpty.class, OneToMany.class);
        AssertAnnotations.assertField(Object.class, "accessMethods", Column.class, NotEmpty.class, OneToMany.class);
        AssertAnnotations.assertField(Object.class, "description");
        AssertAnnotations.assertField(Object.class, "aliases", ElementCollection.class);
    }

    @Test
    public void methodAnnotations() {
        AssertAnnotations.assertMethod(
                Object.class, "getId", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getName", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getSize", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getCreated", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getUpdated", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getVersion", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getMime_type", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getChecksums", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getAccessMethods", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getDescription", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Object.class, "getAliases", JsonProperty.class);
    }

    @Test
    public void entity() {
        Entity entity
                = ReflectTool.getClassAnnotation(Object.class, Entity.class);
        Assert.assertEquals("", entity.name());
    }

    @Test
    public void table() {
        Table table
                = ReflectTool.getClassAnnotation(Object.class, Table.class);
        Assert.assertEquals("object", table.name());
    }

    @Test
    public void testConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
        AccessURL accessURL = new AccessURL("http//www.string.com", map);
        AccessMethods accessMethods = new AccessMethods(Long.parseLong("1"), AccessMethodType.FILE, "region", accessURL, null);

        Object validObject = new Object(Long.parseLong("1"), "string", 0, date, date, "string", "application/json",
                Arrays.asList(new Checksum("string", ChecksumType.MD5_Code)), Arrays.asList(accessMethods), "string", null);

        Set<ConstraintViolation<Object>> violations = validator.validate(validObject);

        Assert.assertTrue(violations.isEmpty());

        Object wrongObject = new Object(Long.parseLong("1"), "string", 0, null, date, "string", "application/json",
                Arrays.asList(), Arrays.asList(), "string", null);

        violations = validator.validate(wrongObject);
        List<String> constraintPaths = Arrays.asList("created", "checksums", "accessMethods");
        for (ConstraintViolation<Object> violation : violations) {
            String property = violation.getPropertyPath().toString();
            Assert.assertTrue(constraintPaths.contains(property));
        }
    }
}
