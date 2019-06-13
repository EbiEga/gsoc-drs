package com.ega.datarepositorysevice.model;

import com.ega.datarepositorysevice.utils.AssertAnnotations;
import com.ega.datarepositorysevice.utils.ReflectTool;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

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
        AssertAnnotations.assertField(Object.class, "size", Column.class, NonNull.class);
        AssertAnnotations.assertField(Object.class, "created",Column.class, NonNull.class, JsonSerialize.class);
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
    public void testConstraints(){

    }
}
