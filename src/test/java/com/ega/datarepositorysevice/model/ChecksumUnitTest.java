package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.model.enums.ChecksumType;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ChecksumUnitTest {
    @Test
    public void typeAnnotations() {

        AssertAnnotations.assertType(
                ServiceInfo.class, Entity.class, Table.class, JsonInclude.class);
    }

    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(Checksum.class, "id", Id.class, GeneratedValue.class);
        AssertAnnotations.assertField(Checksum.class, "checksum");
        AssertAnnotations.assertField(Checksum.class, "type", Column.class, Enumerated.class, NotNull.class);
        AssertAnnotations.assertField(Checksum.class, "bundle", JoinColumn.class, ManyToOne.class);
        AssertAnnotations.assertField(Checksum.class, "object", JoinColumn.class, ManyToOne.class);
    }

    @Test
    public void methodAnnotations() {
        AssertAnnotations.assertMethod(
                Checksum.class, "getId", JsonIgnore.class);
        AssertAnnotations.assertMethod(
                Checksum.class, "getChecksum", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Checksum.class, "getType", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Checksum.class, "getBundle", JsonIgnore.class);
        AssertAnnotations.assertMethod(
                Checksum.class, "getObject", JsonIgnore.class);

    }

    @Test
    public void entity() {
        Entity entity
                = ReflectTool.getClassAnnotation(Checksum.class, Entity.class);
        Assert.assertEquals("", entity.name());
    }

    @Test
    public void table() {
        Table table
                = ReflectTool.getClassAnnotation(Checksum.class, Table.class);
        Assert.assertEquals("checksum", table.name());
    }

    @Test
    public void testConstraints(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Checksum validChecksum = new Checksum("string", ChecksumType.MD5_Code);
        Set<ConstraintViolation<Checksum>> violations = validator.validate(validChecksum);

        Assert.assertTrue(violations.isEmpty());

        Checksum wrongChecksum = new Checksum("string", null);
        violations = validator.validate(wrongChecksum);
        List<String> constraintProperties = Arrays.asList("type");

        for(ConstraintViolation<Checksum> violation:violations){
            String property = violation.getPropertyPath().toString();
            Assert.assertTrue(constraintProperties.contains(property));
        }
    }
}
