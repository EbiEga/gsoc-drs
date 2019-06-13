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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
                BundleObject.class, "getName",JsonProperty.class);
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
    public void testConstraints(){

    }

    @Test
    public void testNamePattern(){

    }
}

