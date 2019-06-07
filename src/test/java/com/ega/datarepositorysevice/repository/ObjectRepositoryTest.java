package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Object;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ObjectRepositoryTest {

    @Autowired
    ObjectRepository objectRepository;

    @Test
    public void getBundleByIdTest() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        Object object = new Object("id", "string", 0, null,  df.parse("2019-06-02T14:04:49.123Z"), "string", "application/json",
                null, null, "string", null);
        objectRepository.save(object);
        Object responseObject = objectRepository.findById("id").get();
        Assert.assertEquals("id", responseObject.getId());
        Assert.assertEquals("string", responseObject.getName());
        Assert.assertEquals(0, responseObject.getSize());
    }
}
