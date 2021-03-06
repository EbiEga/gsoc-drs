package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.BundleObjectType;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BundleUnitTest {
    @Test
    public void typeAnnotations() {

        AssertAnnotations.assertType(
                Bundle.class, Entity.class, Table.class, JsonInclude.class);
    }


    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(Bundle.class, "id", Id.class, GeneratedValue.class);
        AssertAnnotations.assertField(Bundle.class, "name");
        AssertAnnotations.assertField(Bundle.class, "size", Column.class, NotNull.class);
        AssertAnnotations.assertField(Bundle.class, "created", Column.class, NotNull.class, JsonSerialize.class);
        AssertAnnotations.assertField(Bundle.class, "updated", JsonSerialize.class);
        AssertAnnotations.assertField(Bundle.class, "version");
        AssertAnnotations.assertField(Bundle.class, "checksums", ElementCollection.class, OneToMany.class, NotEmpty.class);
        AssertAnnotations.assertField(Bundle.class, "description");
        AssertAnnotations.assertField(Bundle.class, "aliases", ElementCollection.class);
        AssertAnnotations.assertField(Bundle.class, "contents", OneToMany.class, NotEmpty.class, ElementCollection.class);
    }

    @Test
    public void methodAnnotations() {
        AssertAnnotations.assertMethod(
                Bundle.class, "getId", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getName", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getSize", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getCreated", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getUpdated", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getVersion", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getChecksums", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getDescription", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getAliases", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Bundle.class, "getContents", JsonProperty.class);
    }

    @Test
    public void entity() {
        Entity entity
                = ReflectTool.getClassAnnotation(Bundle.class, Entity.class);
        Assert.assertEquals("", entity.name());
    }

    @Test
    public void table() {
        Table table
                = ReflectTool.getClassAnnotation(Bundle.class, Table.class);
        Assert.assertEquals("bundle", table.name());
    }

    @Test
    public void testConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Bundle validBundle = new Bundle(Long.parseLong("1"), "string", 23, date,
                date, "string", Arrays.asList(new Checksum("string", ChecksumType.ETAG_CODE)), "string",
                Arrays.asList("string"), Arrays.asList(new BundleObject(Long.parseLong("1"), "string", BundleObjectType.BUNDLE, null, null)));

        Set<ConstraintViolation<Bundle>> violations = validator.validate(validBundle);

        Assert.assertTrue(violations.isEmpty());

        Bundle wrongBundle = new Bundle(Long.parseLong("1"), "string", 23, date,
                date, "string", null, "string",
                Arrays.asList("string"), Arrays.asList(new BundleObject(Long.parseLong("1"), "string", BundleObjectType.OBJECT, null, null)));
        violations = validator.validate(wrongBundle);
        List<String> constraintProperties = Arrays.asList("created", "checksums", "accessMethods");

        for (ConstraintViolation<Bundle> violation : violations) {
            String property = violation.getPropertyPath().toString();
            Assert.assertTrue(constraintProperties.contains(property));
        }
    }
}
