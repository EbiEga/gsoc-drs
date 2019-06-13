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
import java.time.OffsetDateTime;
import java.util.List;

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
        AssertAnnotations.assertField(Bundle.class, "size", Column.class, NonNull.class);
        AssertAnnotations.assertField(Bundle.class, "created", Column.class, NonNull.class, JsonSerialize.class);
        AssertAnnotations.assertField(Bundle.class, "updated", JsonSerialize.class);
        AssertAnnotations.assertField(Bundle.class, "version");
        AssertAnnotations.assertField(Bundle.class, "checksums", Column.class, OneToMany.class, NotEmpty.class);
        AssertAnnotations.assertField(Bundle.class, "description");
        AssertAnnotations.assertField(Bundle.class, "aliases", ElementCollection.class);
        AssertAnnotations.assertField(Bundle.class, "contents", Column.class, OneToMany.class, NotEmpty.class);
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
    public void testConstraints(){

    }
}
