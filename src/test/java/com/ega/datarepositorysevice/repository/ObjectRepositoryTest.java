package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Checksum;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.utils.TestObjectCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ObjectRepositoryTest {

    @Autowired
    ObjectRepository objectRepository;

    @Test
    public void getBundleByIdTest() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = TestObjectCreator.getObject();
        object = objectRepository.save(object);
        Object responseObject = objectRepository.findById(object.getId()).get();
        Assert.assertEquals(object, responseObject);

    }

    @Test(expected = ConstraintViolationException.class)
    public void getBundleByIdNullTest() throws ParseException {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object(Long.parseLong("5"), "string", 0, null, date, "string", "application/json",
                new ArrayList<>(), new ArrayList<>(), "string", null);
        objectRepository.save(object);
        Optional<Object> responseObject = objectRepository.findById(Long.parseLong("3"));
        Assert.assertTrue(!responseObject.isPresent());
    }
}
