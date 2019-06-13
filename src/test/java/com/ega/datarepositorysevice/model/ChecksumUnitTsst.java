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
import javax.validation.constraints.NotNull;

public class ChecksumUnitTsst {
    @Test
    public void typeAnnotations() {

        AssertAnnotations.assertType(
                ServiceInfo.class, Entity.class, Table.class, JsonInclude.class);
    }

    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(Checksum.class, "checksum", Id.class);
        AssertAnnotations.assertField(Checksum.class, "type", Column.class, Enumerated.class, NotNull.class);
        AssertAnnotations.assertField(Checksum.class, "bundleObject", JoinColumn.class, ManyToOne.class);
        AssertAnnotations.assertField(Checksum.class, "object", JoinColumn.class, ManyToOne.class);
    }

    @Test
    public void methodAnnotations() {
        AssertAnnotations.assertMethod(
                Checksum.class, "getChecksums", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Checksum.class, "getType", JsonProperty.class);
        AssertAnnotations.assertMethod(
                Checksum.class, "getBundleObject", JsonIgnore.class);
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

    }
}
